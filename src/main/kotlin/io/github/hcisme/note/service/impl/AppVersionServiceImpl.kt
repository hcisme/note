package io.github.hcisme.note.service.impl

import io.github.hcisme.note.config.AppConfig
import io.github.hcisme.note.entity.constants.Constants
import io.github.hcisme.note.entity.enums.ResponseCodeEnum
import io.github.hcisme.note.entity.pojo.AppVersion
import io.github.hcisme.note.entity.query.AppVersionQuery
import io.github.hcisme.note.exception.BusinessException
import io.github.hcisme.note.mappers.AppVersionMapper
import io.github.hcisme.note.redis.RedisUtils
import io.github.hcisme.note.redis.deleteUploadingFileInfo
import io.github.hcisme.note.redis.getUploadingFileInfo
import io.github.hcisme.note.service.AppVersionService
import io.github.hcisme.note.utils.FileTools
import jakarta.annotation.Resource
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


/**
 * APP版本管理表 业务接口实现
 */
@Service("appVersionService")
class AppVersionServiceImpl : AppVersionService {
    @Resource
    private lateinit var appVersionMapper: AppVersionMapper<AppVersion, AppVersionQuery>

    @Resource
    private lateinit var redisUtils: RedisUtils

    @Resource
    private lateinit var appConfig: AppConfig

    private val logger = LoggerFactory.getLogger(AppVersionServiceImpl::class.java)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * 根据条件查询列表
     */
    override fun findListByParam(param: AppVersionQuery): List<AppVersion> {
        return this.appVersionMapper.selectList(param)
    }

    /**
     * 新增
     */
    override fun add(bean: AppVersion): Int {
        return this.appVersionMapper.insert(bean)
    }

    /**
     * 根据Id获取对象
     */
    override fun getAppVersionById(id: Long): AppVersion? {
        return this.appVersionMapper.selectById(id)
    }

    /**
     * 根据Id修改
     */
    override fun updateAppVersionById(bean: AppVersion, id: Long): Int {
        return this.appVersionMapper.updateById(bean, id)
    }

    /**
     * 根据Id删除
     */
    override fun deleteAppVersionById(id: Long): Int {
        return this.appVersionMapper.deleteById(id)
    }

    /**
     * 根据VersionCode获取对象
     */
    override fun getAppVersionByVersionCode(versionCode: Int): AppVersion? {
        return this.appVersionMapper.selectByVersionCode(versionCode)
    }

    /**
     * 根据VersionCode修改
     */
    override fun updateAppVersionByVersionCode(bean: AppVersion, versionCode: Int): Int {
        return this.appVersionMapper.updateByVersionCode(bean, versionCode)
    }

    /**
     * 根据VersionCode删除
     */
    override fun deleteAppVersionByVersionCode(versionCode: Int): Int {
        return this.appVersionMapper.deleteByVersionCode(versionCode)
    }

    /**
     * 根据VersionName获取对象
     */
    override fun getAppVersionByVersionName(versionName: String): AppVersion? {
        return this.appVersionMapper.selectByVersionName(versionName)
    }

    /**
     * 根据VersionName修改
     */
    override fun updateAppVersionByVersionName(bean: AppVersion, versionName: String): Int {
        return this.appVersionMapper.updateByVersionName(bean, versionName)
    }

    /**
     * 根据VersionName删除
     */
    override fun deleteAppVersionByVersionName(versionName: String): Int {
        return this.appVersionMapper.deleteByVersionName(versionName)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun uploadAppInfo(
        versionCode: Int,
        versionName: String,
        fileMd5: String,
        updateContent: String,
        uploadId: String
    ) {
        val curDate = Date()
        val uploadingFile4Redis = redisUtils.getUploadingFileInfo(uploadId = uploadId)
            ?: throw BusinessException(codeEnum = ResponseCodeEnum.CODE_600)

        appVersionMapper.insert(AppVersion().apply {
            this@apply.versionCode = versionCode
            this@apply.versionName = versionName
            this@apply.downloadUrl = "$versionCode"
            this@apply.fileSize = uploadingFile4Redis.fileSize
            this@apply.fileMd5 = fileMd5
            this@apply.updateContent = updateContent
            this@apply.publishTime = curDate
            this@apply.createdTime = curDate
            this@apply.updatedTime = curDate
        })

        // 在协程中合成文件操作 合成后 删除相关的临时目录
        syntheticFile(
            versionCode = versionCode,
            versionName = versionName,
            uploadId = uploadId,
            fileMd5 = fileMd5,
            fileSize = uploadingFile4Redis.fileSize,
            chunks = uploadingFile4Redis.chunks!!
        )
    }

    override fun selectMaxVersion(): AppVersion? {
        return appVersionMapper.selectMaxVersion()
    }

    private fun syntheticFile(
        versionCode: Int,
        uploadId: String,
        versionName: String,
        fileMd5: String,
        fileSize: Long,
        chunks: Int
    ) {
        // 源临时切片目录
        val filePath =
            "${appConfig.projectFolder}${Constants.UPLOAD_FOLDER}${Constants.UPLOAD_APP_TEMP_FOLDER}$uploadId/$versionCode"

        val targetFileFolder =
            "${appConfig.projectFolder}${Constants.UPLOAD_FOLDER}${Constants.UPLOAD_APP_FOLDER}${versionCode}/"
        val targetFilePath = "${targetFileFolder}${versionName}${Constants.APP_SUFFIX}"

        scope.launch {
            // 保证最终会尽量清理临时目录；中途若抛出异常，会向上抛出 BusinessException
            val tmpDir = File(filePath)
            val targetDir = File(targetFileFolder)
            val targetFile = File(targetFilePath)

            // 临时合并文件使用一个中间文件（同目录下 .part），避免写入到最终名造成不一致
            val tempMergedFile = File(targetDir, "${versionName}${Constants.APP_SUFFIX}${Constants.TEMP_APP_SUFFIX}")

            try {
                // 检查临时目录是否存在
                if (!tmpDir.exists() || !tmpDir.isDirectory) {
                    throw BusinessException("临时目录不存在 或 不是一个目录")
                }

                // 确保目标目录存在
                if (!targetDir.exists()) {
                    if (!targetDir.mkdirs()) {
                        throw BusinessException("无法创建目录")
                    }
                }

                // 如果已有旧的合并临时文件，先删除
                if (tempMergedFile.exists()) {
                    tempMergedFile.delete()
                }

                // 合并切片：按文件名从 0 到 chunks-1 顺序合并
                FileOutputStream(tempMergedFile, true).use { output ->
                    val buffer = ByteArray(8 * 1024) // 8KB 缓冲
                    for (i in 0 until chunks) {
                        val chunkFile = File(tmpDir, i.toString())
                        if (!chunkFile.exists() || !chunkFile.isFile) {
                            throw BusinessException(codeEnum = ResponseCodeEnum.CODE_600) // 切片缺失
                        }
                        FileInputStream(chunkFile).use { input ->
                            var read: Int
                            while (input.read(buffer).also { read = it } != -1) {
                                output.write(buffer, 0, read)
                            }
                        }
                    }
                    output.flush()
                }

                // 校验合并后文件的大小（先检查）
                val mergedSize = tempMergedFile.length()
                if (mergedSize != fileSize) {
                    // 清理临时合并文件，抛出异常提示尺寸不匹配
                    tempMergedFile.delete()
                    throw BusinessException("尺寸和上传时大小不一样")
                }

                // 计算合并后文件的 MD5 并与传入的 fileMd5 比对
                val computedMd5 = withContext(Dispatchers.Default) { FileTools.calculateMd5(tempMergedFile) }
                // 统一小写比较
                if (!computedMd5.equals(fileMd5, ignoreCase = true)) {
                    tempMergedFile.delete()
                    throw BusinessException("文件 MD5 HASH 值校验失败")
                }

                // 校验通过后，将临时合并文件重命名为目标文件（原子操作：renameTo）
                if (targetFile.exists()) {
                    // 如果需要可以先备份或直接覆盖；这里先删除旧文件再移动
                    targetFile.delete()
                }
                val renamed = tempMergedFile.renameTo(targetFile)
                if (!renamed) {
                    logger.info("重命名失败, 尝试复制然后删除")
                    FileInputStream(tempMergedFile).use { inStream ->
                        FileOutputStream(targetFile).use { outStream ->
                            val buf = ByteArray(8 * 1024)
                            var r: Int
                            while (inStream.read(buf).also { r = it } != -1) {
                                outStream.write(buf, 0, r)
                            }
                            outStream.flush()
                        }
                    }
                    tempMergedFile.delete()
                    logger.info("复制成功, 已删除临时 ${Constants.TEMP_APP_SUFFIX} 文件")
                }

                // 合成成功后删除临时目录下的切片文件（彻底清理）
                tmpDir.listFiles()?.forEach { it.delete() }
                tmpDir.parent?.let {
                    File(it).deleteRecursively()
                }

                // 删除Redis残留
                redisUtils.deleteUploadingFileInfo(uploadId = uploadId)

                // 合成成功可以记录日志或更新状态（示例）
                logger.info("合并完成：versionCode=$versionCode target=${targetFile.absolutePath} size=$mergedSize md5=$computedMd5")
            } catch (e: BusinessException) {
                logger.error("合并切片失败（BusinessException） uploadId=$uploadId versionCode=$versionCode", e)
                throw e
            } catch (e: Exception) {
                logger.error("合并切片失败 uploadId=$uploadId versionCode=$versionCode", e)
                // 尝试清理临时合并文件
                tempMergedFile.delete()
                throw BusinessException("合并切片失败")
            }
        }
    }
}
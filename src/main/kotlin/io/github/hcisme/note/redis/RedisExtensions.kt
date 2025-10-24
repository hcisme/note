package io.github.hcisme.note.redis

import io.github.hcisme.note.entity.constants.Constants
import io.github.hcisme.note.entity.vo.TokenUserInfoVO
import io.github.hcisme.note.entity.vo.UploadingFile4Redis
import io.github.hcisme.note.utils.StringTools
import java.io.File
import java.util.*

/**
 * 保存验证码
 */
fun RedisUtils.saveCaptcha(code: String, time: Long = Constants.REDIS_TIME_1MIN * 10L): String {
    val captchaKey = UUID.randomUUID().toString()
    this.setValueAndExpire(Constants.REDIS_KEY_CAPTCHA_KEY + captchaKey, code, time)
    return captchaKey
}

/**
 * 获取验证码
 */
fun RedisUtils.getCaptcha(captchaKey: String) = this.getValue(Constants.REDIS_KEY_CAPTCHA_KEY + captchaKey) as String?

/**
 * 根据 `captchaKey` 清空当前验证码
 */
fun RedisUtils.cleanCaptcha(captchaKey: String) {
    this.delete(Constants.REDIS_KEY_CAPTCHA_KEY + captchaKey)
}

/**
 * 保存登录信息到redis
 */
fun RedisUtils.saveTokenUserInfo(tokenUserInfoVO: TokenUserInfoVO) {
    val token = UUID.randomUUID().toString()
    tokenUserInfoVO.apply {
        this@apply.token = token
        this@apply.expireAt = System.currentTimeMillis() + Constants.REDIS_KEY_TOKEN_EXPIRES_15_DAY

        this@saveTokenUserInfo.setValueAndExpire(
            Constants.REDIS_KEY_TOKEN + token,
            this@apply,
            Constants.REDIS_KEY_TOKEN_EXPIRES_15_DAY.toLong()
        )
    }
}

/**
 * 通过 `token` 获取用户信息
 */
fun RedisUtils.getUserInfoByToken(token: String?): TokenUserInfoVO? {
    return this.getValue(Constants.REDIS_KEY_TOKEN + token) as TokenUserInfoVO?
}

/**
 * 通过 token 清空指定用户redis里的 `token` 信息
 */
fun RedisUtils.cleanTokenByToken(token: String?) {
    this.delete(Constants.REDIS_KEY_TOKEN + token)
}

/**
 * 更新 redis 里的用户信息
 */
fun RedisUtils.updateTokenUserInfo(tokenUserInfoVO: TokenUserInfoVO) {
    this.setValue(
        Constants.REDIS_KEY_TOKEN + tokenUserInfoVO.token,
        tokenUserInfoVO
    )
}

/**
 * 保存视频 预上传信息
 *
 * @return `uploadId` String
 */
fun RedisUtils.savePreUploadAppInfo(
    versionCode: Int,
    fileSize: Long,
    chunks: Int
): String {
    val uploadId = StringTools.getRandomNumber(Constants.LENGTH_8)
    val filePath = "$uploadId/$versionCode"
    val uploadingFile4Redis = UploadingFile4Redis().apply {
        this@apply.uploadId = uploadId
        this@apply.chunkIndex = 0
        this@apply.chunks = chunks
        this@apply.filePath = filePath
        this@apply.fileSize = fileSize
        this@apply.versionCode = versionCode
    }

    val tempAppPath =
        "${this.appConfig.projectFolder}${Constants.UPLOAD_FOLDER}${Constants.UPLOAD_APP_TEMP_FOLDER}$filePath"
    File(tempAppPath).also { it.mkdirs() }
    this.setValueAndExpire(
        "${Constants.REDIS_KEY_UPLOADING_FILE}:$uploadId",
        uploadingFile4Redis,
        Constants.REDIS_KEY_EXPIRES_ONE_DAY.toLong()
    )
    return uploadId
}

/**
 * 获取视频 的 上传信息
 */
fun RedisUtils.getUploadingFileInfo(uploadId: String): UploadingFile4Redis? {
    return this.getValue("${Constants.REDIS_KEY_UPLOADING_FILE}:$uploadId") as UploadingFile4Redis?
}

/**
 * 更新上传中的文件信息
 */
fun RedisUtils.updateUploadingFileInfo(uploadId: String, info: UploadingFile4Redis) {
    this.setValueAndExpire(
        "${Constants.REDIS_KEY_UPLOADING_FILE}:$uploadId",
        info,
        Constants.REDIS_KEY_EXPIRES_ONE_DAY.toLong() / 2
    )
}

/**
 * 通过 上传id 删除对应的数据
 */
fun RedisUtils.deleteUploadingFileInfo(uploadId: String) {
    this.delete("${Constants.REDIS_KEY_UPLOADING_FILE}:$uploadId")
}

package io.github.hcisme.note.controller

import io.github.hcisme.note.annotation.Access
import io.github.hcisme.note.config.AppConfig
import io.github.hcisme.note.entity.constants.Constants
import io.github.hcisme.note.entity.enums.ResponseCodeEnum
import io.github.hcisme.note.entity.vo.PreUploadAppVO
import io.github.hcisme.note.entity.vo.ResponseVO
import io.github.hcisme.note.entity.vo.UploadingFileVO
import io.github.hcisme.note.exception.BusinessException
import io.github.hcisme.note.redis.RedisUtils
import io.github.hcisme.note.redis.getUploadingFileInfo
import io.github.hcisme.note.redis.savePreUploadAppInfo
import io.github.hcisme.note.redis.updateUploadingFileInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

/**
 * 文件操作 Controller
 */
@Tag(name = "web 文件操作")
@RestController
@RequestMapping("/file")
class FileController : ABaseController() {
    @Resource
    private lateinit var appConfig: AppConfig

    @Resource
    private lateinit var redisUtils: RedisUtils

    @Operation(summary = "app文件 预上传")
    @CrossOrigin
    @PostMapping("/preUpload")
    fun preUpload(@Validated @RequestBody preUploadAppVO: PreUploadAppVO): ResponseVO<String> {
        checkUploadFileAccess(
            username = preUploadAppVO.username!!,
            password = preUploadAppVO.password!!,
            appConfig = appConfig
        )
        val uploadId = redisUtils.savePreUploadAppInfo(
            versionCode = preUploadAppVO.versionCode!!,
            fileSize = preUploadAppVO.fileSize!!,
            chunks = preUploadAppVO.chunks!!
        )
        return getSuccessResponseVO(uploadId)
    }

    @Operation(summary = "文件分片上传")
    @CrossOrigin
    @PostMapping("/upload")
    fun upload(
        @Validated @ModelAttribute info: UploadingFileVO,
        @RequestParam("chunkFile") chunkFile: MultipartFile? = null
    ): ResponseVO<Any?> {
        if (chunkFile == null) {
            throw BusinessException("上传的切片不能为空")
        }
        checkUploadFileAccess(username = info.username!!, password = info.password!!, appConfig = appConfig)

        val uploadId = info.uploadId!!
        val chunkIndex = info.chunkIndex!!
        val uploadingFile4Redis = redisUtils.getUploadingFileInfo(uploadId = uploadId)
            ?: throw BusinessException("文件不存在,请重新上传")

        // 判断分片 保证顺序
        if (
            chunkIndex !in Constants.ZERO..uploadingFile4Redis.chunks!! ||
            chunkIndex - 1 > uploadingFile4Redis.chunkIndex!! ||
            chunkIndex > uploadingFile4Redis.chunks!! - 1
        ) {
            throw BusinessException(ResponseCodeEnum.CODE_600)
        }

        val tempPath =
            "${appConfig.projectFolder}${Constants.UPLOAD_FOLDER}${Constants.UPLOAD_APP_TEMP_FOLDER}${uploadingFile4Redis.filePath}"
        chunkFile.transferTo(File("$tempPath/$chunkIndex"))

        // 更新Redis
        uploadingFile4Redis.apply { this@apply.chunkIndex = chunkIndex }
        redisUtils.updateUploadingFileInfo(uploadId = uploadId, info = uploadingFile4Redis)
        return getSuccessResponseVO(null)
    }

    @Operation(summary = "app文件下载")
    @CrossOrigin
    @Access
    @GetMapping("/download/{versionCode}/{versionName}")
    fun download(
        response: HttpServletResponse,
        @PathVariable @NotNull(message = "版本代码不能为空") versionCode: Int? = null,
        @PathVariable @NotEmpty(message = "版本号不能为空") versionName: String? = null
    ) {
        val targetFilePath =
            "${appConfig.projectFolder}${Constants.UPLOAD_FOLDER}${Constants.UPLOAD_APP_FOLDER}${versionCode}/${versionName}${Constants.APP_SUFFIX}"

        response.setHeader("Content-Type", "application/octet-stream")
        readFile(response, targetFilePath)
    }
}
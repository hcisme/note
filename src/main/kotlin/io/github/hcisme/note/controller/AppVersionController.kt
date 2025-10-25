package io.github.hcisme.note.controller

import io.github.hcisme.note.annotation.Access
import io.github.hcisme.note.config.AppConfig
import io.github.hcisme.note.entity.pojo.AppVersion
import io.github.hcisme.note.entity.vo.ResponseVO
import io.github.hcisme.note.entity.vo.UploadAppInfoVO
import io.github.hcisme.note.service.AppVersionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.Resource
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * APP版本管理表 Controller
 */
@Tag(name = "版本管理")
@RestController("appVersionController")
@RequestMapping("/appVersion")
class AppVersionController : ABaseController() {
    @Resource
    private lateinit var appVersionService: AppVersionService

    @Resource
    private lateinit var appConfig: AppConfig

    @Operation(summary = "上传应用相关信息")
    @CrossOrigin
    @PostMapping("/uploadAppInfo")
    fun uploadAppInfo(@Validated @RequestBody info: UploadAppInfoVO): ResponseVO<Any?> {
        checkUploadFileAccess(username = info.username!!, password = info.password!!, appConfig = appConfig)

        appVersionService.uploadAppInfo(
            versionCode = info.versionCode!!,
            versionName = info.versionName!!,
            fileMd5 = info.fileMd5!!,
            updateContent = info.updateContent!!,
            uploadId = info.uploadId!!
        )
        return getSuccessResponseVO(null)
    }

    @Operation(summary = "查询最大版本信息")
    @CrossOrigin
    @Validated
    @GetMapping("/selectMaxVersion")
    fun selectMaxVersion(
        @RequestParam @NotEmpty(message = "用户名不能为空") username: String? = null,
        @RequestParam @NotEmpty(message = "密码不能为空") password: String? = null
    ): ResponseVO<AppVersion?> {
        checkUploadFileAccess(username = username!!, password = password!!, appConfig = appConfig)

        val appVersion = appVersionService.selectMaxVersion()
        return getSuccessResponseVO(appVersion)
    }

    @Operation(summary = "app检查更新接口")
    @Access
    @GetMapping("/checkUpdate/{versionCode}")
    fun checkUpdate(@Valid @PathVariable @NotNull(message = "版本代码不能为空") versionCode: Int? = null): ResponseVO<AppVersion?> {
        val appMaxVersion = appVersionService.selectMaxVersion()
        if (appMaxVersion == null || versionCode!! >= (appMaxVersion.versionCode ?: 0)) {
            return getSuccessResponseVO(null)
        }
        return getSuccessResponseVO(appMaxVersion)
    }
}
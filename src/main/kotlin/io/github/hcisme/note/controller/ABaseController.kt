package io.github.hcisme.note.controller

import io.github.hcisme.note.config.AppConfig
import io.github.hcisme.note.entity.enums.ResponseCodeEnum
import io.github.hcisme.note.entity.vo.ResponseVO
import io.github.hcisme.note.entity.vo.TokenUserInfoVO
import io.github.hcisme.note.exception.BusinessException
import io.github.hcisme.note.redis.RedisUtils
import io.github.hcisme.note.redis.cleanCaptcha
import io.github.hcisme.note.redis.getCaptcha
import io.github.hcisme.note.redis.getUserInfoByToken
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.File
import java.io.FileInputStream
import java.net.URLEncoder

open class ABaseController {
    @Resource
    private lateinit var redisUtils: RedisUtils

    private val logger = LoggerFactory.getLogger(ABaseController::class.java)

    companion object {
        const val STATUS_SUCCESS = "success"
        const val STATUS_ERROR = "error"
    }

    protected fun <T> getSuccessResponseVO(t: T): ResponseVO<T> {
        val responseVO = ResponseVO<T>()
        responseVO.status = STATUS_SUCCESS
        responseVO.code = ResponseCodeEnum.CODE_200.code
        responseVO.info = ResponseCodeEnum.CODE_200.msg
        responseVO.data = t
        return responseVO
    }

    protected fun <T> getBusinessErrorResponseVO(e: BusinessException, t: T): ResponseVO<T> {
        val vo = ResponseVO<T>()
        vo.status = STATUS_ERROR
        vo.code = e.code ?: ResponseCodeEnum.CODE_600.code
        vo.info = e.message
        vo.data = t
        return vo
    }

    protected fun <T> getServerErrorResponseVO(t: T): ResponseVO<T> {
        val vo = ResponseVO<T>()
        vo.status = STATUS_ERROR
        vo.code = ResponseCodeEnum.CODE_500.code
        vo.info = ResponseCodeEnum.CODE_500.msg
        vo.data = t
        return vo
    }

    /**
     * 检验 验证码是否正确 的方法
     *
     * `captcha` 前端传过来的 验证码
     *
     * `captchaKey` 前端传过来的 验证码KEY
     *
     * `onSuccess` 验证正确 触发此回调
     */
    protected fun <T> checkCaptcha(captcha: String, captchaKey: String, onSuccess: () -> ResponseVO<T>): ResponseVO<T> {
        val dbCaptcha = redisUtils.getCaptcha(captchaKey)

        try {
            if (dbCaptcha == null) {
                throw BusinessException("验证码过期 请重新获取")
            }
            if (!captcha.equals(dbCaptcha, ignoreCase = true)) {
                throw BusinessException("图片验证码不正确")
            }

            return onSuccess()
        } finally {
            redisUtils.cleanCaptcha(captchaKey)
        }
    }

    protected fun getUserInfoByToken(): TokenUserInfoVO? {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val token: String? = request.getHeader("token")
        return redisUtils.getUserInfoByToken(token)
    }

    protected fun checkUploadFileAccess(username: String, password: String, appConfig: AppConfig) {
        if (username != appConfig.account || password != appConfig.password) {
            throw BusinessException("用户名或密码错误")
        }
    }

    protected fun readFile(response: HttpServletResponse, filePath: String) {
        val file = File(filePath)
        if (!file.exists()) {
            return
        }
        val fileName = file.name
        val encodedFileName = try {
            URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
        } catch (_: Exception) {
            fileName
        }
        response.setHeader("Content-Length", file.length().toString())
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''$encodedFileName")
        try {
            val output = response.outputStream
            val input = FileInputStream(file)

            input.use { inputStream ->
                output.use { outputStream ->
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.flush()
                }
            }
        } catch (e: Exception) {
            logger.error("读取文件异常", e)
            throw BusinessException("下载文件异常")
        }
    }
}

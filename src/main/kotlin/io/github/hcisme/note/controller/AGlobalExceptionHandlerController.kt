package io.github.hcisme.note.controller

import io.github.hcisme.note.entity.enums.ResponseCodeEnum
import io.github.hcisme.note.entity.vo.ResponseVO
import io.github.hcisme.note.exception.BusinessException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class AGlobalExceptionHandlerController : ABaseController() {
    companion object {
        private val logger = LoggerFactory.getLogger(AGlobalExceptionHandlerController::class.java)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception, request: HttpServletRequest): ResponseVO<Any> {
        logger.error("请求错误，请求地址${request.requestURL}, 错误信息:", e)
        val ajaxResponse = ResponseVO<Any>()

        when (e) {
            is NoHandlerFoundException -> {
                ajaxResponse.code = ResponseCodeEnum.CODE_404.code
                ajaxResponse.info = ResponseCodeEnum.CODE_404.msg
                ajaxResponse.status = STATUS_ERROR
            }

            is BusinessException -> {
                ajaxResponse.code = e.code ?: ResponseCodeEnum.CODE_600.code
                ajaxResponse.info = e.message
                ajaxResponse.status = STATUS_ERROR
            }

            is BindException,
            is MethodArgumentTypeMismatchException,
            is HandlerMethodValidationException,
            is MissingServletRequestPartException -> {
                val map = HashMap<String, String>()
                ajaxResponse.code = ResponseCodeEnum.CODE_600.code
                ajaxResponse.info = ResponseCodeEnum.CODE_600.msg
                ajaxResponse.status = STATUS_ERROR

                if (e is BindException && e.hasErrors()) {
                    val fieldErrors = e.fieldErrors
                    for (i in fieldErrors.indices) {
                        val field = fieldErrors[i]
                        if (!map.containsKey(field.field)) {
                            map[field.field] = ""
                        }
                        map[field.field] = map[field.field] + (field.defaultMessage ?: "") + ";"
                    }
                    ajaxResponse.data = map
                }

                if (e is MissingServletRequestPartException) {
                    map[e.requestPartName] = e.requestPartName + " 必传"
                    ajaxResponse.data = map
                }
            }

            is DuplicateKeyException -> {
                ajaxResponse.code = ResponseCodeEnum.CODE_601.code
                ajaxResponse.info = ResponseCodeEnum.CODE_601.msg
                ajaxResponse.status = STATUS_ERROR
            }

            else -> {
                ajaxResponse.code = ResponseCodeEnum.CODE_500.code
                ajaxResponse.info = ResponseCodeEnum.CODE_500.msg
                ajaxResponse.status = STATUS_ERROR
            }
        }

        return ajaxResponse
    }
}

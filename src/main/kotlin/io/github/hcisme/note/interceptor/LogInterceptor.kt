package io.github.hcisme.note.interceptor

import io.github.hcisme.note.entity.enums.DateTimePatternEnum
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.text.SimpleDateFormat
import java.util.*

@Component
class LogInterceptor : HandlerInterceptor {
    override fun preHandle(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull handler: Any
    ): Boolean {
        val sdf = SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.name)
        val currentTime = sdf.format(Date())
        val params = StringBuilder()
        val contentType: String? = request.contentType

        if (contentType == null) {
            if (!request.requestURI.contains("/api/webjars")) {
                println("$currentTime ${request.method} ${request.requestURI}")
            }
            return true
        }

        when {
            contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE) -> {
                handleUrlencodedRequest(request, params)
            }

            contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE) -> {
                handleFormDataRequest(request, params)
            }
        }
        println("$currentTime ${request.method} ${request.requestURI}\n参数信息: $params")
        return true
    }

    private fun handleUrlencodedRequest(request: HttpServletRequest, params: StringBuilder) {
        val paramNames = request.parameterNames
        while (paramNames.hasMoreElements()) {
            val key = paramNames.nextElement()
            val value = request.getParameter(key)
            params.append(key).append(": ").append(value).append(", ")
        }
    }

    private fun handleFormDataRequest(request: HttpServletRequest, params: StringBuilder) {
        val parts = request.parts
        for (part in parts) {
            val name = part.name
            val value = request.getParameter(name)
            params.append(name).append(": ").append(value).append(", ")
        }
    }
}

package com.inner.circle.api.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.Base64
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

private const val TMP_KEY = "test"

@Component
class AuthCheckInterceptor : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler is HandlerMethod) {
            val method = handler.method
            if (method.isAnnotationPresent(RequireAuth::class.java)) {
                val authHeader = request.getHeader("Authorization")
                if (authHeader == null || !authHeader.startsWith("Basic ")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    return false
                }

                // TODO: 키 체계에 맞춰서 체크 로직 구성
                val base64Credentials = authHeader.substring("Basic ".length)
                val credentials = String(Base64.getDecoder().decode(base64Credentials)).split(":")

                if (credentials.size != 2 ||
                    credentials[0] != TMP_KEY ||
                    credentials[1].isNotEmpty()
                ) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    return false
                }
            }
        }
        return true
    }
}

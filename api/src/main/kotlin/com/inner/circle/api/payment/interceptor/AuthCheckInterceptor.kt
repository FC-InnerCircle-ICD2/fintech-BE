package com.inner.circle.api.payment.interceptor

import com.inner.circle.core.structure.service.ApiCredentialService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.Base64
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthCheckInterceptor(
    private val apiCredentialService: ApiCredentialService
) : HandlerInterceptor {
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

                val requestUserApiKey = credentials[0]
                if (credentials.size != 2 ||
                    requestUserApiKey.isEmpty() ||
                    credentials[1].isNotEmpty()
                ) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    return false
                }

                val merchantId = apiCredentialService.getMerchantIdByApiKey(requestUserApiKey)
                request.setAttribute("merchantId", merchantId)
            }
        }
        return true
    }
}

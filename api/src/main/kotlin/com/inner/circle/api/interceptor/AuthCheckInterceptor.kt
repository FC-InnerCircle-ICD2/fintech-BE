package com.inner.circle.api.interceptor

import com.inner.circle.core.usecase.MerchantUserHandleUseCase
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthCheckInterceptor(
    private val merchantUserHandleUseCase: MerchantUserHandleUseCase,
    userHandleUseCase: MerchantUserHandleUseCase
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

                val credentialString = authHeader.substring("Basic ".length)
                val credentials = credentialString.split(":")

                if (credentials.size != 1) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                    return false
                }
                val targetKey = credentials[0]
                val foundMerchantUser = merchantUserHandleUseCase.findMerchantUser(targetKey)

                request.setAttribute("merchantUser", foundMerchantUser)

                return true
            }
        }
        return true
    }
}

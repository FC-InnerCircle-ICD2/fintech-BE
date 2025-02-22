package com.inner.circle.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.api.controller.dto.PaymentError
import com.inner.circle.api.controller.dto.PaymentResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    companion object {
        private val OBJECT_MAPPER = ObjectMapper()
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val error =
            PaymentResponse.fail(
                PaymentError(
                    HttpStatus.UNAUTHORIZED.value().toString(),
                    authException.message ?: "인증이 필요합니다."
                )
            )

        val result = OBJECT_MAPPER.writeValueAsString(error)

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(result)
    }
}

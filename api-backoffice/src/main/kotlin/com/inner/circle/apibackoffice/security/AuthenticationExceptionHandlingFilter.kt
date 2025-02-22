package com.inner.circle.apibackoffice.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.apibackoffice.exception.BackofficeError
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class AuthenticationExceptionHandlingFilter : OncePerRequestFilter() {
    private val mapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
            val error =
                BackofficeResponse.fail(
                    BackofficeError(
                        HttpStatus.UNAUTHORIZED.value().toString(),
                        ex.message ?: "인증이 필요합니다."
                    )
                )

            val result = mapper.writeValueAsString(error)

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(result)
        }
    }
}

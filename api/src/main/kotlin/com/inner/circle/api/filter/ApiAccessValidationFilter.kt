package com.inner.circle.api.filter

import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter

class ApiAccessValidationFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // TODO : API PREFIX 에 따른 인증 인가 방식을 구현 한다.
        val requestUri = request.requestURI
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        when {
            requestUri.startsWith(prefix = MERCHANT_API_PREFIX) -> {
                authenticateWithBasicAuth(authHeader = authHeader)
            }
            requestUri.startsWith(prefix = USER_API_PREFIX) -> {
                authenticateWithBearerToken(authHeader = authHeader)
            }
            requestUri.startsWith(prefix = SSE_API_PREFIX) -> {
                filterChain.doFilter(request, response)
                return
            }
            else -> throw AppException(status = HttpStatus.UNAUTHORIZED, message = "")
        }
    }

    private fun authenticateWithBasicAuth(authHeader: String?) {
        if (authHeader == null || !authHeader.startsWith(prefix = BASIC_AUTH_TOKEN_PREFIX)) {
            throw AppException(HttpStatus.UNAUTHORIZED, "Unauthorized: Basic Auth required")
        }
    }

    private fun authenticateWithBearerToken(authHeader: String?) {
        if (authHeader == null || !authHeader.startsWith(prefix = BEARER_AUTH_TOKEN_PREFIX)) {
            throw AppException(HttpStatus.UNAUTHORIZED, "Unauthorized: Bearer Token required")
        }
    }

    companion object {
        private const val MERCHANT_API_PREFIX = "/api/payment/v1/merchant"
        private const val USER_API_PREFIX = "/api/payment/v1/user"
        private const val SSE_API_PREFIX = "/api/payment/v1/sse"

        private const val BASIC_AUTH_TOKEN_PREFIX = "Basic "
        private const val BEARER_AUTH_TOKEN_PREFIX = "Bearer "
    }
}


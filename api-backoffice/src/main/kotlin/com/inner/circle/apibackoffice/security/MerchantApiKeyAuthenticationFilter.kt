package com.inner.circle.apibackoffice.security

import com.inner.circle.corebackoffice.security.MerchantApiKeyProvider
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MerchantApiKeyAuthenticationFilter(
    private val provider: MerchantApiKeyProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // TODO : Security 로 옮겨야 한다. 임시 처리로 해놓음
        // Security 개선작업 시 옮겨야 함
        if (request.requestURI == MERCHANT_LOGIN_PATH || request.requestURI == MERCHANT_SIGN_UP_PATH) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?: throw UserAuthenticationException.UnauthorizedException(
                    "Missing Authorization header"
                )
        val token = authHeader.removePrefix(BEARER_PREFIX).trim()

        SecurityContextHolder.getContext().authentication =
            provider.getMerchantValidAuthenticationOrThrow(secret = token)

        filterChain.doFilter(request, response)
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
        private const val MERCHANT_LOGIN_PATH = "/api/backoffice/v1/sign-in"
        private const val MERCHANT_SIGN_UP_PATH = "/api/backoffice/v1/sign-up"
    }
}

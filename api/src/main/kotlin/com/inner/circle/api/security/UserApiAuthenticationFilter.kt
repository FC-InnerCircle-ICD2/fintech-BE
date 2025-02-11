package com.inner.circle.api.security

import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter

class UserApiAuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?: throw UserAuthenticationException.UnauthorizedException(
                    "Missing Authorization header"
                )
    }
}

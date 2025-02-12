package com.inner.circle.api.security

import com.inner.circle.core.security.AccountValidationProvider
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class UserApiAuthenticationFilter(
    private val accountValidationProvider: AccountValidationProvider
) : OncePerRequestFilter() {
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

        SecurityContextHolder.getContext().authentication =
            accountValidationProvider.getUserValidAuthenticationOrThrow(token = authHeader)

        filterChain.doFilter(request, response)
    }
}

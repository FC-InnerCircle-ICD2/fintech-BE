package com.inner.circle.api.security

import com.inner.circle.core.security.UserValidation
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter

class UserApiAuthenticationFilter(
    private val userValidation: UserValidation
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyOTM4NDc1NjIzNDI4NzQyMzkiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTczOTI4NTc0MH0.lilzS_zfDE7ZzcuzkQouTBWlVvhAhbXpSoFtLS43KVQ"
        val token = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiIyOTM4NDc1NjIzNDI4NzQyMzkiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTczOTI4NzQzN30.jigVOsC46oOlpheh57VVa22P6LTo8lE7SCRuspTI5KaI63QB0u7pUSrvl2GftF_t"
        val authHeader =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?: throw UserAuthenticationException.UnauthorizedException(
                    "Missing Authorization header"
                )

        userValidation.validateUserOrThrow(token = token)
        filterChain.doFilter(request, response)
    }
}

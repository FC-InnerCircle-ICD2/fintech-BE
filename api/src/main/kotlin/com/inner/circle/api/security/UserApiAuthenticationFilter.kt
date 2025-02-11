package com.inner.circle.api.security

import com.inner.circle.core.service.JwtHandler
import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter

class UserApiAuthenticationFilter(
    private val jwtHandler: JwtHandler,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjM0NTYiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTczOTI4MTcyMH0.auwSH2b4Gy6gLOHxF6ZUup5MYmdrtkJGNe57VR-zav8"
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjM0NTYiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTczOTI4MjY0Mn0.fBFNfANq1At4qQy00x9Ug9vuovwT0cBEkuUJJglUyw8"
        val authHeader =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?: throw UserAuthenticationException.UnauthorizedException(
                    "Missing Authorization header"
                )

        filterChain.doFilter(request, response)
    }
}

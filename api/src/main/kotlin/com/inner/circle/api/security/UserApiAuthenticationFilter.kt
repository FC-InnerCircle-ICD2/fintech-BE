package com.inner.circle.api.security

import com.inner.circle.core.security.AccountValidationProvider
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class UserApiAuthenticationFilter(
    private val provider: AccountValidationProvider,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // TODO : Security 로 옮겨야 한다. 임시 처리로 해놓음
            // Security 개선작업 시 옮겨야 함
            if (request.requestURI == USER_LOGIN_PATH || request.requestURI == USER_SIGN_UP_PATH) {
                filterChain.doFilter(request, response)
                return
            }

            val authHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION)
                    ?: throw UserAuthenticationException.UnauthorizedException()
            val token = authHeader.removePrefix(BEARER_PREFIX).trim()

            SecurityContextHolder.getContext().authentication =
                provider.getUserValidAuthenticationOrThrow(token = token)

            filterChain.doFilter(request, response)
        } catch (ex: UserAuthenticationException.UnauthorizedException) {
            authenticationEntryPoint.commence(
                request,
                response,
                BadCredentialsException(ex.message)
            )
        } finally {
            SecurityContextHolder.clearContext()
        }
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
        private const val USER_LOGIN_PATH = "/api/v1/p/user/sign-in"
        private const val USER_SIGN_UP_PATH = "/api/v1/p/user/sign-up"
    }
}

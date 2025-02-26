package com.inner.circle.api.security

import com.inner.circle.core.security.MerchantApiKeyProvider
import com.inner.circle.exception.UserAuthenticationException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MerchantApiKeyAuthenticationFilter(
    private val provider: MerchantApiKeyProvider,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION)
                    ?: throw UserAuthenticationException.UnauthorizedException()

            val apiKey = resolveApiKey(authHeader)

            val authentication = provider.getAuthentication(apiKey)
            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(
                request,
                response,
                BadCredentialsException(ex.message)
            )
        }
    }

    private fun resolveApiKey(authHeader: String): String {
        authenticateWithBasicAuth(authHeader = authHeader)

        val authorizationInfo = authHeader.split(" ")
        if (authorizationInfo.size != 2 || authorizationInfo[0] != BASIC_AUTH_TOKEN_PREFIX) {
            throw UserAuthenticationException.UnauthorizedException()
        }

        val encodedApiKey = authorizationInfo[1]
        val decodedApiKey =
            try {
                String(
                    java.util.Base64
                        .getDecoder()
                        .decode(encodedApiKey)
                )
            } catch (e: IllegalArgumentException) {
                throw UserAuthenticationException.UnauthorizedException()
            }

        if (!decodedApiKey.contains(":")) {
            throw UserAuthenticationException.UnauthorizedException()
        }

        return decodedApiKey.substringBefore(":")
    }

    private fun authenticateWithBasicAuth(authHeader: String?) {
        if (authHeader == null || !authHeader.startsWith(prefix = BASIC_AUTH_TOKEN_PREFIX)) {
            throw UserAuthenticationException.UnauthorizedException()
        }
    }

    companion object {
        private const val BASIC_AUTH_TOKEN_PREFIX = "Basic"
    }
}

package com.inner.circle.api.security

import com.inner.circle.core.security.CustomSecurityProvider
import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MerchantApiKeyAuthenticationFilter(
    private val provider: CustomSecurityProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        val apiKey = resolveApiKey(authHeader)

        val authentication = provider.getAuthentication(apiKey)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    private fun resolveApiKey(authHeader: String?): String {
        authenticateWithBasicAuth(authHeader = authHeader)

        val authorizationInfo = authHeader?.split(" ")
        val encodedApiKey = authorizationInfo?.get(1)
        val decodedApiKey = String(Base64.getDecoder().decode(encodedApiKey))
        val apiKey = decodedApiKey.removeSuffix(":")

        return apiKey
    }

    private fun authenticateWithBasicAuth(authHeader: String?) {
        if (authHeader == null || !authHeader.startsWith(prefix = BASIC_AUTH_TOKEN_PREFIX)) {
            throw AppException(HttpStatus.UNAUTHORIZED, "Unauthorized: Basic Auth required")
        }
    }

    companion object {
        private const val BASIC_AUTH_TOKEN_PREFIX = "Basic "
    }
}

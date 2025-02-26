package com.inner.circle.apibackoffice.security

import com.inner.circle.corebackoffice.security.MerchantDetailService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class MerchantApiKeyAuthenticationFilter(
    private val detailService: MerchantDetailService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
            val token = authHeader.removePrefix("Basic ").trim()
            val userDetails = detailService.loadUserByUsername(token = token)

            SecurityContextHolder.getContext().authentication =
                MerchantAuthenticationToken(
                    principal = userDetails,
                    credentials = null,
                    authorities = userDetails.authorities
                )

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

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return authHeader == null ||
            !authHeader.startsWith("Basic ") ||
            path == "/api/backoffice/v1/sign-in" ||
            path == "/api/backoffice/v1/sign-up"
    }
}

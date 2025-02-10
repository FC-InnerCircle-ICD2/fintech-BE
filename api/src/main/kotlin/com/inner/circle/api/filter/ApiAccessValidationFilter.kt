package com.inner.circle.api.filter

import com.inner.circle.api.filter.type.AuthenticationType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ApiAccessValidationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestUri =
            request.requestURI
                .split(delimiters = arrayOf(URI_DELIMITER))
                .filterNot {
                    it.isBlank()
                }.getOrNull(AUTH_TYPE_INDEX)!!
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        ValidationFactory(authType = AuthenticationType.of(type = requestUri))
            .getAuthentication()
            .validateOrThrow(authHeader = authHeader)
    }

    companion object {
        private const val URI_DELIMITER = "/"
        private const val AUTH_TYPE_INDEX = 3
    }
}

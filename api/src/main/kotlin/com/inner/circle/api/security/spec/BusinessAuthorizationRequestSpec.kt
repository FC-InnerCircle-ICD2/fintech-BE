package com.inner.circle.api.security.spec

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcherEntry

/**
 * @author Theo
 * @since 2025/02/15
 */
fun HttpSecurity.businessAuthorizationHttpRequests(): BusinessAuthorizationRequestSpec =
    BusinessAuthorizationRequestSpec(this)

class BusinessAuthorizationRequestSpec(
    private val http: HttpSecurity
) {
    private val maps: MutableList<RequestMatcherEntry<AuthorizationManager<HttpServletRequest>>> =
        mutableListOf()

    private var matcher: RequestMatcher? = null

    fun requestMatcher(vararg antPatterns: String): Access {
        val orRequestMatcher = OrRequestMatcher(antPatterns.map { AntPathRequestMatcher(it) })
        this.matcher = orRequestMatcher
        return Access()
    }

    inner class Access {
        fun access(
            manager: AuthorizationManager<HttpServletRequest>
        ): BusinessAuthorizationRequestSpec {
            this@BusinessAuthorizationRequestSpec.maps.add(
                RequestMatcherEntry(this@BusinessAuthorizationRequestSpec.matcher, manager)
            )
            this@BusinessAuthorizationRequestSpec.matcher = null
            return this@BusinessAuthorizationRequestSpec
        }
    }
}

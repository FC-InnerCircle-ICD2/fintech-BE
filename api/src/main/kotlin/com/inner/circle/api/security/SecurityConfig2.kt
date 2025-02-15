package com.inner.circle.api.security

import com.inner.circle.api.security.spec.BusinessAuthorizationRequestSpec
import com.inner.circle.api.security.spec.businessAuthorizationHttpRequests
import com.inner.circle.api.security.spec.manager.OrderAuthorizationManager
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsUtils

/**
 * @author Theo
 * @since 2025/02/15
 */
@Configuration
@EnableWebSecurity
class SecurityConfig2(
    private val somethingAdapater: SomethingAdapater
) {
    /**
     * 게시판 -> 게시글 삭제
     * 1. 회원인가?
     * 2. 이 회원이 해당 게시글에 접근 권한이 있는가?
     */

    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher(AntPathRequestMatcher("/api/**"))
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .cors { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        CorsUtils::isPreFlightRequest,
                        AntPathRequestMatcher("/api/v1/p/user/orders/abcdef")
                    ).permitAll()
            }.businessAuthorizationHttpRequests()
            .buildAccess(OrderAuthorizationManager(somethingAdapater), "/api/v1/p/user/{userId}/**")
            .buildAccess(OrderAuthorizationManager(), "/api/v1/p/domain/**")
            .buildAccess(OrderAuthorizationManager(), "/api/v1/p/product/**")
            .buildAccess(OrderAuthorizationManager(), "/api/v1/p/pay/**")
            .buildAccess(OrderAuthorizationManager(), "/api/v1/p/orders/**")

        return http.build()
    }

    private fun BusinessAuthorizationRequestSpec.buildAccess(
        authorizationManager: AuthorizationManager<HttpServletRequest>,
        vararg antPatterns: String
    ): BusinessAuthorizationRequestSpec =
        this.requestMatcher(*antPatterns).access(authorizationManager)
}

package com.inner.circle.apibackoffice.security

import com.inner.circle.apibackoffice.exception.CustomAuthenticationEntryPoint
import com.inner.circle.corebackoffice.security.MerchantApiKeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val merchantApiKeyProvider: MerchantApiKeyProvider,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/api/backoffice/v1/**")
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .anyRequest()
                    .hasAuthority("ROLE_MERCHANT")
            }.addFilterBefore(
                MerchantApiKeyAuthenticationFilter(
                    merchantApiKeyProvider,
                    authenticationEntryPoint
                ),
                UsernamePasswordAuthenticationFilter::class.java
            ).formLogin { it.disable() }
        return http.build()
    }
}

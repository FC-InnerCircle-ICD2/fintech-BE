package com.inner.circle.api.security

import com.inner.circle.core.security.MerchantApiKeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val merchantApiKeyProvider: MerchantApiKeyProvider
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/api/payment/merchant/v1/**")
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .anyRequest()
                    .hasAuthority("ROLE_MERCHANT")
            }.addFilterBefore(
                MerchantApiKeyAuthenticationFilter(merchantApiKeyProvider),
                UsernamePasswordAuthenticationFilter::class.java
            ).formLogin { it.disable() }
        return http.build()
    }
}

package com.inner.circle.api.security

import com.inner.circle.api.exception.CustomAuthenticationEntryPoint
import com.inner.circle.core.security.AccountValidationProvider
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
    private val merchantApiKeyProvider: MerchantApiKeyProvider,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accountValidationProvider: AccountValidationProvider
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/api/v1/p/merchant/**")
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

    @Bean
    fun userSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher("/api/v1/p/user/**")
            .csrf { it.disable() }
            .cors { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .addFilterBefore(
                UserApiAuthenticationFilter(accountValidationProvider = accountValidationProvider),
                UsernamePasswordAuthenticationFilter::class.java
            ).build()
}

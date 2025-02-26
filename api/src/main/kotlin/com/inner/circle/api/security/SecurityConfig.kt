package com.inner.circle.api.security

import com.inner.circle.core.security.AccountValidationProvider
import com.inner.circle.core.security.MerchantApiKeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val merchantApiKeyProvider: MerchantApiKeyProvider,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accountValidationProvider: AccountValidationProvider
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher("/api/v1/p/merchant/**")
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        CorsUtils::isPreFlightRequest
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(
                MerchantApiKeyAuthenticationFilter(
                    provider = merchantApiKeyProvider,
                    authenticationEntryPoint = authenticationEntryPoint
                ),
                UsernamePasswordAuthenticationFilter::class.java
            ).build()

    @Bean
    fun userSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher("/api/v1/p/user/**")
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        CorsUtils::isPreFlightRequest,
                        AntPathRequestMatcher("/api/v1/p/user/sign-in"),
                        AntPathRequestMatcher("/api/v1/p/user/sign-up")
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(
                UserApiAuthenticationFilter(
                    provider = accountValidationProvider,
                    authenticationEntryPoint = authenticationEntryPoint
                ),
                UsernamePasswordAuthenticationFilter::class.java
            ).build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
        configuration.allowedHeaders = listOf("*")
        configuration.exposedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

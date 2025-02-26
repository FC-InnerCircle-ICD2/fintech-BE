package com.inner.circle.apibackoffice.security

import com.inner.circle.corebackoffice.security.MerchantApiKeyProvider
import com.inner.circle.corebackoffice.security.MerchantDetailService
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
    private val merchantDetailService: MerchantDetailService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher("/api/backoffice/v1/**")
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        CorsUtils::isPreFlightRequest,
                        AntPathRequestMatcher("/api/backoffice/v1/sign-in"),
                        AntPathRequestMatcher("/api/backoffice/v1/sign-up")
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(
                MerchantApiKeyAuthenticationFilter(
                    detailService = merchantDetailService,
                    authenticationEntryPoint = authenticationEntryPoint
                ),
                UsernamePasswordAuthenticationFilter::class.java
            ).addFilterBefore(
                MerchantDetailAuthenticationFilter(
                    provider = merchantApiKeyProvider,
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

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

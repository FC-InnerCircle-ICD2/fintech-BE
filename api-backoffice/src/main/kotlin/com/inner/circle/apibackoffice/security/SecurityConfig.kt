package com.inner.circle.apibackoffice.security

import com.inner.circle.corebackoffice.security.MerchantApiKeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val merchantApiKeyProvider: MerchantApiKeyProvider
) {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher("/api/backoffice/v1/**")
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .addFilterBefore(
                AuthenticationExceptionHandlingFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            ).addFilterBefore(
                MerchantApiKeyAuthenticationFilter(
                    merchantApiKeyProvider
                ),
                UsernamePasswordAuthenticationFilter::class.java
            ).build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("authorization", "content-type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

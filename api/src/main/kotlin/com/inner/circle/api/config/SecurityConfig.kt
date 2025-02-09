package com.inner.circle.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig{
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain =
        // TODO - Payment / User API Path 가 나누어 지면 Security 재 설정 필요
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/health-check",
                        "/**", // TODO - 추후 제거가 필요, PREFIX 제대로 나누기 전 임시 처리
                    ).permitAll()
            }.sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()
}

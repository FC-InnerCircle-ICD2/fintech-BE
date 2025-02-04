package com.inner.circle.api.security

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.ASYNC) // 오류 페이지, 비동기 요청 허용
                    .permitAll()
                    .requestMatchers(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/health-check",
                        "/api/payment/v1/sse/**"
                    ).permitAll() // swagger, sse 연결은 permit
                    .requestMatchers("/api/payment/v1/payments", "/api/payment/v1/payments/**")
                    .authenticated()
            }.sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.httpBasic { it.disable() }
            .csrf { it.disable() }
            .httpBasic(withDefaults())

        return http.build()
    }
}

package com.inner.circle.api.security

import com.inner.circle.core.security.SecurityAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val securityAuthenticationProvider: SecurityAuthenticationProvider
) {
//    @Bean
//    fun authenticationManager(auth: AuthenticationManagerBuilder): AuthenticationManager {
//        auth.authenticationProvider(securityAuthenticationProvider)
//        return auth.build()
//    }

    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests{authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/v1/**").authenticated()
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic(withDefaults()) // basic authentication 활성화
        return http.build()
    }

}

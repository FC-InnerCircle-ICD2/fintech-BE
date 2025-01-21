package com.inner.circle.core.security

import com.inner.circle.infra.structure.repository.MerchantRepository
import org.springframework.core.env.Environment
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailsService(
    private val repository: MerchantRepository,
    private val environment: Environment
) : UserDetailsService {
    override fun loadUserByUsername(token: String): UserDetails {
        if (environment.activeProfiles.contains("local") && "pay200".equals(token)) {
            return User
                .withUsername(token)
                .password("{noop}pay200")
                .authorities("ROLE_PAY200")
                .build()
        }

        val validMerchant =
            repository.findByToken(token) ?: throw IllegalArgumentException("잘못된 접근입니다.")

        return User
            .withUsername(token)
            .password(validMerchant.password)
            .authorities(validMerchant.authorities)
            .build()
    }
}

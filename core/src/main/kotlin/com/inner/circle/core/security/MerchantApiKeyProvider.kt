package com.inner.circle.core.security

import com.inner.circle.infra.security.MerchantDetailService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class MerchantApiKeyProvider(
    private val merchantDetailsService: MerchantDetailService
) : CustomSecurityProvider {
    override fun getAuthentication(token: String): Authentication {
        val userDetails = merchantDetailsService.loadUserByUsername(token)
        val authentication =
            UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                listOf(
                    SimpleGrantedAuthority("ROLE_MERCHANT")
                )
            )
        return authentication
    }
}

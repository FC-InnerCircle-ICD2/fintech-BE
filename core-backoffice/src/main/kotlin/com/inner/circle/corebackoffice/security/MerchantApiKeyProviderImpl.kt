package com.inner.circle.corebackoffice.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class MerchantApiKeyProviderImpl(
    private val merchantDetailsService: MerchantDetailService
) : MerchantApiKeyProvider {
    override fun getAuthentication(secret: String): Authentication {
        val userDetails = merchantDetailsService.loadUserByUsername(secret)
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

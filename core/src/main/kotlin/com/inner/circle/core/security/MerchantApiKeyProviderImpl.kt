package com.inner.circle.core.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class MerchantApiKeyProviderImpl(
    private val merchantDetailsService: MerchantDetailService
) : MerchantApiKeyProvider {
    override fun getAuthentication(secret: String): Authentication {
        if (secret == "pay200") {
            val mockMerchantUserDetails =
                MerchantUserDetails(
                    id = 678467526895083491,
                    email = "test@gmail.com",
                    password = "qwer1234",
                    name = "테스트 상점"
                )
            return UsernamePasswordAuthenticationToken(
                mockMerchantUserDetails,
                "",
                listOf(
                    SimpleGrantedAuthority("ROLE_MERCHANT")
                )
            )
        }
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

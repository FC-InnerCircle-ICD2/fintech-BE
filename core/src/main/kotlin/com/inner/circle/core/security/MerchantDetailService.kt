package com.inner.circle.core.security

import com.inner.circle.infra.port.MerchantHandlePort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MerchantDetailService(
    private val merchantHandlePort: MerchantHandlePort
) : UserDetailsService {
    override fun loadUserByUsername(token: String): UserDetails {
        val merchant =
            merchantHandlePort.findMerchantByKey(token)

        val userDetails =
            MerchantUserDetails(
                id = merchant.id,
                username = merchant.email,
                password = merchant.password,
                name = merchant.name
            )
        return userDetails
    }
}

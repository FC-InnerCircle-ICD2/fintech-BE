package com.inner.circle.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MerchantUserDetails(
    private val id: String,
    private val username: String,
    private val password: String,
    private val name: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_CLIENT"))

    override fun getUsername(): String = username

    override fun getPassword(): String = password
}

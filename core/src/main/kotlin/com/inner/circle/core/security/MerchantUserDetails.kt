package com.inner.circle.core.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MerchantUserDetails(
    private val id: Long,
    private val username: String,
    private val password: String,
    private val name: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_MERCHANT"))

    override fun getUsername(): String = username

    override fun getPassword(): String = password

    fun getId(): Long = id

    fun getName(): String = name
}

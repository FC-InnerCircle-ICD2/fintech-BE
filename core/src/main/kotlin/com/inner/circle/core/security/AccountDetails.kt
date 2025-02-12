package com.inner.circle.core.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AccountDetails(
    val id: Long,
    private val userName: String,
    private val userPassword: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getPassword(): String = this.userPassword

    override fun getUsername(): String = this.userName
}

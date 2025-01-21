package com.inner.circle.infra.structure.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "merchant")
data class MerchantEntity(
    @Id
    val id: String,
    @Column(nullable = false, unique = true)
    val mid: String,
    @Column(nullable = false, unique = true)
    val token: String,
    @Column(nullable = false)
    val name: String
) : BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_MERCHANT"))
    }

    override fun getPassword(): String {
        return mid
    }

    override fun getUsername(): String {
        return token
    }
}

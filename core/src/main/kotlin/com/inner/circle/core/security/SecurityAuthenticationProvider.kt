package com.inner.circle.core.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class SecurityAuthenticationProvider(
    private val userDetailsService: UserDetailsService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val userDetails = userDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(
            userDetails.username,
            null,
            userDetails.authorities
        )
    }

    override fun supports(authentication: Class<*>?): Boolean =
        UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
}

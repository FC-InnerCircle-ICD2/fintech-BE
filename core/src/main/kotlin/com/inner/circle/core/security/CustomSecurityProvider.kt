package com.inner.circle.core.security

import org.springframework.security.core.Authentication

interface CustomSecurityProvider {
    fun getAuthentication(secret: String): Authentication
}

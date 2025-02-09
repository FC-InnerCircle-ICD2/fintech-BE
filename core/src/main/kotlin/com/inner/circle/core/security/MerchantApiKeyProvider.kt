package com.inner.circle.core.security

import org.springframework.security.core.Authentication

interface MerchantApiKeyProvider {
    fun getAuthentication(secret: String): Authentication
}

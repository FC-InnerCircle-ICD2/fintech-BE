package com.inner.circle.core.security

import org.springframework.security.core.Authentication

fun interface MerchantApiKeyProvider {
    fun getAuthentication(secret: String): Authentication
}

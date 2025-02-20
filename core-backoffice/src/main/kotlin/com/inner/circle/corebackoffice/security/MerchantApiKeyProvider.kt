package com.inner.circle.corebackoffice.security

import org.springframework.security.core.Authentication

fun interface MerchantApiKeyProvider {
    fun getAuthentication(secret: String): Authentication
}

package com.inner.circle.corebackoffice.security

import org.springframework.security.core.Authentication

fun interface MerchantApiKeyProvider {
    fun getMerchantValidAuthenticationOrThrow(secret: String): Authentication
}

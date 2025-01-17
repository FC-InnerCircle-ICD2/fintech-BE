package com.inner.circle.corebackoffice.util

import java.security.SecureRandom
import java.util.*

object ClientCredentialsGenerator {
    private val secureRandom = SecureRandom()
    private val base64Encoder = Base64.getUrlEncoder()

    fun generateClientId(): String {
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        return base64Encoder.encodeToString(randomBytes)
    }

    fun generateClientSecret(): String {
        val randomBytes = ByteArray(32)
        secureRandom.nextBytes(randomBytes)
        return base64Encoder.encodeToString(randomBytes)
    }
}

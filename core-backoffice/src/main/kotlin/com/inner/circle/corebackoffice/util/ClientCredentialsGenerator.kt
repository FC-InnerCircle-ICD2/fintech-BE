package com.inner.circle.corebackoffice.util

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

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

        val timestamp = System.nanoTime().toString()
        val input = randomBytes.joinToString("") + timestamp
        val hash = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return base64Encoder.encodeToString(hash)
    }
}

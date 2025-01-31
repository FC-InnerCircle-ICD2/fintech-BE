package com.inner.circle.corebackoffice.util

interface ClientCredentialsGenerator {
    fun generateClientId(): String

    fun generateClientSecret(): String
}

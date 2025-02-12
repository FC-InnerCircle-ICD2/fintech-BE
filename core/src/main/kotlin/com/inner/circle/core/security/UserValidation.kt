package com.inner.circle.core.security

import org.springframework.security.core.Authentication

fun interface UserValidation {
    fun getUserValidAuthenticationOrThrow(token: String): Authentication
}

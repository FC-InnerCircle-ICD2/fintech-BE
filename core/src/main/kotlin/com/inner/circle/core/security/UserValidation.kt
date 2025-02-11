package com.inner.circle.core.security

fun interface UserValidation {
    fun validateUserOrThrow(token: String)
}

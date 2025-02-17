package com.inner.circle.core.usecase

fun interface UserSignUpUseCase {
    data class UserSignUpInfo(
        val email: String,
        val password: String
    )

    fun signUp(account: UserSignUpInfo)
}

package com.inner.circle.api.controller.request

import com.inner.circle.core.usecase.UserLoginUseCase

data class UserLoginRequest(
    val email: String,
    val password: String
) {
    companion object {
        fun from(userLoginRequest: UserLoginRequest): UserLoginUseCase.UserLoginInfo =
            UserLoginUseCase.UserLoginInfo(
                email = userLoginRequest.email,
                password = userLoginRequest.password,
            )
    }
}

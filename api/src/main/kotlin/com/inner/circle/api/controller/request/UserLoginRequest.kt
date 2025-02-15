package com.inner.circle.api.controller.request

import com.inner.circle.core.usecase.UserLoginUseCase
import jakarta.validation.constraints.NotBlank

data class UserLoginRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
) {
    companion object {
        fun from(userLoginRequest: UserLoginRequest): UserLoginUseCase.UserLoginInfo =
            UserLoginUseCase.UserLoginInfo(
                email = userLoginRequest.email,
                password = userLoginRequest.password
            )
    }
}

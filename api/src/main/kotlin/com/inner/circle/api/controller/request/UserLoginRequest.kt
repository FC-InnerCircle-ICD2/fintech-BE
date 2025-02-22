package com.inner.circle.api.controller.request

import com.inner.circle.core.usecase.UserLoginUseCase
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserLoginRequest(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    @field:Size(min = 5, max = 20)
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$")
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

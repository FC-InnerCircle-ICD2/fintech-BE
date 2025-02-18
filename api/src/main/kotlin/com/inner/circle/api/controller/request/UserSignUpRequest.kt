package com.inner.circle.api.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserSignUpRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    @field:Size(min = 5, max = 20, message = "비밀번호는 영문자+숫자 5자 이상 20자 이하로 설정 하셔야 합니다")
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$", message = "비밀번호는 숫자와 영문자만 포함해야 합니다.")
    val password: String
)

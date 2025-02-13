package com.inner.circle.api.controller.user

import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.dto.UserLoginResponse
import com.inner.circle.api.controller.request.UserLoginRequest
import com.inner.circle.core.usecase.TokenHandlerUseCase
import com.inner.circle.core.usecase.UserLoginUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserLoginController(
    private val userLoginUseCase: UserLoginUseCase,
    private val tokenHandlerUseCase: TokenHandlerUseCase,
) {
    @PostMapping("/login")
    fun userLogin(
        @RequestBody userLoginRequest: UserLoginRequest
    ): PaymentResponse<UserLoginResponse> =
        userLoginUseCase.findValidAccountOrThrow(
            loginInfo = UserLoginRequest.from(userLoginRequest = userLoginRequest)
        ).run {
            tokenHandlerUseCase.generateTokenBy(
                keyString = this.id.toString(),
                data = this,
            )
        }.let {
            PaymentResponse.ok(
                data = UserLoginResponse(
                    accessToken = it,
                )
            )
        }
}

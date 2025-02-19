package com.inner.circle.api.controller.user

import com.inner.circle.api.config.SwaggerConfig
import com.inner.circle.api.controller.PaymentForUserV1Api
import com.inner.circle.api.controller.dto.PaymentResponse
import com.inner.circle.api.controller.dto.UserInfoResponse
import com.inner.circle.api.controller.dto.UserLoginResponse
import com.inner.circle.api.controller.request.UserLoginRequest
import com.inner.circle.api.controller.request.UserSignUpRequest
import com.inner.circle.core.security.AccountDetails
import com.inner.circle.core.usecase.TokenHandlerUseCase
import com.inner.circle.core.usecase.UserLoginUseCase
import com.inner.circle.core.usecase.UserSignUpUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "User - Account", description = "고객(App) 관련 API")
@PaymentForUserV1Api
class UserLoginController(
    private val userLoginUseCase: UserLoginUseCase,
    private val userSignUpUseCase: UserSignUpUseCase,
    private val tokenHandlerUseCase: TokenHandlerUseCase
) {
    @PostMapping("/sign-in")
    @Operation(description = "결제 고객 로그인 API")
    fun userLogin(
        @RequestBody @Valid userLoginRequest: UserLoginRequest
    ): PaymentResponse<UserLoginResponse> =
        userLoginUseCase
            .findValidAccountOrThrow(
                loginInfo = UserLoginRequest.from(userLoginRequest = userLoginRequest)
            ).run {
                tokenHandlerUseCase.generateTokenBy(
                    keyString = this.id.toString(),
                    data = this
                )
            }.let {
                PaymentResponse.ok(
                    data =
                        UserLoginResponse(
                            accessToken = it
                        )
                )
            }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "결제 고객 회원 가입 API")
    fun userSingUp(
        @RequestBody @Valid userSignUpRequest: UserSignUpRequest
    ) {
        userSignUpUseCase.signUp(
            account =
                UserSignUpUseCase.UserSignUpInfo(
                    email = userSignUpRequest.email,
                    password = userSignUpRequest.password
                )
        )
    }

    @GetMapping("/me")
    @SecurityRequirement(name = SwaggerConfig.BEARER_AUTH)
    fun getCurrentUserInfo(
        @AuthenticationPrincipal user: AccountDetails
    ): UserInfoResponse = UserInfoResponse.from(userInfo = user)
}

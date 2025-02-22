package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.BackofficeResponse
import com.inner.circle.apibackoffice.controller.dto.MerchantDto
import com.inner.circle.apibackoffice.controller.dto.MerchantSignInDto
import com.inner.circle.apibackoffice.controller.request.SignInMerchantRequest
import com.inner.circle.apibackoffice.controller.request.SignUpMerchantRequest
import com.inner.circle.corebackoffice.usecase.MerchantSaveUseCase
import com.inner.circle.corebackoffice.usecase.MerchantSignInUseCase
import com.inner.circle.corebackoffice.usecase.TokenHandlerUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
class MerchantController(
    private val merchantSaveUseCase: MerchantSaveUseCase,
    private val merchantSignInUseCase: MerchantSignInUseCase,
    private val tokenHandlerUseCase: TokenHandlerUseCase
) {
    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody @Valid request: SignUpMerchantRequest
    ): BackofficeResponse<MerchantDto> {
        val response =
            MerchantDto.of(
                merchantSaveUseCase.save(
                    MerchantSaveUseCase.Request(
                        email = request.email,
                        password = request.password,
                        name = request.name
                    )
                )
            )
        return BackofficeResponse.ok(response)
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInMerchantRequest
    ): BackofficeResponse<MerchantSignInDto> {
        val merchant =
            merchantSignInUseCase
                .signIn(
                    MerchantSignInUseCase.Request(
                        email = request.email,
                        password = request.password
                    )
                )
        val token =
            tokenHandlerUseCase.generateTokenBy(
                keyString = merchant.id.toString(),
                data = merchant
            )
        return BackofficeResponse.ok(
            data =
                MerchantSignInDto(
                    accessToken = token,
                    id = merchant.id.toString(),
                    name = merchant.name
                )
        )
    }
}

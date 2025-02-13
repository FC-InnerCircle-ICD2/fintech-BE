package com.inner.circle.apibackoffice.controller

import com.inner.circle.apibackoffice.controller.dto.MerchantDto
import com.inner.circle.apibackoffice.controller.request.SignUpMerchantRequest
import com.inner.circle.apibackoffice.exception.BackofficeResponse
import com.inner.circle.corebackoffice.usecase.MerchantSaveUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Payment", description = "Payment API")
@BackofficeV1Api
class MerchantController(
    private val merchantSaveUseCase: MerchantSaveUseCase
) {
    @Operation(summary = "회원가입")
    @PostMapping("/users")
    fun signUp(
        @RequestBody request: SignUpMerchantRequest
    ): BackofficeResponse<MerchantDto> {
        val response = MerchantDto.of(
            merchantSaveUseCase.saveMerchant(
                MerchantSaveUseCase.Request(
                    username = request.username,
                    password = request.password,
                    name = request.name
                )
            )
        )
        return BackofficeResponse.ok(response)
    }

    @Operation(summary = "로그인")
    @PostMapping("users/login")
    fun signIn() {

    }
}

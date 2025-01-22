package com.inner.circle.api.controller

import com.inner.circle.api.interceptor.RequireAuth
import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Claims", description = "Claim (결제 요청) API")
@PaymentV1Api
class ClaimController(
    private val claimUseCase: PaymentClaimUseCase
) {
    @Operation(summary = "결제 요청")
    @RequireAuth
    @PostMapping
    fun createPayment(
        @RequestBody request: PaymentClaimUseCase.PaymentClaimRequest
    ): PaymentResponse<PaymentClaimResponse> {
        val merchantId = "tempMerchantId" // @AuthenticationPrincipal
        val response = claimUseCase.createPayment(request, merchantId)
        return PaymentResponse.ok(response)
    }
}

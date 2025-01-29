package com.inner.circle.api.controller

import com.inner.circle.api.structure.dto.PaymentResponse
import com.inner.circle.core.usecase.PaymentClaimUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Claims", description = "Claim (결제 요청) API")
@PaymentV1Api
class PaymentClaimController(
    private val claimUseCase: PaymentClaimUseCase
) {
    @Operation(summary = "결제 요청")
    @PostMapping
    fun createPayment(
        @RequestBody request: PaymentClaimUseCase.PaymentClaimRequest
    ): PaymentResponse<PaymentClaimUseCase.PaymentClaimResponse> {
        val merchantId = "tempMerchantId" // @AuthenticationPrincipal
        val response = claimUseCase.createPayment(request, merchantId)
        return PaymentResponse.ok(response)
    }
}

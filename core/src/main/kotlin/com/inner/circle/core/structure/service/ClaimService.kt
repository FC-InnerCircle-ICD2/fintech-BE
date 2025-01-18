package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.domain.PaymentRequest
import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import org.springframework.stereotype.Service

@Service
class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort
) : PaymentClaimUseCase {
    override fun createPayment(
        request: PaymentClaimUseCase.PaymentClaimRequest,
        merchantId: String
    ): PaymentClaimResponse {
        val createdPaymentRequest = PaymentRequest.fromClaim(request, merchantId)
        // request -> PaymentRequest
        // claimHandlingPort 를 통해 저장 처리 들어감
        // 반환값으로 response 구성
        //        PaymentRequest

        val generatePaymentRequest = claimHandlingPort.generatePaymentRequest(createdPaymentRequest)
        return PaymentClaimResponse
    }
}

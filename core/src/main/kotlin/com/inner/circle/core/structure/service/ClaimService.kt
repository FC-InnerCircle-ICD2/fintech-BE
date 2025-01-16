package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.domain.PaymentRequest
import com.inner.circle.core.structure.usecase.PaymentClaimRequest
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort
) {
    fun createPayment(
        request: PaymentClaimRequest,
        merchantId: String
    ): MultiValueMap<String, String>? {
        val createdPaymentRequest = PaymentRequest.fromClaim(request, merchantId)
        // request -> PaymentRequest
        // claimHandlingPort 를 통해 저장 처리 들어감
        // 반환값으로 response 구성
//        PaymentRequest

        val generatePaymentRequest = claimHandlingPort.generatePaymentRequest(createdPaymentRequest)
        return null
    }
}

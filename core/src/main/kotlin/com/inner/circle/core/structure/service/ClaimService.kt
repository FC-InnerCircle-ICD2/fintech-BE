package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.usecase.PaymentClaimRequest
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort
) {
    fun createPayment(request: PaymentClaimRequest): MultiValueMap<String, String> {
        // request -> PaymentRequest
        // claimHandlingPort 를 통해 저장 처리 들어감
        // 반환값으로 response 구성
//        PaymentRequest

        val generatePaymentRequest = claimHandlingPort.generatePaymentRequest()
//        return generatePaymentRequest
    }
}

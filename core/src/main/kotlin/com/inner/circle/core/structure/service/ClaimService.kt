package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import com.inner.circle.infra.structure.adaptor.PaymentProcessStatus
import com.inner.circle.infra.structure.adaptor.dto.PaymentRequestDto
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort
) : PaymentClaimUseCase {
    override fun createPayment(
        request: PaymentClaimUseCase.PaymentClaimRequest,
        requestMerchantId: String
    ): PaymentClaimResponse {
        val (amount, orderId, orderName, successUrl, failUrl) = request

        val requestDto =
            PaymentRequestDto(
                orderId = orderId,
                orderName = orderName,
                orderStatus = PaymentProcessStatus.READY,
                merchantId = requestMerchantId,
                paymentKey = null,
                amount = amount,
                requestTime = LocalDateTime.now(),
                successUrl = successUrl,
                failUrl = failUrl
            )

        val generatePaymentRequest = claimHandlingPort.generatePaymentRequest(requestDto)

        return PaymentClaimResponse.testOne()
    }
}

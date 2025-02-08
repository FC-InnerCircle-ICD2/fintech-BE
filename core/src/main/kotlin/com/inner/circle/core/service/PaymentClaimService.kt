package com.inner.circle.core.service

import com.inner.circle.core.usecase.PaymentClaimUseCase
import com.inner.circle.infra.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.adaptor.dto.PaymentProcessStatus
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentClaimHandlingPort
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import org.springframework.stereotype.Service

private const val PAYMENT_REQUEST_EXPIRED_MINUTES = 10L

@Service
class PaymentClaimService(
    private val paymentClaimHandlingPort: PaymentClaimHandlingPort,
    private val jwtHandler: JwtHandler
) : PaymentClaimUseCase {
    override fun createPayment(
        request: PaymentClaimUseCase.ClaimRequest,
        merchantId: String
    ): PaymentClaimUseCase.PaymentClaimResponse {
        val (amount, orderId, orderName) = request
        val requestDto =
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = orderName,
                orderStatus = PaymentProcessStatus.READY,
                merchantId = merchantId,
                paymentKey = null,
                paymentType = null,
                cardNumber = null,
                amount = amount,
                requestTime = LocalDateTime.now(),
                paymentToken = null
            )

        val issuedAt = Date()
        val jwtToken =
            jwtHandler.generateToken(
                paymentClaimDto = requestDto,
                issuedAt = issuedAt,
                expiresMinute = PAYMENT_REQUEST_EXPIRED_MINUTES.toInt()
            )
        val jwtExpiresAt =
            LocalDateTime
                .ofInstant(
                    issuedAt.toInstant(),
                    ZoneId.systemDefault()
                ).plusMinutes(PAYMENT_REQUEST_EXPIRED_MINUTES)

        val paymentTokenDto =
            PaymentTokenDto(
                merchantId = requestDto.merchantId,
                orderId = requestDto.orderId,
                generatedToken = jwtToken,
                expiresAt = jwtExpiresAt
            )

        paymentClaimHandlingPort.createAndSavePaymentRequest(
            requestDto,
            paymentTokenDto
        )

        return PaymentClaimUseCase.PaymentClaimResponse.of(jwtToken, jwtExpiresAt)
    }
}

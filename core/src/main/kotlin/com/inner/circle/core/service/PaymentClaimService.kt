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

private const val ORDER_EXPIRED_MINUTES = 10L
private const val JWT_EXPIRATION_MINUTES = 5L

@Service
class PaymentClaimService(
    private val paymentClaimHandlingPort: PaymentClaimHandlingPort,
    private val jwtHandler: JwtHandler
) : PaymentClaimUseCase {
    override fun createPayment(
        request: PaymentClaimUseCase.PaymentClaimRequest,
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
                requestDto,
                issuedAt,
                JWT_EXPIRATION_MINUTES.toInt()
            )
        val jwtExpiresAt =
            LocalDateTime
                .ofInstant(
                    issuedAt.toInstant(),
                    ZoneId.systemDefault()
                ).plusMinutes(JWT_EXPIRATION_MINUTES)

        val paymentTokenDto =
            PaymentTokenDto(
                merchantId = requestDto.merchantId,
                orderId = requestDto.orderId,
                generatedToken = jwtToken,
                expiresAt = jwtExpiresAt
            )

        paymentClaimHandlingPort.generatePaymentRequest(
            requestDto,
            paymentTokenDto
        )

        return PaymentClaimUseCase.PaymentClaimResponse.of(jwtToken, jwtExpiresAt)
    }
}

package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.dto.PaymentClaimResponse
import com.inner.circle.core.structure.usecase.PaymentClaimUseCase
import com.inner.circle.infra.structure.adaptor.dto.PaymentClaimDto
import com.inner.circle.infra.structure.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.structure.adaptor.enum.PaymentProcessStatus
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import org.springframework.stereotype.Service

private const val ORDER_EXPIRED_MINUTES = 10L
private const val JWT_EXPIRATION_MINUTES = 5L

@Service
class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort,
    private val paymentTokenGenerator: PaymentTokenGenerator,
    private val jwtHandler: JwtHandler
) : PaymentClaimUseCase {
    override fun createPayment(
        request: PaymentClaimUseCase.PaymentClaimRequest,
        requestMerchantId: String
    ): PaymentClaimResponse {
        val (amount, orderId, orderName, successUrl, failUrl) = request
        val requestDto =
            PaymentClaimDto(
                paymentRequestId = null,
                orderId = orderId,
                orderName = orderName,
                orderStatus = PaymentProcessStatus.READY,
                merchantId = requestMerchantId,
                paymentKey = null,
                amount = amount,
                requestTime = LocalDateTime.now(),
                successUrl = successUrl,
                failUrl = failUrl,
                paymentToken = null
            )

        val (token, expiresAt) = paymentTokenGenerator.generateToken(orderId, ORDER_EXPIRED_MINUTES)

        val paymentTokenDto =
            PaymentTokenDto(
                merchantId = requestDto.merchantId,
                orderId = requestDto.orderId,
                generatedToken = token,
                expiresAt = expiresAt
            )

        val generatePaymentRequest =
            claimHandlingPort.generatePaymentRequest(
                requestDto,
                paymentTokenDto
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

        return PaymentClaimResponse.of(jwtToken, jwtExpiresAt)
    }
}

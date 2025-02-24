package com.inner.circle.core.service

import com.inner.circle.core.service.dto.PaymentTokenHandleDto
import com.inner.circle.core.usecase.PaymentTokenHandlingUseCase
import com.inner.circle.exception.PaymentJwtException
import com.inner.circle.infra.port.PaymentTokenHandlingPort
import org.springframework.stereotype.Service

@Service
class PaymentTokenHandleService(
    private val paymentTokenHandlingPort: PaymentTokenHandlingPort,
    private val jwtHandler: JwtHandler
) : PaymentTokenHandlingUseCase {
    override fun findPaymentToken(token: String): PaymentTokenHandleDto {
        val paymentData =
            paymentTokenHandlingPort.getMerchantIdAndOrderIdFromPaymentToken(token)

        val merchantId = paymentData.merchantId
        val orderId = paymentData.orderId
        val validateToken =
            jwtHandler.validateToken(
                token,
                paymentData.signature
            )

        if (!validateToken) {
            throw PaymentJwtException.TokenInvalidException(
                "결제 토큰이 유효하지 않습니다. ($token)"
            )
        }

        return PaymentTokenHandleDto(
            merchantId = merchantId,
            orderId = orderId,
            generatedToken = token
        )
    }

    override fun checkPaymentStatus(
        merchantId: String,
        orderId: String
    ): Boolean {
        val checkPaymentStatus = paymentTokenHandlingPort.checkPaymentStatus(merchantId, orderId)
        return checkPaymentStatus.isNotBlank()
    }
}

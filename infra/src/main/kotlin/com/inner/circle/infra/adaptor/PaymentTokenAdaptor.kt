package com.inner.circle.infra.adaptor

import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentTokenHandlingPort
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import org.springframework.stereotype.Component

@Component
class PaymentTokenAdaptor(
    private val paymentTokenRepository: PaymentTokenRepository
) : PaymentTokenHandlingPort {
    override fun getMerchantIdAndOrderIdFromPaymentToken(token: String): PaymentTokenDto {
        val paymentDataFromToken = paymentTokenRepository.getPaymentDataFromToken(token)
        return PaymentTokenDto(
            merchantId = paymentDataFromToken.merchantId,
            orderId = paymentDataFromToken.orderId,
            generatedToken = paymentDataFromToken.generatedToken,
            signature = paymentDataFromToken.signature,
            expiredAt = null
        )
    }

    override fun deletePaymentToken(token: String) {
        paymentTokenRepository.removePaymentDataByToken(token)
    }
}

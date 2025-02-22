package com.inner.circle.infra.adaptor

import com.google.common.hash.Hashing
import com.inner.circle.infra.adaptor.dto.PaymentTokenDto
import com.inner.circle.infra.port.PaymentTokenHandlingPort
import com.inner.circle.infra.repository.entity.PaymentTokenRepository
import java.nio.charset.StandardCharsets
import org.springframework.stereotype.Component

@Component
class PaymentTokenAdaptor(
    private val paymentTokenRepository: PaymentTokenRepository
) : PaymentTokenHandlingPort {
    override fun getMerchantIdAndOrderIdFromPaymentToken(token: String): PaymentTokenDto {
        val hashedToken = Hashing.murmur3_128().hashString(token, StandardCharsets.UTF_8).toString()
        val paymentDataFromToken = paymentTokenRepository.getPaymentDataFromToken(hashedToken)
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

    override fun checkPaymentStatus(
        merchantId: String,
        orderId: String
    ): String = paymentTokenRepository.checkPaymentInProgress(merchantId, orderId).orEmpty()
}

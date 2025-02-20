package com.inner.circle.infra.port

import com.inner.circle.infra.adaptor.dto.PaymentTokenDto

interface PaymentTokenHandlingPort {
    fun getMerchantIdAndOrderIdFromPaymentToken(token: String): PaymentTokenDto

    fun deletePaymentToken(token: String)

    fun checkPaymentStatus(
        merchantId: String,
        orderId: String
    ): String
}

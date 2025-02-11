package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentTokenHandleDto

interface TokenHandlingUseCase {
    fun findPaymentToken(token: String): PaymentTokenHandleDto {
        TODO("Not yet implemented")
    }
}

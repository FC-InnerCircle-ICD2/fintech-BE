package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.PaymentTokenHandleDto

interface PaymentTokenHandlingUseCase {
    fun findPaymentToken(token: String): PaymentTokenHandleDto
}

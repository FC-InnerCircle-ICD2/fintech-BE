package com.inner.circle.core.usecase

import com.inner.circle.core.service.dto.MerchantDto

fun interface MerchantUserHandleUseCase {
    fun findMerchantUser(token: String): MerchantDto
}

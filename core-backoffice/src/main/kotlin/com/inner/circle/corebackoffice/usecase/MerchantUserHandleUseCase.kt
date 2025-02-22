package com.inner.circle.corebackoffice.usecase

import com.inner.circle.corebackoffice.service.dto.MerchantDto

fun interface MerchantUserHandleUseCase {
    fun findMerchantUser(token: String): MerchantDto
}

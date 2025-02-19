package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.MerchantEntity

fun interface MerchantRepository {
    fun findById(merchantId: Long): MerchantEntity
}

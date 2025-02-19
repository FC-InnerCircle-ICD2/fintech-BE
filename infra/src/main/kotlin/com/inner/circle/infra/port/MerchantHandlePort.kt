package com.inner.circle.infra.port

import com.inner.circle.infra.repository.entity.MerchantEntity

fun interface MerchantHandlePort {
    fun findMerchantByToken(token: String): MerchantEntity
}

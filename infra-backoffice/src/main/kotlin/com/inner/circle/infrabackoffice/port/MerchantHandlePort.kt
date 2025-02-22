package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

fun interface MerchantHandlePort {
    fun findMerchantByToken(token: String): MerchantEntity
}

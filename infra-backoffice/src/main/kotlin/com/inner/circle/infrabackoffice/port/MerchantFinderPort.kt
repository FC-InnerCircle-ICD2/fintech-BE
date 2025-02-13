package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

fun interface MerchantFinderPort {
    fun findByUsername(username: String): MerchantEntity?
}

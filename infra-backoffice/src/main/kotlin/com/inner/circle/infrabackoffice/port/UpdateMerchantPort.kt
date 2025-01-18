package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

fun interface UpdateMerchantPort {
    data class Request(
        val merchant: MerchantEntity
    )

    fun updateMerchant(request: Request): MerchantEntity
}

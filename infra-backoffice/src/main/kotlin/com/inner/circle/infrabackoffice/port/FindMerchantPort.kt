package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

fun interface FindMerchantPort {
    data class Request(
        val id: String
    )

    fun findById(request: Request): MerchantEntity
}

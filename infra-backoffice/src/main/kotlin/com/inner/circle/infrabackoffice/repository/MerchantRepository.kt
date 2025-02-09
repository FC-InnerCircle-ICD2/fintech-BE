package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findById(id: String): MerchantEntity

    fun save(merchant: MerchantEntity): MerchantEntity
}

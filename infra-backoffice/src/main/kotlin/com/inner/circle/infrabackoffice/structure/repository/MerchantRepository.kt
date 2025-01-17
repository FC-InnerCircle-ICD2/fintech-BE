package com.inner.circle.infrabackoffice.structure.repository

import com.inner.circle.infrabackoffice.structure.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findById(id: String): MerchantEntity

    fun save(merchant: MerchantEntity): MerchantEntity
}

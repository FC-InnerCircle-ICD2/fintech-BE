package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findById(id: Long): MerchantEntity

    fun existsByEmail(email: String): Boolean

    fun save(merchant: MerchantEntity): MerchantEntity

    fun findByEmailOrNull(email: String): MerchantEntity?

    fun findByIdOrNull(id: Long): MerchantEntity?
}

package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findById(id: String): MerchantEntity

    fun existsByEmail(email: String): Boolean

    fun save(merchant: MerchantEntity): MerchantEntity

    fun findByUsernameAndPassword(
        email: String,
        password: String
    ): MerchantEntity
}

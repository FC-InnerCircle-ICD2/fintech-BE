package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findById(id: String): MerchantEntity

    fun existsByUsername(username: String): Boolean

    fun save(merchant: MerchantEntity): MerchantEntity

    fun findByUsernameAndPassword(
        username: String,
        password: String
    ): MerchantEntity
}

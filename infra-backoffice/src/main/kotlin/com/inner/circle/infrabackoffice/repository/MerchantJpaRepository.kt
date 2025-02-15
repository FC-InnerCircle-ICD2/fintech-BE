package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, String> {
    fun existsByEmail(email: String): Boolean

    fun findByEmailAndPassword(
        email: String,
        password: String
    ): MerchantEntity?
}

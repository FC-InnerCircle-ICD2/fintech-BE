package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, Long> {
    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): MerchantEntity?
}

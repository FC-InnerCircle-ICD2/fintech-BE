package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, String> {
    fun existsByUsername(username: String): Boolean

    fun findByUsernameAndPassword(
        username: String,
        password: String
    ): MerchantEntity?
}

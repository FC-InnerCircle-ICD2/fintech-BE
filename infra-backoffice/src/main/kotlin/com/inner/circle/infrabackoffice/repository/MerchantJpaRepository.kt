package com.inner.circle.infrabackoffice.repository

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, String>

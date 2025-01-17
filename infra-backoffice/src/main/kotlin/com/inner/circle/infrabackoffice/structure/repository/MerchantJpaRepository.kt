package com.inner.circle.infrabackoffice.structure.repository

import com.inner.circle.infrabackoffice.structure.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, String>

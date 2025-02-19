package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantJpaRepository : JpaRepository<MerchantEntity, Long>

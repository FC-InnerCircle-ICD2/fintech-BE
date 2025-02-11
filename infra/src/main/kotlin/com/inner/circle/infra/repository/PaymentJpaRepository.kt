package com.inner.circle.infra.repository

import com.inner.circle.infra.repository.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, String>

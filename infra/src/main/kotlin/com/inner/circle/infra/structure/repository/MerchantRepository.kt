package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.MerchantEntity

interface MerchantRepository {
    fun findByToken(token: String): MerchantEntity?
}

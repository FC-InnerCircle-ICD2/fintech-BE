package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantFinderPort {
    fun existsByEmail(email: String): Boolean

    fun findByEmailOrNull(email: String): MerchantEntity?

    fun findByIdOrNull(id: Long): MerchantEntity?
}

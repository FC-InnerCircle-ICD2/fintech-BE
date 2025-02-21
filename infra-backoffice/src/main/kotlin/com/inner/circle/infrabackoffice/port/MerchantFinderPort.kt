package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantFinderPort {
    fun existsByEmail(email: String): Boolean

    fun findByUsernameAndPassword(
        email: String,
        password: String
    ): MerchantDto

    fun findByIdOrNull(id: Long): MerchantEntity?
}

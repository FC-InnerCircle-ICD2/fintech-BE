package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.MerchantDto

interface MerchantFinderPort {
    fun existsByEmail(email: String): Boolean

    fun findByUsernameAndPassword(
        email: String,
        password: String
    ): MerchantDto
}

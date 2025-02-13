package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantFinderPort {
    fun existByUsername(username: String): Boolean

    fun findByUsernameAndPassword(
        username: String,
        password: String
    ): MerchantEntity
}

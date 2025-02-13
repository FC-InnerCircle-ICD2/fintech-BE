package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity

interface MerchantFinderPort {
    fun findByUsername(username: String): MerchantEntity?

    fun findByUsernamePassword(
        username: String,
        password: String
    ): MerchantEntity
}

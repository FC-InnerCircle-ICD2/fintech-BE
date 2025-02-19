package com.inner.circle.infrabackoffice.port

import com.inner.circle.infrabackoffice.adaptor.dto.ApiKeyFinderDto

interface ApiKeyFinderPort {
    fun findByMerchantId(merchantId: Long): ApiKeyFinderDto
}

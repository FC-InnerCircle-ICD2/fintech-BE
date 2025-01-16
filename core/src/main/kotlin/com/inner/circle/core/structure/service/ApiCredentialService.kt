package com.inner.circle.core.structure.service

import org.springframework.stereotype.Service

@Service
class ApiCredentialService {
    fun getMerchantIdByApiKey(apiKey: String): String {
        // TODO: apiKey를 통해 merchantId를 조회하는 로직을 구현
        return "merchant123"
    }
}

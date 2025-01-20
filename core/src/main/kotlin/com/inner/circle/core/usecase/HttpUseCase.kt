package com.inner.circle.core.usecase

import java.io.Serializable


interface HttpUseCase {
    fun httpPostRequest(
        baseUrl: String,
        endPoint: String,
        params: Map<String, Any>
    ): Map<String, Any>
}

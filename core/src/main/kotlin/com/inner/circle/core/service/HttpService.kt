package com.inner.circle.core.service

import com.inner.circle.core.usecase.HttpUseCase
import com.inner.circle.infra.http.HttpClient
import java.io.Serializable
import org.springframework.stereotype.Service

@Service
internal class HttpService : HttpUseCase {
    override fun httpPostRequest(
        baseUrl: String,
        endPoint: String,
        params: Map<String, Any>
    ): Map<String, Any> {
        return HttpClient.sendPostRequest(baseUrl, endPoint, params)
    }
}

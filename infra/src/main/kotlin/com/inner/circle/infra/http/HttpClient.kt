package com.inner.circle.infra.http

import com.google.gson.Gson
import com.inner.circle.exception.CardCompanyException
import com.inner.circle.exception.PaymentException
import java.io.IOException
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

@Component
class HttpClient {
    private val log = LoggerFactory.getLogger(HttpClient::class.java)
    private val gson = Gson()

    @Retryable(
        value = [IOException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000)
    )
    fun sendPostRequest(
        baseUrl: String,
        endpoint: String,
        params: Map<String, Any>
    ): Map<String, Any> {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val jsonParams = gson.toJson(params)
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(mediaType, jsonParams)

        val apiService = retrofit.create(ApiService::class.java)
        val call: Call<ResponseBody> = apiService.sendPostRequest(endpoint, body)

        return executeCall(call)
    }

    private fun executeCall(call: Call<ResponseBody>): Map<String, Any> {
        try {
            val response: Response<ResponseBody> = call.execute()

            if (response.isSuccessful) {
                // 응답 본문을 동적으로 파싱하여 바로 반환
                val responseBody = response.body()?.string()
                responseBody?.let {
                    // JSON 응답을 Map으로 파싱
                    return gson.fromJson(it, Map::class.java) as Map<String, Any>
                } ?: throw NullPointerException("Response body is null")
            }
            throw CardCompanyException.ConnenctException(
                code = response.code(),
                msg = response.message()
            )
        } catch (e: IOException) {
            log.error("[ERROR] 네트워크 요청 실패: ${e.message}")
            throw PaymentException.CardAuthFailException()
        }
    }

    fun interface ApiService {
        @POST
        fun sendPostRequest(
            @Url url: String,
            @Body body: RequestBody
        ): Call<ResponseBody>
    }
}

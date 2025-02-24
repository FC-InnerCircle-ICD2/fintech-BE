package com.inner.circle.infra.http

import com.google.gson.Gson
import com.inner.circle.exception.CardCompanyException
import com.inner.circle.exception.PaymentException
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

class HttpClient {
    companion object {
        fun sendPostRequest(
            baseUrl: String,
            endpoint: String,
            params: Map<String, Any>
        ): Map<String, Any> { // 반환 타입을 Map으로 변경
            // Retrofit 객체 생성
            val retrofit =
                Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            // JSON으로 변환
            val jsonParams = Gson().toJson(params)
            val mediaType = MediaType.parse("application/json; charset=utf-8") // MediaType 생성
            val body = RequestBody.create(mediaType, jsonParams)

            // Retrofit의 API 서비스 생성
            val apiService = retrofit.create(ApiService::class.java)

            // 동적으로 URL을 받아서 POST 요청 보내기
            val call: Call<ResponseBody> = apiService.sendPostRequest(endpoint, body)

            try {
                val response: Response<ResponseBody> = call.execute()

                if (response.isSuccessful) {
                    // 응답 본문을 동적으로 파싱하여 바로 반환
                    val responseBody = response.body()?.string()
                    responseBody?.let {
                        // JSON 응답을 Map으로 파싱
                        return Gson().fromJson(it, Map::class.java) as Map<String, Any>
                    } ?: throw NullPointerException("Response body is null")
                } else {
                    throw CardCompanyException.ConnenctException(
                        code = response.code(),
                        msg = response.message()
                    )
                }
            } catch (e: Exception) {
                throw PaymentException.CardAuthFailException()
            }
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

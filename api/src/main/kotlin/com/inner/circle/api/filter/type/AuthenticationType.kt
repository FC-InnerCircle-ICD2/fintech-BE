package com.inner.circle.api.filter.type

enum class AuthenticationType(
    private val resource: String,
    private val description: String,
) {
    MERCHANT(
        resource = "merchant",
        description = "상점 API Validation (Basic 인증 방식 사용)",
    ),
    USER(
        resource = "user",
        description = "회원 관련 API (Bearer 인증 방식 사용)",
    ),
    SSE(
        resource = "sse",
        description = "SSE 연결 API",
    )
    ;
    companion object {
        fun of(type: String): AuthenticationType =
            when (type) {
                MERCHANT.resource -> MERCHANT
                USER.resource -> USER
                SSE.resource -> SSE
                else -> throw RuntimeException("존재하지 않는 타입입니다 ! Exception 처리 하기 !")
            }
    }
}

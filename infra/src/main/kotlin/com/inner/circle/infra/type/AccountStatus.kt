package com.inner.circle.infra.type

enum class AccountStatus(
    private val description: String
) {
    ACTIVE(
        description = "계정 사용 가능 상태"
    ),
    INACTIVE(
        description = "계정 비 활성화 상태"
    ),
    SUSPENDED(
        description = "계정 정지 상태"
    )
}

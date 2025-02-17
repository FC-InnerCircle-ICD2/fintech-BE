package com.inner.circle.infra.type

enum class AccountStatus(
    val code: Int,
    private val description: String,
) {
    ACTIVE(
        code = 0,
        description = "계정 사용 가능 상태",
    ),
    INACTIVE(
        code = 1,
        description = "계정 비 활성화 상태",
    ),
    SUSPENDED(
        code = 2,
        description = "계정 정지 상태",
    ),;

}

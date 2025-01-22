package com.inner.circle.core.usecase

fun interface RequestPaymentUseCase {
    data class Request(
        val userName: String,
        val amount: Long
    )

    fun payment(request: com.inner.circle.core.usecase.RequestPaymentUseCase.Request)
}

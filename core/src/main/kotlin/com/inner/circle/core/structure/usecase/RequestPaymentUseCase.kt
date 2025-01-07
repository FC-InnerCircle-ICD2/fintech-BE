package com.inner.circle.core.structure.usecase

fun interface RequestPaymentUseCase {
    data class Request(
        val userName: String,
        val amount: Long
    )

    fun payment(request: Request)
}

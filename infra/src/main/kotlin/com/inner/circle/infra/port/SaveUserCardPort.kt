package com.inner.circle.infra.port

fun interface SaveUserCardPort {
    data class Request(
        val accountId: Long?,
        val isRepresentative: Boolean,
        val cardNumber: String,
        val expirationPeriod: String,
        val cvc: String,
        val cardCompany: String
    )

    fun save(request: SaveUserCardPort.Request)
}

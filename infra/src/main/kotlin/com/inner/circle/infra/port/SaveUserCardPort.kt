package com.inner.circle.infra.port

fun interface SaveUserCardPort {
    data class Request(
        val userId: Long?,
        val representativeYn: Boolean,
        val cardNumber: String,
        val expirationPeriod: String,
        val cvc: String
    )

    fun save(request: SaveUserCardPort.Request)
}

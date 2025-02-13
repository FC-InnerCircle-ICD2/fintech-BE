package com.inner.circle.corebackoffice.service.dto

data class MerchantDto(
    val id: String,
    val username: String,
    val password: String,
    val token: String,
    val name: String
) {
}

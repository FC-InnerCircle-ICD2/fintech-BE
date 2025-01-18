package com.inner.circle.apibackoffice.controller.dto

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: com.inner.circle.corebackoffice.domain.Currency): Currency =
            when (currency) {
                com.inner.circle.corebackoffice.domain.Currency.KRW -> KRW
                com.inner.circle.corebackoffice.domain.Currency.USD -> USD
            }
    }
}

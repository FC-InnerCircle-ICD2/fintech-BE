package com.inner.circle.corebackoffice.domain

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: com.inner.circle.infrabackoffice.repository.entity.Currency): Currency =
            when (currency) {
                com.inner.circle.infrabackoffice.repository.entity.Currency.KRW -> KRW
                com.inner.circle.infrabackoffice.repository.entity.Currency.USD -> USD
            }
    }
}

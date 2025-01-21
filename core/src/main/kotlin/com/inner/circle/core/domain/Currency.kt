package com.inner.circle.core.domain

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: com.inner.circle.infra.repository.entity.Currency): Currency =
            when (currency) {
                com.inner.circle.infra.repository.entity.Currency.KRW -> KRW
                com.inner.circle.infra.repository.entity.Currency.USD -> USD
            }
    }
}

package com.inner.circle.core.domain

import com.inner.circle.infra.repository.entity.Currency as InfraCurrency

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: InfraCurrency): Currency =
            when (currency) {
                InfraCurrency.KRW -> KRW
                InfraCurrency.USD -> USD
            }

        fun toInfraCurrency(currency: Currency): InfraCurrency =
            when (currency) {
                KRW -> InfraCurrency.KRW
                USD -> InfraCurrency.USD
            }
    }
}

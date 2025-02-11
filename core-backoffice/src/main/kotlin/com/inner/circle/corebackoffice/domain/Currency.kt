package com.inner.circle.corebackoffice.domain

import com.inner.circle.infrabackoffice.repository.entity.Currency as InfraBackOfficeCurrency

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: InfraBackOfficeCurrency): Currency =
            when (currency) {
                InfraBackOfficeCurrency.KRW -> KRW
                InfraBackOfficeCurrency.USD -> USD
            }
    }
}

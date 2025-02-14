package com.inner.circle.apibackoffice.controller.dto

import com.inner.circle.corebackoffice.domain.Currency as CoreBackofficeCurrency

enum class Currency {
    KRW,
    USD;

    companion object {
        fun of(currency: CoreBackofficeCurrency): Currency =
            when (currency) {
                CoreBackofficeCurrency.KRW -> KRW
                CoreBackofficeCurrency.USD -> USD
            }
    }
}

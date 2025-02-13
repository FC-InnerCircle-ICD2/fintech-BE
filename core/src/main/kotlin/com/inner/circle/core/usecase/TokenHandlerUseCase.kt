package com.inner.circle.core.usecase

interface TokenHandlerUseCase {
    fun <T> generateTokenBy(
        keyString: String,
        data: T
    ): String
}

package com.inner.circle.corebackoffice.usecase

interface TokenHandlerUseCase {
    fun <T> generateTokenBy(
        keyString: String,
        data: T
    ): String
}

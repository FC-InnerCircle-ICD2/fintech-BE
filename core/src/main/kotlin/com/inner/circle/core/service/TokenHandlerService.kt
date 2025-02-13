package com.inner.circle.core.service

import com.inner.circle.core.usecase.TokenHandlerUseCase
import org.springframework.stereotype.Service

@Service
class TokenHandlerService(
    private val jwtHandler: JwtHandler
) : TokenHandlerUseCase {
    override fun <T> generateTokenBy(
        keyString: String,
        data: T
    ): String = jwtHandler.generateTokenBy(claimTarget = data)
}

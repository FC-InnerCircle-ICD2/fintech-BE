package com.inner.circle.core.service

import com.inner.circle.core.usecase.TokenHandlingUseCase
import org.springframework.stereotype.Service

@Service
class TokenHandleService(
    private val jwtHandler: JwtHandler
) : TokenHandlingUseCase

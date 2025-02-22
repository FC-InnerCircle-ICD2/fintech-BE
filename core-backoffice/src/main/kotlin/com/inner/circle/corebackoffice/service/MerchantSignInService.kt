package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.MerchantSignInDto
import com.inner.circle.corebackoffice.usecase.MerchantSignInUseCase
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MerchantSignInService(
    private val merchantFinderPort: MerchantFinderPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : MerchantSignInUseCase {
    override fun signIn(request: MerchantSignInUseCase.Request): MerchantSignInDto =
        merchantFinderPort
            .findByEmailOrNull(
                email = request.email
            )?.let { merchantEntity ->
                merchantEntity
                    .takeIf { bCryptPasswordEncoder.matches(request.password, it.password) }
                    ?.let {
                        MerchantSignInDto(
                            id = it.id,
                            name = it.name
                        )
                    }
            } ?: throw UserAuthenticationException.UserNotFoundException()
}

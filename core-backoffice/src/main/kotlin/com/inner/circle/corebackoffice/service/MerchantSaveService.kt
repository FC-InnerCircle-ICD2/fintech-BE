package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.service.dto.MerchantDto
import com.inner.circle.corebackoffice.usecase.MerchantSaveUseCase
import com.inner.circle.corebackoffice.util.ClientCredentialsGenerator
import com.inner.circle.exception.BackofficeException
import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import com.inner.circle.infrabackoffice.port.MerchantSavePort
import org.springframework.stereotype.Service

@Service
class MerchantSaveService(
    private val merchantSavePort: MerchantSavePort,
    private val merchantFinderPort: MerchantFinderPort,
    private val clientCredentialsGenerator: ClientCredentialsGenerator
) : MerchantSaveUseCase {
    override fun save(request: MerchantSaveUseCase.Request): MerchantDto {
        if (merchantFinderPort.existsByEmail(request.email)) {
            throw BackofficeException.MerchantAlreadyExistException()
        }

        val savedMerchant =
            merchantSavePort.save(
                MerchantSavePort.Request(
                    email = request.email,
                    password = request.password,
                    token = clientCredentialsGenerator.generateClientSecret(),
                    name = request.name
                )
            )

        return MerchantDto(
            id = savedMerchant.id,
            email = savedMerchant.email,
            password = savedMerchant.password,
            token = savedMerchant.token,
            name = savedMerchant.name
        )
    }
}

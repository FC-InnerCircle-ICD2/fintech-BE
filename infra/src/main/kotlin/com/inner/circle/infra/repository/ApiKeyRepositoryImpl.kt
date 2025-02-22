package com.inner.circle.infra.repository

import com.inner.circle.exception.PaymentException
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.repository.entity.ApiKeyEntity
import org.springframework.stereotype.Repository

@Repository
internal class ApiKeyRepositoryImpl(
    private val apiKeyJpaRepository: ApiKeyJpaRepository
) : ApiKeyRepository {
    override fun findByMerchantId(merchantId: Long): ApiKeyEntity =
        apiKeyJpaRepository.findByMerchantId(merchantId)
            ?: throw PaymentException.MerchantNotFoundException(
                merchantId = merchantId,
                message = "Merchant with id $merchantId not found"
            )

    override fun findByToken(token: String): ApiKeyEntity =
        apiKeyJpaRepository.findByToken(token = token)
            ?: throw UserAuthenticationException.UserNotFoundException()
}

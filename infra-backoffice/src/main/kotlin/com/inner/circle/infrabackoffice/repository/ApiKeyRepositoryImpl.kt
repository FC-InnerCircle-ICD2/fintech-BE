package com.inner.circle.infrabackoffice.repository

import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infrabackoffice.repository.entity.ApiKeyEntity
import org.springframework.stereotype.Repository

@Repository
internal class ApiKeyRepositoryImpl(
    private val apiKeyJpaRepository: ApiKeyJpaRepository
) : ApiKeyRepository {
    override fun findByMerchantId(merchantId: Long): ApiKeyEntity? =
        apiKeyJpaRepository.findByMerchantId(merchantId = merchantId)

    override fun findByToken(token: String): ApiKeyEntity =
        apiKeyJpaRepository.findByToken(token = token)
            ?: throw UserAuthenticationException.UserNotFoundException()

    override fun save(apiKey: ApiKeyEntity): ApiKeyEntity = apiKeyJpaRepository.save(apiKey)
}

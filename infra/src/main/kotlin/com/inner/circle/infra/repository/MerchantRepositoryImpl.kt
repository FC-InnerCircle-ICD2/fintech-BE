package com.inner.circle.infra.repository

import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.repository.entity.MerchantEntity
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findByToken(token: String): MerchantEntity =
        merchantJpaRepository.findByToken(token)
            ?:throw UserAuthenticationException.UserNotFoundException()
}

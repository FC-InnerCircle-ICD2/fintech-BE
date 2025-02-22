package com.inner.circle.infra.repository

import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infra.repository.entity.MerchantEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findById(merchantId: Long): MerchantEntity =
        merchantJpaRepository.findByIdOrNull(merchantId)
            ?: throw UserAuthenticationException.UserNotFoundException()
}

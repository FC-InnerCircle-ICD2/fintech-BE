package com.inner.circle.infra.structure.repository

import com.inner.circle.infra.structure.repository.entity.MerchantEntity
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findByToken(token: String): MerchantEntity? =
        merchantJpaRepository.findByToken(token)
}

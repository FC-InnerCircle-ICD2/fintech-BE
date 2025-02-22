package com.inner.circle.infrabackoffice.repository

import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findById(id: Long): MerchantEntity =
        merchantJpaRepository.findByIdOrNull(id)
            ?: throw PaymentException.MerchantNotFoundException(
                merchantId = id,
                message = "Merchant with id $id not found"
            )

    override fun existsByEmail(email: String): Boolean = merchantJpaRepository.existsByEmail(email)

    override fun save(merchant: MerchantEntity): MerchantEntity =
        merchantJpaRepository.save(merchant)

    override fun findByEmailOrNull(email: String): MerchantEntity? =
        merchantJpaRepository.findByEmail(email)

    override fun findByIdOrNull(id: Long): MerchantEntity? =
        merchantJpaRepository.findByIdOrNull(id)
}

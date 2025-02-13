package com.inner.circle.infrabackoffice.repository

import com.inner.circle.exception.PaymentException
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findById(id: String): MerchantEntity =
        merchantJpaRepository.findByIdOrNull(id)
            ?: throw PaymentException.MerchantNotFoundException(
                merchantId = id,
                message = "Merchant with id $id not found"
            )

    override fun existsByUsername(username: String): Boolean =
        merchantJpaRepository.existsByUsername(username)

    override fun save(merchant: MerchantEntity): MerchantEntity =
        merchantJpaRepository.save(merchant)

    override fun findByUsernameAndPassword(
        username: String,
        password: String
    ): MerchantEntity =
        merchantJpaRepository.findByUsernameAndPassword(username, password)
            ?: throw PaymentException.MerchantNotFoundException(
                merchantId = "",
                message = "Invalid username or password"
            )
}

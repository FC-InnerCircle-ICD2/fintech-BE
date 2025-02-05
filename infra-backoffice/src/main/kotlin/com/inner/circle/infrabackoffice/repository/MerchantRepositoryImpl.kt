package com.inner.circle.infrabackoffice.repository

import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class MerchantRepositoryImpl(
    private val merchantJpaRepository: MerchantJpaRepository
) : MerchantRepository {
    override fun findById(id: String): MerchantEntity =
        merchantJpaRepository.findByIdOrNull(id)
            ?: throw AppException(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다.")

    override fun save(merchant: MerchantEntity): MerchantEntity =
        merchantJpaRepository.save(merchant)
}

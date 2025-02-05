package com.inner.circle.corebackoffice.service

import com.inner.circle.corebackoffice.usecase.ManagementsUseCase
import com.inner.circle.infrabackoffice.repository.MerchantRepository
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan

@DataJpaTest
@ComponentScan(
    basePackages = ["com.inner.circle.infrabackoffice", "com.inner.circle.corebackoffice.service"]
)
class ManagementServiceTest(
    private val managementsUseCase: ManagementsUseCase,
    private val merchantRepository: MerchantRepository
) : ExpectSpec(
        {
            context("") {
                expect("Merchant의 토큰을 갱신할 수 있다.") {
                    // Given
                    val merchant =
                        MerchantEntity(
                            id = "test@gmail.com",
                            mid = "M20250118SHOP001",
                            token = "test",
                            name = "test shop"
                        )
                    merchantRepository.save(merchant)

                    // When
                    managementsUseCase.createOrUpdateKey(
                        request = ManagementsUseCase.CreateOrUpdateKeyRequest(id = merchant.id)
                    )

                    // Then
                    val updatedMerchant = merchantRepository.findById(merchant.id)
                    updatedMerchant.shouldNotBeNull()
                    updatedMerchant.token shouldNotBe merchant.token
                }
            }
        }
    )

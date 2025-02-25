package com.inner.circle.infra.adaptor

import com.inner.circle.exception.PaymentException
import com.inner.circle.exception.UserCardException
import com.inner.circle.infra.adaptor.dto.UserCardDto
import com.inner.circle.infra.port.UserCardPort
import com.inner.circle.infra.repository.UserCardRepository
import com.inner.circle.infra.repository.entity.UserCardEntity
import jakarta.transaction.Transactional
import kotlin.Long
import org.springframework.stereotype.Component

@Component
internal class UserCardAdaptor(
    private val repository: UserCardRepository
) : UserCardPort {
    @Transactional
    override fun save(request: UserCardDto): UserCardDto {
        val userCardEntity =
            repository.findByAccountIdAndIsRepresentative(
                request.accountId,
                true
            )

        // 이미 대표 카드가 있으면 기존 카드 대표 여부 해제
        if (request.isRepresentative && userCardEntity != null) {
            repository.save(
                UserCardEntity(
                    id = userCardEntity.id,
                    accountId = userCardEntity.accountId,
                    isRepresentative = false,
                    cardNumber = userCardEntity.cardNumber,
                    expirationPeriod = userCardEntity.expirationPeriod,
                    cvc = userCardEntity.cvc,
                    cardCompany = userCardEntity.cardCompany
                )
            )
        }

        // 대표 카드가 하나도 없으면 대표 카드로 고정
        var isRepresentative = request.isRepresentative
        if (!request.isRepresentative && userCardEntity == null) {
            isRepresentative = true
        }

        /*
        카드 형식 확인
        일반 카드: xxxx-xxxx-xxxx-xxxx
        AMEX: xxxx-xxxxxx-xxxxx
         */
        val standardCardFormat = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
        val amexCardFormat = Regex("^\\d{4}-\\d{6}-\\d{5}$")

        if (!(
                standardCardFormat.matches(request.cardNumber) ||
                    amexCardFormat.matches(request.cardNumber)
            )
        ) {
            throw UserCardException.BadCardNumberException(request.cardNumber)
        }

        val result =
            repository.save(
                UserCardEntity(
                    id = request.id,
                    accountId = request.accountId,
                    isRepresentative = isRepresentative,
                    cardNumber = request.cardNumber,
                    expirationPeriod = request.expirationPeriod,
                    cvc = request.cvc,
                    cardCompany = request.cardCompany
                )
            )

        return UserCardDto(
            id = result.id,
            accountId = result.accountId,
            isRepresentative = result.isRepresentative,
            cardNumber = result.cardNumber,
            expirationPeriod = result.expirationPeriod,
            cvc = result.cvc,
            cardCompany = result.cardCompany
        )
    }

    override fun findByAccountId(accountId: Long): List<UserCardDto> {
        val userCardEntityList = repository.findByAccountId(accountId) ?: emptyList()
        return userCardEntityList
            .map { userCardEntity ->
                UserCardDto(
                    id = userCardEntity.id,
                    accountId = userCardEntity.accountId,
                    isRepresentative = userCardEntity.isRepresentative,
                    cardNumber = userCardEntity.cardNumber,
                    expirationPeriod = userCardEntity.expirationPeriod,
                    cvc = userCardEntity.cvc,
                    cardCompany = userCardEntity.cardCompany
                )
            }.toList()
    }

    override fun findAll(): List<UserCardDto> {
        val userCardEntityList = repository.findAll()
        return userCardEntityList
            .map { userCardEntity ->
                UserCardDto(
                    id = userCardEntity.id,
                    accountId = userCardEntity.accountId,
                    isRepresentative = userCardEntity.isRepresentative,
                    cardNumber = userCardEntity.cardNumber,
                    expirationPeriod = userCardEntity.expirationPeriod,
                    cvc = userCardEntity.cvc,
                    cardCompany = userCardEntity.cardCompany
                )
            }.toList()
    }

    override fun findById(id: Long): UserCardDto {
        val userCardEntity = repository.findById(id)
        return UserCardDto(
            id = userCardEntity.id,
            accountId = userCardEntity.accountId,
            isRepresentative = userCardEntity.isRepresentative,
            cardNumber = userCardEntity.cardNumber,
            expirationPeriod = userCardEntity.expirationPeriod,
            cvc = userCardEntity.cvc,
            cardCompany = userCardEntity.cardCompany
        )
    }

    @Transactional
    override fun deleteById(
        accountId: Long,
        id: Long
    ): UserCardDto {
        // 선택한 카드 삭제
        val userCardEntity = repository.findById(id)
        repository.deleteById(id)

        // 해당 카드가 대표 카드인 경우 id가 가장 작은 카드를 대표카드로 설정
        if (userCardEntity.isRepresentative) {
            val userCardDtoList = repository.findByAccountId(accountId)
            if (!userCardDtoList.isNullOrEmpty()) { // null 또는 빈 리스트 체크
                repository.save(
                    UserCardEntity(
                        id = userCardDtoList[0].id,
                        accountId = accountId,
                        isRepresentative = true,
                        cardNumber = userCardDtoList[0].cardNumber,
                        expirationPeriod = userCardDtoList[0].expirationPeriod,
                        cvc = userCardDtoList[0].cvc,
                        cardCompany = userCardDtoList[0].cardCompany
                    )
                )
            }
        }

        return UserCardDto(
            id = userCardEntity.id,
            accountId = userCardEntity.accountId,
            isRepresentative = userCardEntity.isRepresentative,
            cardNumber = userCardEntity.cardNumber,
            expirationPeriod = userCardEntity.expirationPeriod,
            cvc = userCardEntity.cvc,
            cardCompany = userCardEntity.cardCompany
        )
    }

    override fun findByAccountIdAndIsRepresentative(
        accountId: Long,
        isRepresentative: Boolean
    ): UserCardDto {
        val userCardEntity =
            repository.findByAccountIdAndIsRepresentative(
                accountId,
                isRepresentative
            ) ?: throw PaymentException.CardNotFoundException()

        return UserCardDto(
            id = userCardEntity.id,
            accountId = userCardEntity.accountId,
            isRepresentative = userCardEntity.isRepresentative,
            cardNumber = userCardEntity.cardNumber,
            expirationPeriod = userCardEntity.expirationPeriod,
            cvc = userCardEntity.cvc,
            cardCompany = userCardEntity.cardCompany
        )
    }

    @Transactional
    override fun saveAll(userCardDtoList: List<UserCardDto>): List<UserCardDto> {
        repository.saveAll(
            userCardDtoList
                .map { userCardDto ->
                    UserCardEntity(
                        id = userCardDto.id,
                        accountId = userCardDto.accountId,
                        isRepresentative = userCardDto.isRepresentative,
                        cardNumber = userCardDto.cardNumber,
                        expirationPeriod = userCardDto.expirationPeriod,
                        cvc = userCardDto.cvc,
                        cardCompany = userCardDto.cardCompany
                    )
                }.toList()
        )
        return userCardDtoList
    }
}

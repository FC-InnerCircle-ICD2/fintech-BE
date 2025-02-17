package com.inner.circle.core.service

import com.inner.circle.core.service.dto.UserCardDto
import com.inner.circle.core.usecase.UserCardUseCase
import com.inner.circle.infra.port.UserCardPort
import org.springframework.stereotype.Service
import com.inner.circle.infra.adaptor.dto.UserCardDto as InfraUserCardDto

@Service
internal class UserCardService(
    private val userCardPort: UserCardPort
) : UserCardUseCase {
    override fun save(userCard: UserCardDto): UserCardDto {
        val infraUserCardDto =
            userCardPort.save(
                InfraUserCardDto(
                    id = null,
                    accountId = userCard.accountId,
                    isRepresentative = userCard.isRepresentative,
                    cardNumber = userCard.cardNumber,
                    expirationPeriod = userCard.expirationPeriod,
                    cvc = userCard.cvc
                )
            )
        return userCard
    }

    override fun findByAccountId(accountId: Long): List<UserCardDto> {
        val infraUserCardDtoList = userCardPort.findByAccountId(accountId)
        return infraUserCardDtoList
            .map { infraUserCardDto ->
                UserCardDto(
                    id = infraUserCardDto.id,
                    accountId = infraUserCardDto.accountId,
                    isRepresentative = infraUserCardDto.isRepresentative,
                    cardNumber = infraUserCardDto.cardNumber,
                    expirationPeriod = infraUserCardDto.expirationPeriod,
                    cvc = infraUserCardDto.cvc
                )
            }.toList()
    }

    override fun findAll(): List<UserCardDto> {
        val infraUserCardDtoList = userCardPort.findAll()
        return infraUserCardDtoList
            .map { infraUserCardDto ->
                UserCardDto(
                    id = infraUserCardDto.id,
                    accountId = infraUserCardDto.accountId,
                    isRepresentative = infraUserCardDto.isRepresentative,
                    cardNumber = infraUserCardDto.cardNumber,
                    expirationPeriod = infraUserCardDto.expirationPeriod,
                    cvc = infraUserCardDto.cvc
                )
            }.toList()
    }

    override fun updateRepresentativeCard(
        accountId: Long,
        id: Long
    ): UserCardDto {
        //기존 카드 대표 여부 해제
        val infraUserCardDto = userCardPort.findByAccountIdAndIsRepresentative(
            accountId,
            true
        )

        //선택 카드 대표 여부 설정
        val infraUserCardDto2 = userCardPort.findById(id)

        userCardPort.saveAll(mutableListOf(
            InfraUserCardDto(
                id = infraUserCardDto.id,
                accountId = infraUserCardDto.accountId,
                isRepresentative = false,
                cardNumber = infraUserCardDto.cardNumber,
                expirationPeriod = infraUserCardDto.expirationPeriod,
                cvc = infraUserCardDto.cvc
            ),
            InfraUserCardDto(
                id = id,
                accountId = infraUserCardDto2.accountId,
                isRepresentative = true,
                cardNumber = infraUserCardDto2.cardNumber,
                expirationPeriod = infraUserCardDto2.expirationPeriod,
                cvc = infraUserCardDto2.cvc
            ))
        )

        return UserCardDto(
            id = id,
            accountId = infraUserCardDto2.accountId,
            isRepresentative = true,
            cardNumber = infraUserCardDto2.cardNumber,
            expirationPeriod = infraUserCardDto2.expirationPeriod,
            cvc = infraUserCardDto2.cvc
        )
    }


    override fun deleteById(
        accountId: Long,
        id: Long
    ): UserCardDto {
        val infraUserCardDto = userCardPort.deleteById(
            accountId,
            id
        )

        return UserCardDto(
            id = id,
            accountId = infraUserCardDto.accountId,
            isRepresentative = infraUserCardDto.isRepresentative,
            cardNumber = infraUserCardDto.cardNumber,
            expirationPeriod = infraUserCardDto.expirationPeriod,
            cvc = infraUserCardDto.cvc
        )
    }
}

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
}

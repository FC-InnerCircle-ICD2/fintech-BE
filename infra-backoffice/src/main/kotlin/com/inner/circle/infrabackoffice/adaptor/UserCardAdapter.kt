package com.inner.circle.infrabackoffice.adaptor

import com.inner.circle.infrabackoffice.adaptor.dto.UserCardDto
import com.inner.circle.infrabackoffice.port.UserCardPort
import com.inner.circle.infrabackoffice.repository.UserCardRepository
import com.inner.circle.infrabackoffice.repository.entity.UserCardEntity
import kotlin.Long
import org.springframework.stereotype.Component


@Component
internal class UserCardAdapter(
    private val repository: UserCardRepository
) : UserCardPort {
    override fun save(
        request: UserCardDto
    ): UserCardDto {
        repository.save(
            UserCardEntity(
                id = request.id,
                accountId = request.accountId,
                isRepresentative = request.isRepresentative,
                cardNumber = request.cardNumber,
                expirationPeriod = request.expirationPeriod,
                cvc = request.cvc
            )
        )
        return request
    }

    override fun findByAccountId(
        accountId: Long
    ): List<UserCardDto> {
        val userCardEntityList = repository.findByAccountId(accountId)
        return userCardEntityList.map { userCardEntity ->
            UserCardDto(
                id = userCardEntity.id,
                accountId = userCardEntity.accountId,
                isRepresentative = userCardEntity.isRepresentative,
                cardNumber = userCardEntity.cardNumber,
                expirationPeriod = userCardEntity.expirationPeriod,
                cvc = userCardEntity.cvc
            )
        }.toList()
    }

    override fun findAll(): List<UserCardDto> {
        val userCardEntityList = repository.findAll()
        return userCardEntityList.map { userCardEntity ->
            UserCardDto(
                id = userCardEntity.id,
                accountId = userCardEntity.accountId,
                isRepresentative = userCardEntity.isRepresentative,
                cardNumber = userCardEntity.cardNumber,
                expirationPeriod = userCardEntity.expirationPeriod,
                cvc = userCardEntity.cvc
            )
        }.toList()
    }
}

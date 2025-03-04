package com.inner.circle.core.service

import com.inner.circle.core.service.dto.UserCardDto
import com.inner.circle.core.usecase.UserCardUseCase
import com.inner.circle.exception.AuthenticateException
import com.inner.circle.exception.UserCardException
import com.inner.circle.infra.http.HttpClient
import com.inner.circle.infra.port.UserCardPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.inner.circle.infra.adaptor.dto.UserCardDto as InfraUserCardDto

@Service
internal class UserCardService(
    private val userCardPort: UserCardPort,
    private val httpClient: HttpClient,
    @Value("\${card.url.base-url}") private var baseUrl: String,
    @Value("\${card.url.validate-end-point}") private var endPoint: String
) : UserCardUseCase {
    override fun save(userCard: UserCardDto): UserCardDto {
        val cardValidateMap: Map<String, Any> =
            httpClient.sendPostRequest(
                baseUrl,
                endPoint,
                mapOf(
                    "cardNumber" to userCard.cardNumber,
                    "expiryDate" to userCard.expirationPeriod,
                    "cvc" to userCard.cvc,
                    "cardCompany" to userCard.cardCompany
                )
            )

        // 어댑터로 이동 예정
        if (!(cardValidateMap["isValid"] as Boolean)) {
            throw AuthenticateException.CardAuthFailException(userCard.cardNumber)
        }

        try {
            val infraUserCardDto =
                userCardPort.save(
                    InfraUserCardDto(
                        id = null,
                        accountId = userCard.accountId,
                        isRepresentative = userCard.isRepresentative,
                        cardNumber = userCard.cardNumber,
                        expirationPeriod = userCard.expirationPeriod,
                        cvc = userCard.cvc,
                        cardCompany = userCard.cardCompany
                    )
                )
            return UserCardDto(
                id = infraUserCardDto.id,
                accountId = infraUserCardDto.accountId,
                isRepresentative = infraUserCardDto.isRepresentative,
                cardNumber = infraUserCardDto.cardNumber,
                expirationPeriod = infraUserCardDto.expirationPeriod,
                cvc = infraUserCardDto.cvc,
                cardCompany = infraUserCardDto.cardCompany
            )
        } catch (e: Exception) {
            throw UserCardException.AlreadyRegisterCardException(userCard.cardNumber)
        }
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
                    cvc = infraUserCardDto.cvc,
                    cardCompany = infraUserCardDto.cardCompany
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
                    cvc = infraUserCardDto.cvc,
                    cardCompany = infraUserCardDto.cardCompany
                )
            }.toList()
    }

    override fun updateRepresentativeCard(
        accountId: Long,
        id: Long
    ): List<UserCardDto> {
        // 유저 카드 목록 전체 조회
        val infraUserCardDtoList =
            userCardPort.findByAccountId(accountId)

        // id가 존재하지 않으면 예외 처리
        if (infraUserCardDtoList.none { it.id == id }) {
            throw UserCardException.CardNotFoundException(id) // 해당 id에 대한 예외 처리
        }

        // 대표 카드 변경
        val result =
            userCardPort.saveAll(
                infraUserCardDtoList.map { infraUserCardDto ->
                    infraUserCardDto.copy(
                        isRepresentative = (id == infraUserCardDto.id)
                    )
                }
            )

        return result
            .map { userCardDto ->
                UserCardDto(
                    id = userCardDto.id,
                    accountId = userCardDto.accountId,
                    isRepresentative = userCardDto.isRepresentative,
                    cardNumber = userCardDto.cardNumber,
                    expirationPeriod = userCardDto.expirationPeriod,
                    cvc = userCardDto.cvc,
                    cardCompany = userCardDto.cardCompany
                )
            }.toList()
    }

    override fun deleteById(
        accountId: Long,
        id: Long
    ): UserCardDto {
        val infraUserCardDto =
            userCardPort.deleteById(
                accountId,
                id
            )

        return UserCardDto(
            id = id,
            accountId = infraUserCardDto.accountId,
            isRepresentative = infraUserCardDto.isRepresentative,
            cardNumber = infraUserCardDto.cardNumber,
            expirationPeriod = infraUserCardDto.expirationPeriod,
            cvc = infraUserCardDto.cvc,
            cardCompany = infraUserCardDto.cardCompany
        )
    }
}

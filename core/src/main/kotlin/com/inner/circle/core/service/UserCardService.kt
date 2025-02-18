package com.inner.circle.core.service

import com.inner.circle.core.service.dto.UserCardDto
import com.inner.circle.core.usecase.UserCardUseCase
import com.inner.circle.exception.UserPaymentMethodException
import com.inner.circle.infra.port.UserCardPort
import net.gentledot.maskit.DataMasking
import net.gentledot.maskit.applications.modules.MaskingModule
import net.gentledot.maskit.models.DataTypes
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
        val dataMasking = DataMasking.builder().build()
        val cardMaskingModule = dataMasking.getModule(DataTypes.CREDIT_CARD)
        val nameMaskingModule = dataMasking.getModule(DataTypes.NAME)
        val infraUserCardDtoList = userCardPort.findByAccountId(accountId)

        return infraUserCardDtoList
            .map { infraUserCardDto ->
                val maskedCardNumber = maskingCardNumber(cardMaskingModule, infraUserCardDto)
                UserCardDto(
                    id = infraUserCardDto.id,
                    accountId = infraUserCardDto.accountId,
                    isRepresentative = infraUserCardDto.isRepresentative,
                    cardNumber = maskedCardNumber,
                    expirationPeriod = infraUserCardDto.expirationPeriod,
                    cvc = nameMaskingModule.mask(infraUserCardDto.cvc, 1, 2)
                )
            }.toList()
    }

    private fun maskingCardNumber(
        cardMaskingModule: MaskingModule,
        infraUserCardDto: com.inner.circle.infra.adaptor.dto.UserCardDto
    ): String {
        val cardNumber = infraUserCardDto.cardNumber
        val split = cardNumber.split("-")
        val masked = cardMaskingModule.mask(cardNumber)
        if (split.size != 4) throw UserPaymentMethodException.InvalidCardNumberException(masked)
        val firstNumberPart = split[0]
        return """$firstNumberPart${masked.substring(4)}"""
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
    ): List<UserCardDto> {
        // 유저 카드 목록 전체 조회
        val infraUserCardDtoList =
            userCardPort.findByAccountId(accountId)

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
                    cvc = userCardDto.cvc
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
            cvc = infraUserCardDto.cvc
        )
    }
}

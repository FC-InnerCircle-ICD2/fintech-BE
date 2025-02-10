package com.inner.circle.infra.security

import com.inner.circle.exception.AppException
import com.inner.circle.exception.HttpStatus
import com.inner.circle.infra.repository.MerchantJpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MerchantDetailService(
    private val merchantJpaRepository: MerchantJpaRepository
) : UserDetailsService {
    override fun loadUserByUsername(token: String): UserDetails {
        val merchant =
            merchantJpaRepository.findByToken(token)
                ?: throw AppException(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자 입니다.")

        val userDetails =
            MerchantUserDetails(
                id = merchant.id ?: "",
                username = merchant.username,
                password = merchant.password,
                name = merchant.name
            )
        return userDetails
    }
}

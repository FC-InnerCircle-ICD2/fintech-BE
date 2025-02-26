package com.inner.circle.corebackoffice.security

import com.inner.circle.corebackoffice.service.JwtHandler
import com.inner.circle.exception.UserAuthenticationException
import com.inner.circle.infrabackoffice.port.MerchantFinderPort
import com.inner.circle.infrabackoffice.repository.entity.MerchantEntity
import io.jsonwebtoken.Claims
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class MerchantApiKeyProviderImpl(
    @Value("\${jwt.secret}") private val secret: String,
    private val merchantFinderPort: MerchantFinderPort,
    private val jwtHandler: JwtHandler
) : MerchantApiKeyProvider {
    override fun getMerchantValidAuthenticationOrThrow(token: String): Authentication =
        jwtHandler
            .getAuthorizationTokenClaimsOrNull(
                token = token,
                secretKey = secret
            )?.let {
                val accountInfo =
                    merchantFinderPort
                        .findByIdOrNull(
                            id = it.getAccountIdFromClaims()
                        )?.toUserDetails()
                        ?: throw UserAuthenticationException.UserNotFoundException()

                UsernamePasswordAuthenticationToken(
                    accountInfo,
                    null,
                    mutableListOf(SimpleGrantedAuthority("ROLE_MERCHANT"))
                )
            } ?: throw UserAuthenticationException.UnauthorizedException(message = "Invalid Token")

    private fun MerchantEntity.toUserDetails(): MerchantUserDetails =
        MerchantUserDetails(
            id = this.id,
            email = this.email,
            password = this.password,
            name = this.name
        )

    private fun Claims.getAccountIdFromClaims() =
        (this["data"] as Map<*, *>)["id"].toString().toLong()

    companion object {
        private val logger = LoggerFactory.getLogger(MerchantApiKeyProviderImpl::class.java)
    }
}

package com.inner.circle.apibackoffice.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MerchantAuthenticationToken(
    principal: UserDetails,
    credentials: Any?,
    authorities: Collection<GrantedAuthority>
) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)

package com.inner.circle.core.structure.service

import com.inner.circle.core.structure.domain.Payment
import com.inner.circle.core.structure.usecase.RequestPaymentUseCase
import com.inner.circle.infra.structure.port.ClaimHandlingPort
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
internal class ClaimService(
    private val claimHandlingPort: ClaimHandlingPort
) : RequestPaymentUseCase {
    fun createPayment(request: Payment): MultiValueMap<String, String> {
        TODO("Not yet implemented")
    }

    override fun payment(request: RequestPaymentUseCase.Request) {
        TODO("Not yet implemented")
    }
}

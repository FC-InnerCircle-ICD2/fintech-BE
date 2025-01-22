package com.inner.circle.exception

sealed class CardCompanyException(
    status: HttpStatus,
    override val message: String,
    override val cause: Throwable? = null
) : AppException(status, message, cause) {
    data class CardNotApproveException(
        val cardNumber: String,
        override val message: String = "Card Number with ID $cardNumber not Approve",
        override val cause: Throwable? = null
    ) : CardCompanyException(HttpStatus.CARD_NOT_APPROVED, message, cause)

    data class ConnenctException(
        val code: Int?,
        val msg: String?,
        override val message: String = "Connection with credit card company failed. code: $code, msg: $msg",
        override val cause: Throwable? = null
    ) : CardCompanyException(HttpStatus.CARD_COMPANY_CONNECT_FAIL, message, cause)
}

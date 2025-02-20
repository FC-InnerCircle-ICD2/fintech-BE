package com.inner.circle.infrabackoffice.repository.entity

enum class PaymentStatusType {
    /**
     * 결제를 생성하면 가지게 되는 초기 상태입니다.
     * 인증 전까지는 READY 상태를 유지합니다.
     */
    READY,

    /**
     * 결제수단 정보로 결제수단 인증을 요청할 수 있는 상태입니다.
     */
    IN_VERIFICATE,

    /**
     * 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다.
     * 결제 승인 API를 호출하면 결제가 완료됩니다.
     */
    IN_PROGRESS,

    /**
     * 인증된 결제수단으로 요청한 결제가 승인된 상태입니다.
     */
    DONE,

    /**
     * 요청한 결제가 취소된 상태입니다.
     */
    CANCELED,

    /**
     * 결제 승인에 실패한 상태입니다.
     */
    ABORTED,

    /**
     * 요청된 결제의 유효 시간 N분이 지나 거래가 취소된 상태입니다.
     * IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.
     */
    EXPIRED
}

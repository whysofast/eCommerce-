package com.example.ecommercefast.models

import java.time.Duration
import java.time.LocalDateTime

enum class Coupon(val discount: Double, val expiresAt: LocalDateTime) {
    FAST20(0.2, LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
    FAST2019(0.4, LocalDateTime.of(2019, 12, 31, 0, 0, 0))

}

fun Coupon.ifNotExpired(): Coupon? {
    return if (Duration.between(this.expiresAt, LocalDateTime.now()).toDays() < 0) {
        this
    } else null
}
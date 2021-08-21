package com.example.ecommercefast.models

import java.time.Duration
import java.time.LocalDateTime

data class Coupon(
    val name: String,
    val discount: Double,
    val expiresAt: LocalDateTime
) {
    fun toFormerCoupon(name: String) = FormerCoupon.valueOf(name)
}

enum class FormerCoupon(val discount: Double, val expiresAt: LocalDateTime) {
    FAST20(0.2, LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
    FAST2019(0.4, LocalDateTime.of(2019, 12, 31, 0, 0, 0))
}

fun FormerCoupon.ifNotExpired(): FormerCoupon? {
    return if (Duration.between(this.expiresAt, LocalDateTime.now()).toDays() < 0) {
        this
    } else null
}
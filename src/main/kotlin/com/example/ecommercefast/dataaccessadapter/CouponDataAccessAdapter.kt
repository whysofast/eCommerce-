package com.example.ecommercefast.dataaccessadapter

import com.example.ecommercefast.models.Coupon
import com.example.ecommercefast.ports.CouponDataAccessPort
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CouponDataAccessAdapter() : CouponDataAccessPort {

    val coupons = listOf(
        Coupon("FAST20", 0.2, LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
        Coupon("FAST2019", 0.4, LocalDateTime.of(2019, 12, 31, 0, 0, 0)),
    )


    override fun findByName(name: String): Coupon? {
        return coupons.find { it.name == name }
    }
}
package com.example.ecommercefast.ports

import com.example.ecommercefast.models.Coupon

interface CouponDataAccessPort {
    fun findByName(name: String): Coupon?
}
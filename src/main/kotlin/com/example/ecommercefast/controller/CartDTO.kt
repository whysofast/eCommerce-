package com.example.ecommercefast.controller

import com.example.ecommercefast.Cart
import com.example.ecommercefast.Coupon
import com.example.ecommercefast.Customer
import com.example.ecommercefast.Item
import com.example.ecommercefast.ifNotExpired

data class CartDTO(
    val customer: Customer,
    val items: List<Item>,
    val coupon: String? = null
) {
    fun toModel() = Cart(
        customer = customer,
        items = items,
        coupon = coupon?.let {
            try {
                Coupon.valueOf(it).ifNotExpired()
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    )
}

data class CartOutDTO(
    val total: Long,
    val shipping: Double
)






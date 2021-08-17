package com.example.ecommercefast.controller

import com.example.ecommercefast.models.Coupon
import com.example.ecommercefast.models.Customer
import com.example.ecommercefast.models.Item
import com.example.ecommercefast.models.Order
import com.example.ecommercefast.models.ifNotExpired

data class CartDTO(
    val customer: Customer,
    val items: List<Item>,
    val coupon: String? = null
) {
    fun toModel() = Order(
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






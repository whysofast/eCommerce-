package com.example.ecommercefast.models

import com.example.ecommercefast.controller.CartOutDTO
import java.util.UUID

class Cart(
    val id: UUID? = null,
    val customer: Customer,
    val items: List<Item>,
    val coupon: Coupon? = null,
    var shipping: Double = 0.0
) {
    fun toDto() = CartOutDTO(
        total = getTotal(),
        shipping = shipping
    )

    private fun getTotal(): Long {
        var total = 0L
        this.items.map {
            total += it.quantity * it.product.price
        }

        this.coupon?.let { total -= (total * it.discount).toLong() }
        return total
    }
}
package com.example.ecommercefast.dataaccessadapter

import com.example.ecommercefast.models.Order
import com.example.ecommercefast.ports.OrderDataAccessPort
import org.springframework.stereotype.Component

@Component
class OrderDataAccessAdapter : OrderDataAccessPort {

    val orders = mutableListOf<Order>()

    override fun save(order: Order): Order {
        orders.add(order)
        return order
    }
}
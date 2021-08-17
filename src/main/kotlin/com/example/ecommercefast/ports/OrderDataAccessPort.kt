package com.example.ecommercefast.ports

import com.example.ecommercefast.models.Order

interface OrderDataAccessPort {
    fun save(order: Order): Order
}
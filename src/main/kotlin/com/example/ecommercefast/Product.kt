package com.example.ecommercefast

import java.util.UUID

data class Product(
    val id: UUID? = null,
    val name: String,
    val description: String,
    val price: Long,
    val measurements: Measurements
) {
    data class Measurements(
        val weight: Double,
        val height: Long,
        val width: Long,
        val depth: Long
    )
}

data class Item(
    val quantity: Long,
    val product: Product
)
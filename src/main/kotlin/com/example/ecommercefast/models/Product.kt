package com.example.ecommercefast.models

data class Product(
    val id: Long? = null,
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
    val productId: Long,
    val price: Long
)
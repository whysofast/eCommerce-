package com.example.ecommercefast.ports

import com.example.ecommercefast.models.Product

interface ProductDataAccessPort {
    fun findById(productId: Long): Product?
}
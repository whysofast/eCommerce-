package com.example.ecommercefast.shippingAPI

import com.example.ecommercefast.models.Product.Measurements

interface ShippingPort {
    fun getShippingPrice(cep: String, measurements: Measurements): Double
}
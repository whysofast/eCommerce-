package com.example.ecommercefast.shippingAPI

import com.example.ecommercefast.controller.Product.Measurements

interface ShippingPort {
    fun getShippingPrice(cep: String, measurements: Measurements) : Double
}
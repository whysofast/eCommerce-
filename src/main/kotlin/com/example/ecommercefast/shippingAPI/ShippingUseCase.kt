package com.example.ecommercefast.shippingAPI

import com.example.ecommercefast.models.Product.Measurements
import org.springframework.stereotype.Service

const val MINIMUM_SHIPPING_PRICE = 10.0
const val CM3_FACTOR = 10E6

@Service

class ShippingUseCase : ShippingPort {
    override fun getShippingPrice(cep: String, measurements: Measurements): Double {
        val cepFrom = "10000000"

        val distance = 1000

        val volume = with(measurements) { (width * depth * height) / CM3_FACTOR }

        val density = (measurements.weight) / volume

        var price = distance * volume * (density / 100)

        if (price < 10) {
            price = MINIMUM_SHIPPING_PRICE
        }

        return price
    }
}
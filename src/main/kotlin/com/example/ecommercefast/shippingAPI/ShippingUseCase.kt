package com.example.ecommercefast.shippingAPI

import com.example.ecommercefast.Product.Measurements
import org.springframework.stereotype.Service

@Service
class ShippingUseCase : ShippingPort {
    override fun getShippingPrice(cep: String, measurements: Measurements): Double {
        val cepFrom = "10000000"

        val distance = 1000

        val volume = with(measurements) { (width * depth * height) / 10e6 }

        val density = (measurements.weight) / volume

        var price = distance * volume * (density / 100)

        if (price < 10) price = 10.0

        return price
    }
}
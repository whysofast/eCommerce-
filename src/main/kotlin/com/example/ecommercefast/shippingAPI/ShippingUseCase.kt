package com.example.ecommercefast.shippingAPI

import com.example.ecommercefast.controller.Product.Measurements
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class ShippingUseCase : ShippingPort{
    override fun getShippingPrice(cep: String, measurements: Measurements): Double {
        val cepFrom = "10000000"

        val distance =  abs(cep.toLong() - cepFrom.toLong())

        val volume = with(measurements) { (width * depth * height)/10e6 }

        val density = (measurements.weight)/volume

        val price = distance*volume*(density/100)

        return price
    }
}
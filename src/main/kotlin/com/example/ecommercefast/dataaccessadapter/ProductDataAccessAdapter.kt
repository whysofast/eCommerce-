package com.example.ecommercefast.dataaccessadapter

import com.example.ecommercefast.models.Product
import com.example.ecommercefast.ports.ProductDataAccessPort
import org.springframework.stereotype.Component

@Component
class ProductDataAccessAdapter() : ProductDataAccessPort {
    val products = listOf(
        Product(1, "Mouse", "Destro", 200_00, Product.Measurements(0.0, 0L, 0L, 0L)),
        Product(2, "Teclado", "Mecanico", 500_00, Product.Measurements(0.0, 0L, 0L, 0L)),
        Product(3, "Monitor", "144hz", 1000_00, Product.Measurements(0.0, 0L, 0L, 0L)),
        Product(4, "Headset", "", 500_00, Product.Measurements(1.5, 1L, 1L, 1L)),
        Product(5, "Dado", "", 500_00, Product.Measurements(0.1, 1, 1, 1))
    )


    override fun findById(productId: Long): Product? {
        return products.find { it.id == productId }
    }
}
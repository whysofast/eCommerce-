package com.example.ecommercefast.utils

import com.example.ecommercefast.DBO.ProductDBO
import com.example.ecommercefast.repository.ProductRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DummyData(
    private val productRepository: ProductRepository
) {

//    @PostConstruct
    fun saveProductAsDBO() {
        val productDBO = ProductDBO(
            name = "Mouse",
            description = "Ambidestro",
            price = 400_00L
        )

        productRepository.save(productDBO)
    }
}
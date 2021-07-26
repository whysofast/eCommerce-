package com.example.ecommercefast.DataAccessAdapter

import com.example.ecommercefast.repository.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductDataAccessAdapter(
    private val productRepository: ProductRepository
)
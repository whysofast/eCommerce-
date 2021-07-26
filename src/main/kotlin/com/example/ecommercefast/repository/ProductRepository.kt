package com.example.ecommercefast.repository

import com.example.ecommercefast.DBO.ProductDBO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductDBO,String>
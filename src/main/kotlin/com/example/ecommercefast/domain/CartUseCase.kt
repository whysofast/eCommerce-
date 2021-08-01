package com.example.ecommercefast.domain

import com.example.ecommercefast.controller.Cart
import com.example.ecommercefast.controller.Item
import com.example.ecommercefast.controller.hasValidCpf
import org.springframework.stereotype.Service


@Service
class CartUseCase {

    val carrinho = mutableListOf<Item>()

    fun save(cart: Cart) : Cart {
        if (cart.customer.hasValidCpf()){
            cart.items.map { carrinho.add(it) }
        } else {
            throw CustomerWithInvalidCpfException("customer ${cart.customer} has invalid cpf ${cart.customer.cpf}")
        }

        return cart
    }
}
package com.example.ecommercefast.domain

import com.example.ecommercefast.models.Cart
import com.example.ecommercefast.models.Item
import com.example.ecommercefast.models.hasValidCpf
import com.example.ecommercefast.shippingAPI.ShippingPort
import org.springframework.stereotype.Service


@Service
class CartUseCase(
    private val shippingPort: ShippingPort
) {

    val carrinho = mutableListOf<Item>()

    fun save(cart: Cart): Cart {
        if (cart.customer.hasValidCpf()) {
            cart.items.map {
                carrinho.add(it)
                cart.shipping += shippingPort.getShippingPrice(cart.customer.cep, it.product.measurements)
            }
        } else {
            throw CustomerWithInvalidCpfException("customer ${cart.customer} has invalid cpf ${cart.customer.cpf}")
        }

        return cart
    }
}
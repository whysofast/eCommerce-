package com.example.ecommercefast.domain

import com.example.ecommercefast.models.Item
import com.example.ecommercefast.models.Order
import com.example.ecommercefast.models.hasInvalidCpf
import com.example.ecommercefast.ports.OrderDataAccessPort
import com.example.ecommercefast.ports.ProductDataAccessPort
import com.example.ecommercefast.shippingAPI.ShippingPort
import org.springframework.stereotype.Service


@Service
class CartUseCase(
    private val shippingPort: ShippingPort,
    private val productDataAccess: ProductDataAccessPort,
    private val orderDataAccess: OrderDataAccessPort
) {

    val carrinho = mutableListOf<Item>()

    fun save(order: Order): Order {

        if (order.customer.hasInvalidCpf()) throw CustomerWithInvalidCpfException("customer ${order.customer} has invalid cpf ${order.customer.cpf}")

        order.items.map { item ->
            productDataAccess.findById(item.productId)
                ?.let {
                    order.shipping += shippingPort.getShippingPrice(order.customer.cep, it.measurements)
                    carrinho.add(item)
                }
                ?: run {
                    throw RuntimeException("Product not found")
                }
        }

        val newOrder =
            Order(
                items = carrinho.toMutableList(),
                customer = order.customer,
                shipping = order.shipping,
                formerCoupon = order.formerCoupon
            )

        // Pra fazer o mockMvc não bugar e transpaçar resultado de teste pra teste
        carrinho.clear()
        return orderDataAccess.save(newOrder)
    }
}
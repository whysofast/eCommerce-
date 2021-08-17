package com.example.ecommercefast

import com.example.ecommercefast.controller.CartDTO
import com.example.ecommercefast.models.Customer
import com.example.ecommercefast.models.Item
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ShippingTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `should calculate shipment price`() {
        val item = Item(
            quantity = 1,
            productId = 4,
            price = 500_00
        )

        val body = Gson().toJson(
            CartDTO(
                customer = (Customer(
                    name = "Fast",
                    email = "fast@cora.com.br",
                    cpf = "01234567890",
                    cep = "45028634"
                )),
                items = listOf(
                    item
                ),
                coupon = "VALEGRATIS"
            )
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.shipping").value("14.999999999999998"))
    }

    @Test
    fun `should calculate minimum shipment price`() {
        val item = Item(
            quantity = 1,
            productId = 5,
            price = 10_00
        )

        val body = Gson().toJson(
            CartDTO(
                customer = (Customer(
                    name = "Fast",
                    email = "fast@cora.com.br",
                    cpf = "01234567890",
                    cep = "45028634"
                )),
                items = listOf(
                    item
                ),
                coupon = "VALEGRATIS"
            )
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.shipping").value("10.0"))
    }
}

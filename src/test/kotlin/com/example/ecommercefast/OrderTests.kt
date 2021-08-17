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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OrderTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `should succesfully create a cart with 3 products`() {
        val body = Gson().toJson(
            CartDTO(
                customer = (Customer(
                    name = "Fast",
                    email = "fast@cora.com.br",
                    cpf = "01234567890",
                    cep = "45028634"
                )),
                items = listOf(
                    Item(
                        quantity = 1,
                        productId = 1,
                        price = 200_00
                    ),
                    Item(
                        quantity = 2,
                        productId = 2,
                        price = 500_00
                    ), Item(
                        quantity = 3,
                        productId = 3,
                        price = 1000_00
                    )
                )
            )
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(420000L))
    }

    @Test
    fun `should succesfully create a cart with a valid coupon`() {
        val body = Gson().toJson(
            CartDTO(
                customer = (Customer(
                    name = "Fast",
                    email = "fast@cora.com.br",
                    cpf = "01234567890",
                    cep = "45028634"
                )),
                items = listOf(
                    Item(
                        quantity = 1,
                        productId = 1,
                        price = 200_00
                    ),
                    Item(
                        quantity = 2,
                        productId = 2,
                        price = 500_00
                    ), Item(
                        quantity = 3,
                        productId = 3,
                        price = 1000_00
                    )
                ),
                coupon = "FAST20"
            )
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(336000L))


    }

    @Test
    fun `should not apply discount with a invalid coupon`() {

        val item = Item(
            quantity = 1,
            productId = 1,
            price = 200_00
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
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.price))


    }

    @Test
    fun `should not apply discount with a expired coupon`() {

        val item = Item(
            quantity = 1,
            productId = 1,
            price = 200_00
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
                coupon = "FAST2019"
            )
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.price))


    }

    @Test
    fun `should not create cart with invalid cpf`() {

        val invalidCpf = "01234567891"

        val item = Item(
            quantity = 1,
            productId = 1,
            price = 200_00
        )

        val body = Gson().toJson(
            CartDTO(
                customer = (Customer(
                    name = "Fast",
                    email = "fast@cora.com.br",
                    cpf = invalidCpf,
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
            .andExpect(status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid CPF"))


    }
}

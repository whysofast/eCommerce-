package com.example.ecommercefast

import com.example.ecommercefast.controller.CartDTO
import com.example.ecommercefast.controller.Customer
import com.example.ecommercefast.controller.Item
import com.example.ecommercefast.controller.Product
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
class CartTests {

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
                        product = Product(name = "Mouse", description = "Destro", price = 200_00,measurements = Product.Measurements(0L,0L,0L,0L))
                        ),
                    Item(
                        quantity = 2,
                        product = Product(name = "Teclado", description = "Mecanico", price = 500_00,measurements = Product.Measurements(0L,0L,0L,0L))
                    ), Item(
                        quantity = 3,
                        product = Product(name = "Monitor", description = "144hz", price = 1000_00,measurements = Product.Measurements(0L,0L,0L,0L))
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
                        product = Product(name = "Mouse", description = "Destro", price = 200_00,measurements = Product.Measurements(0L,0L,0L,0L))
                    ),
                    Item(
                        quantity = 2,
                        product = Product(name = "Teclado", description = "Mecanico", price = 500_00,measurements = Product.Measurements(0L,0L,0L,0L))
                    ), Item(
                        quantity = 3,
                        product = Product(name = "Monitor", description = "144hz", price = 1000_00,measurements = Product.Measurements(0L,0L,0L,0L))
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
            product = Product(name = "Mouse", description = "Destro", price = 200_00,measurements = Product.Measurements(0L,0L,0L,0L))
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
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.product.price))


    }

    @Test
    fun `should not apply discount with a expired coupon`() {

        val item = Item(
            quantity = 1,
            product = Product(name = "Mouse", description = "Destro", price = 200_00,measurements = Product.Measurements(0L,0L,0L,0L))
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
            .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(item.product.price))


    }

    @Test
    fun `should not create cart with invalid cpf`() {

        val invalidCpf = "01234567891"

        val item = Item(
            quantity = 1,
            product = Product(name = "Mouse", description = "Destro", price = 200_00,measurements = Product.Measurements(0L,0L,0L,0L))
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

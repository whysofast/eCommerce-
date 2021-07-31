package com.example.ecommercefast.controller

import com.example.ecommercefast.domain.CartUseCase
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("cart")
class CartController(
    private val cartUseCase: CartUseCase
) {

    @PostMapping("/create")
    fun create(
        @Valid @RequestBody cartDTO: CartDTO
    ): ResponseEntity<CartOutDTO> {

        val cart = cartUseCase.save(cartDTO.toModel())

        return ok(cart.toDto())

//        Criar dominio para processear o cupom e mover a validação de cpf


    }

}
package com.example.ecommercefast.controller

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
class CartController {

    @PostMapping("/create")
    fun create(
        @Valid @RequestBody cartDTO: CartDTO
    ): ResponseEntity<CartDTO> {

        return if (cartDTO.customer.hasValidCpf()) {
            ok(cartDTO)
        } else {
            badRequest().build()
        }

//        Criar dominio para processear o cupom e mover a validação de cpf


    }

}
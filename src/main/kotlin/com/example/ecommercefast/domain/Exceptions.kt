package com.example.ecommercefast.domain

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

sealed class DomainException(message: String) : RuntimeException(message)

class CustomerWithInvalidCpfException(message: String) : RuntimeException(message)

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CustomerWithInvalidCpfException::class])
    fun handleInvalidCpfException(ex: CustomerWithInvalidCpfException, request: WebRequest) : ResponseEntity<Any> {
        return status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapOf("message" to "Invalid CPF"))
    }

}
package com.example.ecommercefast.domain

sealed class DomainException(message: String) : RuntimeException(message)

class CustomerWithInvalidCpfException(message: String) : DomainException(message)

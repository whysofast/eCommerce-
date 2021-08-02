package com.example.ecommercefast.controller

import java.util.UUID
import com.example.ecommercefast.controller.CartState.CREATED
import java.time.Duration
import java.time.LocalDateTime

class Cart(
    val id: UUID? = null,
    val customer: Customer,
    val items: List<Item>,
    val state: CartState = CREATED,
    val coupon: Coupon? = null,
    var shipping: Double = 0.0
) {
    fun toDto() = CartOutDTO(
        total = getTotal(),
        shipping = shipping
    )

    private fun getTotal(): Long {
        var total = 0L
        this.items.map {
            total += it.quantity * it.product.price
        }

        this.coupon?.let { total -= (total * it.discount).toLong() }
        return total
    }
}

data class CartOutDTO(
    val total: Long,
    val shipping: Double
)

data class CartDTO(
    val customer: Customer,
    val items: List<Item>,
    val coupon: String? = null
) {
    fun toModel() = Cart(
        customer = customer,
        items = items,
        coupon = coupon?.let {
            try {
                Coupon.valueOf(it).ifNotExpired()
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    )
}

enum class CartState {
    CREATED,
    CANCELLED,
    COMPLETED
}

enum class Coupon(val discount: Double, val expiresAt: LocalDateTime) {
    FAST20(0.2, LocalDateTime.of(2022, 1, 1, 0, 0, 0)),
    FAST2019(0.4, LocalDateTime.of(2019, 12, 31, 0, 0, 0))
}

fun Coupon.ifNotExpired(): Coupon? {
    return if (Duration.between(this.expiresAt, LocalDateTime.now()).toDays() < 0) {
        this
    } else null
}

data class Item(
    val quantity: Long,
    val product: Product
)

data class Product(
    val id: UUID? = null,
    val name: String,
    val description: String,
    val price: Long,
    val measurements : Measurements
) {
    data class Measurements(
        val weight: Long,
        val height: Long,
        val width: Long,
        val depth: Long
    )
}

data class Customer(
    val name: String,
    var email: String,
    val cpf: String,
    var cep: String
)

fun Customer.hasValidCpf(): Boolean {

    fun String.hasInvalidLength(): Boolean {
        return this.length != 11
    }

    fun String.hasRepeatableDigits(): Boolean {
        return this.all { it == this.first() }
    }

    fun String.calculateteDigit(factor: Int, max: Int): Int {
        val cpfAsDigitArrayLimited = this.map { it.digitToInt() }.slice(0 until max)
        var total = 0
        var fator = factor

        for (digit in cpfAsDigitArrayLimited) {
            total += digit * fator--
        }

        return if (total % 11 < 2) 0 else (11 - total % 11)

    }

    val cleanCPF = Regex("""\D""").replace(cpf, "")

    if (cleanCPF.isBlank()) return false
    if (cleanCPF.hasInvalidLength()) return false
    if (cleanCPF.hasRepeatableDigits()) return false

    val firstDigit = cleanCPF.calculateteDigit(10, 9)
    val secondDigit = cleanCPF.calculateteDigit(11, 10)

    val lastTwoDigits = cleanCPF.slice(9 until 11)

    return lastTwoDigits == "${firstDigit}${secondDigit}"
}

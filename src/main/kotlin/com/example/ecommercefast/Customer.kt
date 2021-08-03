package com.example.ecommercefast

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
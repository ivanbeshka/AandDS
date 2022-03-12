package lab_1

import java.math.BigInteger

class Number(private val byteArray: ByteArray) {

    constructor(init: Int) : this(BigInteger.valueOf(init.toLong()).toByteArray())
    constructor(init: Long) : this(BigInteger.valueOf(init).toByteArray())
    constructor(init: BigInteger) : this(init.toByteArray())

    operator fun plus(number: Number) =
        Number(BigInteger(byteArray).plus(BigInteger(number.byteArray)))

    operator fun minus(number: Number) =
        Number(BigInteger(byteArray).minus(BigInteger(number.byteArray)))

    operator fun times(number: Number) =
        Number(BigInteger(byteArray).times(BigInteger(number.byteArray)))

    operator fun div(number: Number) =
        Number(BigInteger(byteArray).div(BigInteger(number.byteArray)))

    operator fun rem(number: Number) =
        Number(BigInteger(byteArray).rem(BigInteger(number.byteArray)))

    operator fun compareTo(number: Number) =
        BigInteger(byteArray).compareTo(BigInteger(number.byteArray))

    override fun toString(): String {
        return "As num: ${BigInteger(byteArray)}, As byteArr: ${byteArray.map { it.toString() }}"
    }
}

fun main() {
    val num1 = Number(1000000000000000)
    val num2 = Number(-191)
    println(num1)
    println(num2)
    println(num1 + num2)
    println(num1 - num2)
    println(num1 / num2)
    println(num1 * num2)
    println(num1 % num2)
    println(num1 > num2)
    println(num1 == num2)
}
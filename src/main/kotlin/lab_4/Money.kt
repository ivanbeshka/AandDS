package lab_4

import java.util.*

fun main() {
    val sc = Scanner(System.`in`)
    var money = sc.nextInt()
    var result = ""

    if (money >= 25) {
        val count = money / 25
        result += "25: $count\n"
        money -= count * 25
    }

    if (money >= 10) {
        val count = money / 10
        result += "10: $count\n"
        money -= count * 10
    }

    if (money >= 5) {
        val count = money / 5
        result += "5: $count\n"
        money -= count * 5
    }

    if (money >= 1) {
        result += "1: $money"
    }

    println(result)
}
package lab_1

import java.math.BigInteger

val characters = charArrayOf(
    '#', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И',
    'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С',
    'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ',
    'Э', 'Ю', 'Я', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
    'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
    'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ы', 'ъ',
    'э', 'ю', 'я',' ', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', '0'
)

fun main() {
    while (true) {
        println("Введите 2 больших простых числа через пробел:")
        val values = readLine()!!.split(" ")
        val p = values[0].toLong()
        val q = values[1].toLong()

        if (isTheNumberSimple(p) && isTheNumberSimple(q)){
            val n: Long = p * q
            val m: Long = (p - 1) * (q - 1)
            val d: Long = calculateD(m)
            val e: Long = calculateE(d, m)

            println("Введите сообщение для шифрования:")

            readLine()?.let {
                val encode = RSAEncode(it, e, n)
                println(encode)
                val decode = RSADedoce(encode, d, n)
                println(decode)
            }

        } else {
            println("Одно или оба числа не простые")
        }
    }
}

fun RSAEncode(s: String, e: Long, n: Long): List<String> {
    val result = mutableListOf<String>()
    for (i in s.indices) {
        val index = characters.indexOf(s[i])
        var indexBig = BigInteger.valueOf(index.toLong())
        indexBig = indexBig.pow(e.toInt())

        val nBig = BigInteger.valueOf(n)
        indexBig %= nBig
        result.add(indexBig.toString())
    }
    return result
}

fun RSADedoce(input: List<String>, d: Long, n: Long): String {
    val result = StringBuilder()

    for (item in input) {
        var itemBig = BigInteger(item)
        itemBig = itemBig.pow(d.toInt())

        val nBig = BigInteger.valueOf(n)
        itemBig %= nBig

        val index = itemBig.toInt()
        result.append(characters[index].toString())
    }

    return result.toString()
}

private fun calculateD(m: Long): Long {
    var d = m - 1
    var i: Long = 2
    while (i <= m) {
        if (m % i == 0L && d % i == 0L) {
            d--
            i = 1
        }
        i++
    }
    return d
}

private fun calculateE(d: Long, m: Long): Long {
    var e: Long = 10
    while (true) {
        if (e * d % m == 1L) break else e++
    }
    return e
}


private fun isTheNumberSimple(n: Long): Boolean {
    if (n < 2) {
        return false
    }
    if (n == 2L) {
        return true
    }
    for (i in 2 until n) {
        if (n % i == 0L) {
            return false
        }
    }
    return true
}
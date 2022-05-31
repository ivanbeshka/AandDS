package lab_3

import java.util.*
import kotlin.math.abs

fun main() {

    /* Ввод данных */
    val scanner = Scanner(System.`in`)
    val equationsNumber = scanner.nextInt()
    val unknownsNumber = scanner.nextInt()

    val coeffs = Array(100) { DoubleArray(100) }
    val equationsResults = DoubleArray(100)

    for (i in 0 until equationsNumber) {
        coeffs[i] = DoubleArray(100)
        for (j in 0 until unknownsNumber) {
            coeffs[i][j] = scanner.nextDouble()
        }
        equationsResults[i] = scanner.nextDouble()
    }

    /* Метод Гаусса */
    //проходимся по уравнениям
    for (p in 0 until equationsNumber) {
        var max = p
        //проходимся по оставшимся уравнениям
        for (i in p + 1 until equationsNumber) {
            //находим макс коэф по модулю
            if (abs(coeffs[i][p]) > abs(coeffs[max][p])) {
                max = i
            }
        }

        //передвигаем уравнение с макс коэф вверх, на место текущего уравнения
        coeffs[p] = coeffs[max].also { coeffs[max] = coeffs[p] }
        equationsResults[p] = equationsResults[max].also { equationsResults[max] = equationsResults[p] }

        //если по диагонали есть ноль, то решений нет
        if (abs(coeffs[p][p]) <= 1e-10) {
            println("NO")
            return
        }

        //складываем первое и последующие урвнения,
        //предварительно домножив на альфа,
        //чтобы, в итоге, под диганалью получились нули
        for (i in p + 1 until equationsNumber) {
            val alpha = coeffs[i][p] / coeffs[p][p]

            equationsResults[i] -= alpha * equationsResults[p]
            for (j in p until equationsNumber) {
                coeffs[i][j] -= alpha * coeffs[p][j]
            }
        }
    }

    // Обратный проход (нули над диагональю)
    val x = DoubleArray(equationsNumber)
    for (i in equationsNumber - 1 downTo 0) {
        var sum = 0.0
        for (j in i + 1 until equationsNumber) {
            sum += coeffs[i][j] * x[j]
        }
        x[i] = (equationsResults[i] - sum) / coeffs[i][i]
    }

    /* Вывод результатов */
    if (equationsNumber < unknownsNumber) {
        print("INF")
    } else {
        println("YES")
        for (i in 0 until equationsNumber) {
            print(x[i].toString() + " ")
        }
    }
}
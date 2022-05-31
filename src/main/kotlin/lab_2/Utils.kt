package lab_2

fun printArr(arr: IntArray) {
    arr.forEach {
        print("$it ")
    }
    println()
}

val data = IntArray(10001).apply {
    for (i in 0..10000){
        this[i] = i
    }
    shuffle()
}
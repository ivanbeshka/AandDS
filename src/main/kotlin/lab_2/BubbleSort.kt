package lab_2

fun bubbleSort(arr: IntArray) {
    val n = arr.size
    for (i in 0 until n - 1) {
        for (j in 0 until n - i - 1) {
            if (arr[j] > arr[j + 1]) {
                // swap arr[j+1] and arr[j]
                arr[j] = arr[j + 1].also { arr[j + 1] = arr[j] }
            }
        }
    }
}

fun main() {
    println("Given arr")
    printArr(data)

    val startTime = System.currentTimeMillis()
    bubbleSort(data)
    println("sort time: ${System.currentTimeMillis() - startTime}")

    println("Sorted arr")
    printArr(data)
}

package lab_2

fun insertionSort(arr: IntArray) {
    val n = arr.size
    for (i in 1 until n) {
        val key = arr[i]
        var j = i - 1

        /* Move elements of arr[0..i-1], that are
           greater than key, to one position ahead
           of their current position */
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j -= 1
        }
        arr[j + 1] = key
    }
}

fun main() {
    println("Given arr")
    printArr(data)

    val startTime = System.currentTimeMillis()
    insertionSort(data)
    println("sort time: ${System.currentTimeMillis() - startTime}")

    println("Sorted arr")
    printArr(data)
}
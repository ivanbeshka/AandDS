package lab_2

fun quickSort(arr: IntArray, begin: Int, end: Int) {
    if (begin < end) {
        val partitionIndex: Int = partition(arr, begin, end)
        quickSort(arr, begin, partitionIndex - 1)
        quickSort(arr, partitionIndex + 1, end)
    }
}

private fun partition(arr: IntArray, begin: Int, end: Int): Int {
    val pivot = arr[end]
    var i = begin - 1
    for (j in begin until end) {
        if (arr[j] <= pivot) {
            i++
            arr[i] = arr[j].also { arr[j] = arr[i] }
        }
    }
    arr[i + 1] = arr[end].also { arr[end] = arr[i + 1] }
    return i + 1
}

fun main() {
    val arr = intArrayOf(12, 11, 13, 5, 6, 7)

    println("Given arr")
    printArr(arr)

    quickSort(arr, 0, arr.size - 1)

    println("Sorted arr")
    printArr(arr)
}


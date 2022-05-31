package lab_5

import java.io.IOException
import java.util.*

// более высокая точность означает более раннее завершение
// и более высокую ошибку
const val PRECISION = 0.0

/* инициализируем K центроидов */
fun addCentroids(data: DataSet, K: Int): LinkedList<HashMap<String, Double>> {
    val centroids = LinkedList<HashMap<String, Double>>()
    centroids.add(data.randomFromDataSet())
    for (i in 1 until K) {
        centroids.add(data.calculateWeighedCentroid())
    }
    return centroids
}

fun kMeans(data: DataSet, K: Int) {
    // берём инициализированные центроиды
    var centroids = addCentroids(data, K)

    // инициализируем сумму ошибок квадратов на максимум,
    // будем уменьшать ее на каждой итерации.
    var sse = Double.MAX_VALUE
    while (true) {

        for (record in data.records) {
            var minDist = Double.MAX_VALUE
            // находим центроид на минимальном расстоянии и добавляем запись в его кластер
            for (i in centroids.indices) {
                val dist = DataSet.euclideanDistance(centroids[i], record.record)
                if (dist < minDist) {
                    minDist = dist
                    record.clusterNo = i
                }
            }
        }

        // пересчитываем центроиды в соответствии с новыми назначениями кластеров
        centroids = data.recomputeCentroids(K)

        // условие выхода: разница между старым и новым значением ошибок квадратов меньше 0
        val newSSE = data.calculateTotalSSE(centroids)
        if (sse - newSSE <= PRECISION) {
            break
        }
        sse = newSSE
    }
}

fun main() {
    try {
        // считываем файл
        val data = DataSet("files\\sample.csv")

        // вызываем алгоритм
        kMeans(data, 2)

        // выводим результат в файл
        data.createCsvOutput("files\\result.csv")
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
package lab_5

import java.io.*
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt


class DataSet(csvFileName: String) {
    class Record(var record: HashMap<String, Double>) {
        var clusterNo: Int? = null
    }

    private val attrNames = LinkedList<String>()
    val records = LinkedList<Record>()
    private val indicesOfCentroids = LinkedList<Int>()
    private val minimums = HashMap<String, Double>()
    private val maximums = HashMap<String, Double>()

    init {
        var row: String?
        BufferedReader(FileReader(csvFileName)).use { csvReader ->
            if (csvReader.readLine().also { row = it } != null) {
                val data = row?.split(",")?.toTypedArray()
                data?.let { Collections.addAll(attrNames, *data) }
            }
            while (csvReader.readLine().also { row = it } != null) {
                val data = row?.split(",")?.toTypedArray()
                val record = HashMap<String, Double>()
                if (attrNames.size == data?.size) {
                    for (i in attrNames.indices) {
                        val name = attrNames[i]
                        val `val` = data[i].toDouble()
                        record[name] = `val`
                        updateMin(name, `val`)
                        updateMax(name, `val`)
                    }
                } else {
                    throw IOException("Incorrectly formatted file.")
                }
                records.add(Record(record))
            }
        }
    }

    fun createCsvOutput(outputFileName: String) {
        try {
            BufferedWriter(FileWriter(outputFileName)).use { csvWriter ->
                for (i in attrNames.indices) {
                    csvWriter.write(attrNames[i])
                    csvWriter.write(",")
                }
                csvWriter.write("ClusterId")
                csvWriter.write("\n")
                for (record in records) {
                    for (i in attrNames.indices) {
                        csvWriter.write(record.record[attrNames[i]].toString())
                        csvWriter.write(",")
                    }
                    csvWriter.write(record.clusterNo.toString())
                    csvWriter.write("\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun calculateTotalSSE(centroids: LinkedList<HashMap<String, Double>>): Double {
        var sse = 0.0
        for (i in centroids.indices) {
            sse += calculateClusterSSE(centroids[i], i)
        }
        return sse
    }

    fun calculateWeighedCentroid(): HashMap<String, Double> {
        var sum = 0.0
        for (i in records.indices) {
            if (!indicesOfCentroids.contains(i)) {
                var minDist = Double.MAX_VALUE
                for (ind in indicesOfCentroids) {
                    val dist = euclideanDistance(records[i].record, records[ind].record)
                    if (dist < minDist) minDist = dist
                }
                if (indicesOfCentroids.isEmpty()) sum = 0.0
                sum += minDist
            }
        }
        val threshold = sum * random.nextDouble()
        for (i in records.indices) {
            if (!indicesOfCentroids.contains(i)) {
                var minDist = Double.MAX_VALUE
                for (ind in indicesOfCentroids) {
                    val dist = euclideanDistance(records[i].record, records[ind].record)
                    if (dist < minDist) minDist = dist
                }
                sum += minDist
                if (sum > threshold) {
                    indicesOfCentroids.add(i)
                    return records[i].record
                }
            }
        }
        return HashMap()
    }

    fun recomputeCentroids(K: Int): LinkedList<HashMap<String, Double>> {
        val centroids = LinkedList<HashMap<String, Double>>()
        for (i in 0 until K) {
            centroids.add(calculateCentroid(i))
        }
        return centroids
    }

    fun randomFromDataSet(): HashMap<String, Double> {
        val index = random.nextInt(records.size)
        return records[index].record
    }

    private fun updateMin(name: String, `val`: Double) {
        if (minimums.containsKey(name)) {
            if (`val` < minimums[name]!!) {
                minimums[name] = `val`
            }
        } else {
            minimums[name] = `val`
        }
    }

    private fun updateMax(name: String, `val`: Double) {
        if (maximums.containsKey(name)) {
            if (`val` > maximums[name]!!) {
                maximums[name] = `val`
            }
        } else {
            maximums[name] = `val`
        }
    }

    private fun meanOfAttr(attrName: String, indices: LinkedList<Int>): Double {
        var sum = 0.0
        for (i in indices) {
            if (i < records.size) {
                sum += records[i].record[attrName]!!
            }
        }
        return sum / indices.size
    }

    private fun calculateCentroid(clusterNo: Int): HashMap<String, Double> {
        val centroid = HashMap<String, Double>()
        val recsInCluster = LinkedList<Int>()
        for (i in records.indices) {
            val record = records[i]
            if (record.clusterNo == clusterNo) {
                recsInCluster.add(i)
            }
        }
        for (name in attrNames) {
            centroid[name] = meanOfAttr(name, recsInCluster)
        }
        return centroid
    }

    private fun calculateClusterSSE(centroid: HashMap<String, Double>, clusterNo: Int): Double {
        var sse = 0.0
        for (i in records.indices) {
            if (records[i].clusterNo == clusterNo) {
                sse += euclideanDistance(centroid, records[i].record).pow(2.0)
            }
        }
        return sse
    }

    companion object {
        private val random = Random()

        fun euclideanDistance(a: HashMap<String, Double>, b: HashMap<String, Double>): Double {
            if (a.keys != b.keys) {
                return Double.POSITIVE_INFINITY
            }
            var sum = 0.0
            for (attrName in a.keys) {
                sum += (a[attrName]!! - b[attrName]!!).pow(2.0)
            }
            return sqrt(sum)
        }
    }
}
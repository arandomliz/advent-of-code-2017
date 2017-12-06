package Day06

import java.io.File

val src = "src/Day06/"

fun main(args : Array<String>) {
    println(solution1(content = "0 2 7 0"))
    println(solutionFast(content = "0 2 7 0"))
    // println(solution1(File(src + "input.txt")))
    println(solutionFast(File(src + "Day18/input.txt")))
}

fun Boolean.toInt() = if (this) 1 else 0

fun solution1(file : File? = null, content : String = "") : Pair<Int, Int> {
    var banks = (file?.readText() ?: content).split("[\t ]+".toRegex()).map { it.toInt() }.toIntArray()
    var history = arrayOf<IntArray>()

    do {
        history += banks.copyOf()

        val bIndex = banks.withIndex().maxBy { it.value }!!.index

        /* [. . # .] = 6 (index = 2)
        b: [2 1 1 2]

        x: 6 / size = 1
        y: 6 % size = 2

        b: [x x x x] + [1 0 0 1]

        3 - index =  1 ( <= y && > 0)
        0 - index = -2 + size = 2 ( <= y && > 0)
        1 - index = -1 + size = 3
        2 - index =  0 */

        val b = IntArray(banks.size, {i ->
            val v = (i - bIndex + (i <= bIndex).toInt() * banks.size)
            banks[bIndex] / banks.size + (v > 0 && v <= banks[bIndex] % banks.size).toInt()
        })
        banks[bIndex] = 0
        banks = banks.mapIndexed { index, i -> i + b[index]}.toIntArray()

    } while (history.indexOfFirst { it.contentEquals(banks) } == -1)

    return Pair(history.size, history.size - history.indexOfFirst { it.contentEquals(banks) })
}

fun solutionFast(file : File? = null, content : String = "") : Pair<Int, Int> {
    var banks = (file?.readText() ?: content).split("[\t ]+".toRegex()).map { it.toInt() }.toIntArray()
    val history = HashMap<Int, Int>()  // <-- Due to this beauty

    do {
        history.put(banks.contentHashCode(), history.size)

        val bIndex = banks.withIndex().maxBy { it.value }!!.index

        banks = banks.mapIndexed { i, value ->
            val v = (i - bIndex + (i <= bIndex).toInt() * banks.size)
            value * (i != bIndex).toInt() + banks[bIndex] / banks.size + (v > 0 && v <= banks[bIndex] % banks.size).toInt()
        }.toIntArray()
    } while (!history.containsKey(banks.contentHashCode()))

    return Pair(history.size, history.size - history.getValue(banks.contentHashCode()))
}
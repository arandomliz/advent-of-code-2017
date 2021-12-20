package Day13

import java.io.File
import kotlin.test.assertEquals

val src = "src/Day13/"

fun main(args : Array<String>) {
    assertEquals(24, solution1(File(src + "example01.txt")))
    println(solution1(File(src + "input-new.txt")))

    assertEquals(10, solution2(File(src + "example01.txt")))
    println(solution2(File(src + "input-new.txt")))

}

fun solution1(file : File) : Int {
    val record = file.readLines()
    val regex = """(\d+): (\d+)""".toRegex()

    var severity = 0

    record.forEach {
        val values = (regex.matchEntire(it)?.groupValues ?: listOf()).subList(1, 3).map { it.toInt() }

        if ( values[0] % ((values[1] - 1) * 2) == 0)
            severity += values[0] * values[1]
    }

    return severity
}

fun gotHit(record : List<IntArray>, delay : Int = 0) : Boolean {
    record.forEach {
        if ( (it[0] + delay) % it[1] == 0)
            return true
    }

    return false
}

fun solution2(file : File) : Int {
    val record = file.readLines()
    val regex = """(\d+): (\d+)""".toRegex()

    val inputs = record.map {
        val values = regex.matchEntire(it)?.groupValues ?: listOf()
        intArrayOf(values[1].toInt(), (values[2].toInt() - 1) * 2)
    }

    var delay = 0
    while (gotHit(inputs, delay))
        delay++

    return delay
}
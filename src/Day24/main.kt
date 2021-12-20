package Day24

import java.io.File
import kotlin.test.assertEquals

val src = "src/Day24/"

fun main(args : Array<String>) {
    assertEquals(listOf(1, 2, 3, 5), listOf(1, 2, 3, 4, 5).sublistWithout(4))

    assertEquals(31, solution1(File(src + "example.txt")))
    println(solution1(File(src + "input-new.txt")))

    assertEquals(Pair(19, 4), solution2(File(src + "example.txt")))
    println(solution2(File(src + "input-new.txt")))
}

fun solution1(file : File) : Int {
    val parts = file.readLines().map {
        val parted = it.split('/')
        Pair(parted[0].toInt(), parted[1].toInt())
    }

    // println(getBridge(0, parts))
    return getBridge(0, parts).maxByOrNull { it.first }?.first ?: 0
}

fun solution2(file : File) : Pair<Int, Int> {
    val parts = file.readLines().map {
        val parted = it.split('/')
        Pair(parted[0].toInt(), parted[1].toInt())
    }

    return getBridge2(0, parts)
}

fun getBridge(from : Int, parts : List<Pair<Int, Int>>) : List<Pair<Int, Int>> {
    val fitting = parts.filter { it.first == from || it.second == from }
    if (fitting.isEmpty())
        return listOf(Pair(from, 0))

    return fitting.flatMap {
        getBridge(it.toList()[1 - it.toList().indexOf(from)], parts.sublistWithout(it))
    }.map { Pair(it.first + from * 2, it.second + 1) }

    // println("Using $from on array ${parts.joinToString { it.toString() }}\n     Result: ${result.joinToString { it.toString() }}")
}

fun getBridge2(from : Int, parts: List<Pair<Int, Int>>) : Pair<Int, Int> {
    val fitting = parts.filter { it.first == from || it.second == from }
    if (fitting.isEmpty())
        return Pair(from, 0)

    val result = fitting.map {
        getBridge2(it.toList()[1 - it.toList().indexOf(from)], parts.sublistWithout(it))
    }.map { Pair(it.first + from * 2, it.second + 1) }

    val m = result.maxByOrNull { it.second }

    return result.filter { m?.second == it.second }.maxByOrNull { it.first }?: Pair(-1, -1)
}

fun <E> List<E>.sublistWithout(element : E) : List<E> {
    val i = indexOf(element)
    return subList(0, i) + subList(i + 1, size)
}
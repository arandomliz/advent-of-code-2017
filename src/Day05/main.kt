package Day05

import java.io.File

val src = "src/Day05/"

fun main(args : Array<String>) {
    // println(solution1(src + "input.txt"))
    println(solution2(src + "Day18/input.txt"))
}

fun solution1(fileName : String = "") : Int {
    val maze = File(fileName).readLines().map { it.toInt() }.toIntArray()

    var i = 0
    var steps = 0
    while (i < maze.size && i >= 0) {
        maze[i]++
        i += maze[i] - 1
        steps++
    }

    return steps
}

fun solution2(fileName : String = "") : Int {
    val maze = File(fileName).readLines().map { it.toInt() }.toIntArray()

    var i = 0
    var steps = 0
    while (i < maze.size && i >= 0) {
        val p = maze[i]
        maze[i] += if (p >= 3) -1 else 1
        i += p
        steps++
    }

    return steps
}
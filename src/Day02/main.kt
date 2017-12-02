package Day02

import java.io.File

val src = "src/Day02/"

fun main(args : Array<String>) {
    println(solution2(src + "example02.txt"))
    println(solution2(src + "Day18/input.txt"))
}

fun solution1(fileName : String) : Int {
    val fileList = File(fileName).readLines()

    return fileList.sumBy {
        val ns = it.split("[\t ]".toRegex()).map { it.toInt() }

        when {
            ns.isEmpty() -> 0
            else -> ns.max()!! - ns.min()!!
        }
    }
}

fun solution2(fileName : String) : Int {
    val fileList = File(fileName).readLines()



    return fileList.sumBy {
        val ns = it.split("[\t ]".toRegex()).map { it.toInt() }

        val out = Array(size = ns.size * ns.size, init = {
            Pair(ns[it / ns.size], ns[it % ns.size])
        })

        out.sumBy {
            if (it.first % it.second == 0 && it.first != it.second)
                it.first / it.second
            else 0
        }
    }
}
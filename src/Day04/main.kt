package Day04

import java.io.File

val src = "src/Day04/"

fun main(args : Array<String>) {
    println(solution1(content = "aa bb cc dd ee\naa bb cc dd aa\naa bb cc dd aaa\nbb cc  aa dd ee"))
    println(solution1(content = "aa bb cc dd aa"))
    println(solution1(content = "aa bb cc dd aaa"))
    println(solution1(content = "bb cc  aa dd ee"))
    println(solution1(fileName = src + "Day18/input.txt"))

    println(solution2(content = "aa bb cc dd ee\naa bb cc dd aa\naa bb cc dd aaa\nbb cc  aa dd ee"))
    println(solution2(fileName = src + "Day18/input.txt"))
}

fun solution1(fileName : String = "", content : String = "") : Int {
    val text = if (fileName.isNotEmpty()) File(fileName).readLines() else content.split("\n")

    return text.count {
        val words = it.split(" +".toRegex())
        !(0..words.lastIndex).any {
            val i = words[it]
            words.count { it == i } > 1
        }
    }
}

fun solution2(fileName : String = "", content : String = "") : Int {
    val text = if (fileName.isNotEmpty()) File(fileName).readLines() else content.split("\n")

    return text.count {
        val words = it.split(" +".toRegex()).map { it.toCharArray().sortedArray() }
        !words.any { val i = it; words.count { it.contentEquals(i) } > 1 }
    }
}
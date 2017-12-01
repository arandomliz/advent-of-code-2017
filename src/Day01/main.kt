package Day01

import java.io.File

val src = "src/Day01/"

fun main(args : Array<String>) {
    println(solution2(content = "1212"))
    println(solution2(content = "1221"))
    println(solution2(content = "123425"))
    println(solution2(content = "123123"))
    println(solution2(content = "12131415"))
    println(solution2(fileName = src + "Day18/input.txt"))
}

fun solution1(fileName : String = "", content : String = "") : Int {
    val text = (if (fileName.isNotEmpty()) File(fileName).readText() else content).toCharArray()

    val out = text.filterIndexed { index, c ->
        val i = when(index) {
            0 -> text.lastIndex
            else -> index - 1
        }
        text[i] == c
    }

    return out.sumBy { it.toInt() - 48 }
}

fun solution2(fileName : String = "", content : String = "") : Int {
    val text = (if (fileName.isNotEmpty()) File(fileName).readText() else content).toCharArray()

    val out = text.filterIndexed { index, c ->
        val i = (index + text.size / 2) % text.size
        text[i] == c
    }

    return out.sumBy { it.toInt() - 48 }
}
package Day01

import java.io.File

val src = "src/Day01/"

fun main(args : Array<String>) {
    println(solution1(content = "91212129"))
    println(solution1(fileName = src + "input-new.txt"))
    println(solution2(fileName = src + "input-new.txt"))
}

fun solution1(fileName : String = "", content : String = "") : Int {
    val text = (if (fileName.isNotEmpty()) File(fileName).readText() else content).trim().toCharArray()

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
    val text = (if (fileName.isNotEmpty()) File(fileName).readText() else content).trim().toCharArray()

    val out = text.filterIndexed { index, c ->
        val i = (index + text.size / 2) % text.size
        text[i] == c
    }

    return out.sumBy { it.toInt() - 48 }
}
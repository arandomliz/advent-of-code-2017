package Day09

import java.io.File
import kotlin.test.assertEquals

val src = "src/Day09/"

fun main(args : Array<String>) {
    assertEquals(1, solution1(input = "{}").score)
    assertEquals(6, solution1(input = "{{{}}}").score)
    assertEquals(5, solution1(input = "{{},{}}").score)
    assertEquals(16, solution1(input = "{{{},{},{{}}}}").score)
    assertEquals(1, solution1(input = "{<a>,<a>,<a>,<a>}").score)
    assertEquals(9, solution1(input = "{{<ab>},{<ab>},{<ab>},{<ab>}}").score)
    assertEquals(9, solution1(input = "{{<!!>},{<!!>},{<!!>},{<!!>}}").score)
    assertEquals(3, solution1(input = "{{<a!>},{<a!>},{<a!>},{<ab>}}").score)

    assertEquals(0, solution1(input = "<>").removedCharacters)
    assertEquals(17, solution1(input = "<random characters>").removedCharacters)
    assertEquals(3, solution1(input = "<<<<>").removedCharacters)
    assertEquals(2, solution1(input = "<{!>}>").removedCharacters)
    assertEquals(0, solution1(input = "<!!>").removedCharacters)
    assertEquals(0, solution1(input = "<!!!>>").removedCharacters)
    assertEquals(10, solution1(input = "<{o\"i!a,<{i<a>").removedCharacters)

    println(solution1(File(src + "input-new.txt")))
}

data class SolutionReturnValue(val score : Int, val removedCharacters : Int)

fun solution1(file : File? = null, input : String = "") : SolutionReturnValue {
    val chars = (file?.readText() ?: input).toCharArray()

    var score = 0
    var removed = 0
    var currentDepth = 0
    var inTrash = false
    var escaped = false
    chars.forEach {
        if (inTrash && !escaped && it != '!' && it != '>')
            removed++

        when {
            it == '<' -> inTrash = true
            it == '>' -> if (!escaped) inTrash = false
            inTrash -> {}  // Stop going further, if I'm in trash
            it == '{' -> {
                currentDepth++
                score += currentDepth
            }
            it == '}' -> currentDepth--
            else -> {}
        }



        escaped = it == '!' && !escaped
    }

    return SolutionReturnValue(score, removed)
}
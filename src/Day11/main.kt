package Day11

import java.io.File
import kotlin.math.max
import kotlin.math.sign
import kotlin.test.assertEquals

val src = "src/Day11/"

fun main(args : Array<String>) {
    assertEquals(3, solution(content = "ne,ne,ne").requiredSteps)
    assertEquals(0, solution(content = "ne,ne,sw,sw").requiredSteps)
    assertEquals(2, solution(content = "ne,ne,s,s").requiredSteps)
    assertEquals(3, solution(content = "se,sw,se,sw,sw").requiredSteps)
    println(solution(File(src + "input-new.txt")))
}

data class ReturnValue(val requiredSteps : Int, val maxDistance : Int)

fun solution(file : File? = null, content : String = "") : ReturnValue {
    val input = (file?.readText() ?: content).trim().split("""(, |,)""".toRegex())

    /** s -> - and n -> + **/
    var ns = 0
    /** e -> - and w -> + **/
    var we = 0

    var maxDistance = 0

    /* I've a grid. If I move straight up or down,
    I move two hexagons up and down, but if I move
    to the left and right, I'll only jump by one.
     */
    input.forEach {
        val neJump = 3 - it.length

        when(it[0]) {
            'n' -> ns += neJump
            's' -> ns -= neJump
        }

        if (it.length == 2) {
            when(it[1]) {
                'e' -> we--
                'w' -> we++
            }
        }

        maxDistance = max(maxDistance, position2minSteps(ns, we))
    }

    return ReturnValue(position2minSteps(ns, we), maxDistance)
}

fun position2minStepsText(ns : Int, we : Int) : String {
    var ns = ns
    var we = we

    var steps = arrayOf<String>()

    // Translate into steps
    while (ns != 0 || we != 0) {
        var move = charArrayOf()

        move += if (ns < 0) 's' else 'n'

        if (we != 0)
            move += if (we < 0) 'e' else 'w'

        ns -= ns.sign * (3 - move.size)

        if (move.size == 2)
            we -= we.sign

        steps += move.joinToString(separator = "")
    }

    return steps.contentToString()
}

fun position2minSteps(ns : Int, we : Int) : Int {
    var ns = ns
    var we = we

    var steps = 0

    // Translate into steps
    while (ns != 0 || we != 0) {
        ns -= ns.sign * if(we != 0) 1 else 2
        if (we != 0) we -= we.sign
        steps++
    }

    return steps
}
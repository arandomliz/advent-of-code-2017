package Day19

import javafx.geometry.Pos
import java.io.File
import kotlin.test.assertEquals

var src = "src/Day19/"

fun main(args: Array<String>) {
    assertEquals("ABCDEF", solution1(File(src + "example.txt")).first)
    assertEquals(38, solution1(File(src + "example.txt")).second)
    println(solution1(File(src + "input-new.txt")))
}

fun solution1(file: File) : Pair<String, Int> {
    val map = file.readLines().map { it.toCharArray() }

    val entry = Position(map[0].indexOf('|'), 0)

    return movePath(Direction.DOWN, entry, map)
}


fun movePath(prevDirection: Direction, pos : Position, map : List<CharArray>) : Pair<String, Int> {
    val direction = getDirection(pos, map).single {
        it != -prevDirection
    }
    var chars = ""
    var steps = 0

    do {
        pos.inc(direction)
        steps++

        if (map[pos].isLetter()) {
            chars += map[pos]
            // println("c : ${map[pos]}")
        } else if (map[pos] == ' ')
            return Pair(chars, steps)
    } while (map[pos] != '+')

    return Pair(chars, steps) + movePath(direction, pos, map)
}

fun getDirection(start: Position, map : List<CharArray>) : Array<Direction> {
    var r = arrayOf<Direction>()
    if (map[start.copy(x = start.x-1)] !in "| ")
        r += Direction.LEFT
    if (map[start.copy(x = start.x+1)] !in "| ")
        r += Direction.RIGHT
    if (map[start.copy(y = start.y-1)] !in "- ")
        r += Direction.UP
    if (map[start.copy(y = start.y+1)] !in "- ")
        r += Direction.DOWN

    return r
}

data class Position(var x : Int, var y : Int) {
    fun inc(dir : Direction) : Position {
        when(dir) {
            Direction.UP -> y--
            Direction.DOWN -> y++
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
        }

        return this
    }
}

enum class Direction(val symbol : Char) {
    UP('|'),
    DOWN('|'),
    LEFT('-'),
    RIGHT('-');

    operator fun unaryMinus() = when(this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }
}

operator fun List<CharArray>.get(p : Position) : Char {
    if (p.y > 0 && p.x > 0 &&
            p.y < size && p.x < get(p.y).size)
        return get(p.y)[p.x]
    return ' '
}

operator fun Pair<String, Int>.plus(s : Pair<String, Int>) : Pair<String, Int> {
    return Pair(this.first + s.first, this.second + s.second)
}
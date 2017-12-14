package Day14

import kotlin.test.assertEquals

fun main(args : Array<String>) {
    assertEquals(8108, solution1("flqrgnkx"))
    println(solution1("vbqugkhl"))

    assertEquals(1242, solution2("flqrgnkx"))
    println(solution2("vbqugkhl"))
}

fun solution1(key : String) : Int {
    return (0..127).sumBy {
        Day10.solution2(content = "$key-$it").chunked(8)
                .map { it.toLong(16) }
                .sumBy { countBits(it) }
    }
}

fun countBits(a: Long) : Int {
    var count = 0
    var no = a

    while (no > 0) {
        no = no and no - 1
        ++count
    }

    return count
}

fun solution2(key : String) : Int {
    val grid = (0..127).map {
        Day10.solution2(content = "$key-$it").chunked(4).
                map { it.toInt(16) }
                .joinToString(separator = "") { String.format("%16s", it.toString(2)).replace(" ", "0")}
                .map { it.toInt() - 49 }.toIntArray()
    }.toTypedArray()

    val set = grid.getAllFree()

    var groups = 0
    while (set.size > 0) {
        groups++

        val from = set.first()
        markGroup(from, grid, groups).forEach { set.remove(it) }
    }

    return groups
}

fun markGroup(pos : Coordinate, grid : Array<IntArray>, cGroup : Int) : Array<Coordinate> {
    grid[pos] = cGroup

    var ret = arrayOf(pos)
    grid.getLowSiblings(pos)
            .forEach { ret += markGroup(it, grid, cGroup) }

    return ret
}

data class Coordinate(var x : Int, var y : Int) {
    fun above() = Coordinate(x, y - 1)
    fun below() = Coordinate(x, y + 1)
    fun left() = Coordinate(x - 1, y)
    fun right() = Coordinate(x + 1, y)

    fun neighbors() = arrayOf(above(), below(), left(), right())
}

operator fun Array<IntArray>.get(pos : Coordinate) : Int {
    return if(pos.x < 0 || pos.x > 127 || pos.y < 0 || pos.y > 127) -1 else get(pos.x)[pos.y]
}

operator fun Array<IntArray>.set(pos: Coordinate, value : Int) {
    get(pos.x)[pos.y] = value
}

fun Array<IntArray>.getLowSiblings(pos : Coordinate) =
        pos.neighbors().filter { this[it] == 0 }.toTypedArray()

fun Array<IntArray>.getAllFree() : HashSet<Coordinate> {
    val hSet = HashSet<Coordinate>()

    forEachIndexed { x, ints ->
        ints.forEachIndexed { y, i ->
            if (i == 0)
                hSet.add(Coordinate(x, y))
        }
    }

    return hSet
}


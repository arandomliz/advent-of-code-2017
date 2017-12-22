package Day22

import java.awt.Point
import java.io.File
import kotlin.test.assertEquals

val src = "src/Day22/"

fun main(args : Array<String>) {
    // solution1(File(src + "example.txt"), iterations = 880)
    assertEquals(41, solution1(File(src + "example.txt"), iterations = 70))
    assertEquals(5587, solution1(File(src + "example.txt")))
    println(solution1(File(src + "input.txt")))

    assertEquals(26, solution2(File(src + "example.txt"), iterations = 100))
    assertEquals(2511944, solution2(File(src + "example.txt")))
    println(solution2(File(src + "input.txt")))

}

fun solution1(file : File, iterations : Int = 10_000) : Int {
    val grid = InfiniteGrid.parse(file.readLines())

    val positionV = Point(grid.width / 2,grid.height / 2)
    /**
     * Directions are represented as an integer
     *
     *    0
     *  3   1
     *    2
     * # = (4 + #) % 4
     * p.x += (# % 2) * (2 - #)
     * p.y += ((# + 1) % 2) * (# - 1)
     */
    var directionV = 0

    var posBursts = 0

    repeat(iterations) {
        directionV += if (grid[positionV] == 0) -1 else 1
        directionV = (4 + directionV) % 4

        if (grid.switch(positionV) == 2) posBursts++

        positionV.x += (directionV % 2) * (2 - directionV)
        positionV.y += ((directionV + 1) % 2) * (directionV - 1)
    }

    // println(grid)

    return posBursts
}


fun solution2(file : File, iterations : Int = 10_000_000) : Int {
    val grid = InfiniteGrid.parse(file.readLines())

    val positionV = Point(grid.width / 2,grid.height / 2)
    /**
     * Directions are represented as an integer
     *
     *    0
     *  3   1
     *    2
     * # = (4 + #) % 4
     * p.x += (# % 2) * (2 - #)
     * p.y += ((# + 1) % 2) * (# - 1)
     */
    var directionV = 0

    var posBursts = 0

    repeat(iterations) {
        directionV += grid[positionV] - 1
        directionV = (4 + directionV) % 4

        if (grid.cycle(positionV) == 2) posBursts++

        positionV.x += (directionV % 2) * (2 - directionV)
        positionV.y += ((directionV + 1) % 2) * (directionV - 1)
    }

    return posBursts
}
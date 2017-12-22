package Day22

import java.awt.Point

class InfiniteGrid(var grid : MutableList<MutableList<Int>> = ArrayList()) {

    var width = 0
    var height = 0

    init {
        width = grid.map { it.size }.toSet().single()
        height = grid.size
    }

    companion object {
        fun parse(input : List<String>) =
                InfiniteGrid(input.map { it.map { if(it == '#') 2 else 0 }.toMutableList() }.toMutableList())
    }

    operator fun set(p : Point, v : Int) {
        if (p.y < 0) {
            (0 until -p.y).forEach { grid.add(0, MutableList(width) {0} ) }
            p.y = 0
        } else if (p.y >= height)
            (height .. p.y).forEach { grid.add(MutableList(width) {0} ) }
        height = grid.size

        if (p.x < 0) {
            (0 until -p.x).forEach { grid.forEach { it.add(0, 0) } }
            width -= p.x
            p.x = 0
        } else if (p.x >= width) {
            (width .. p.x).forEach { grid.forEach { it.add(0) } }
            width = p.x + 1
        }

        grid[p.y][p.x] = v
    }

    fun cycle(p : Point) : Int {
        set(p, (get(p) + 1) % 4)
        return get(p)
    }

    fun switch(p : Point) : Int {
        set(p, (get(p) + 2) % 4)
        return get(p)
    }

    operator fun get(p : Point)  =
            if (p.x in (0 until width) && p.y in (0 until height)) grid[p.y][p.x] else 0

    override fun toString(): String {
        return (0 until width * height).joinToString(separator = " ", prefix = " ") {
            when(grid[it / width][it % width]) {
                1 -> 'W'
                2 -> '#'
                3 -> 'F'
                else -> '.'
            } + if ((it + 1) % width == 0) "\n" else ""
        }
    }
}
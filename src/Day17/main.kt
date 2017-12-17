package Day17

import kotlin.test.assertEquals

fun main(args : Array<String>) {
    assertEquals(638, solution1(3))
    println(solution1(359))

    println(solution2(359))
}

fun solution1(jump_size : Int) : Int {
    val memory = arrayListOf(0)

    var i = 0
    repeat(2017, {
        i = (i + jump_size) % memory.size
        memory.add(++i, it + 1)
        // println("$i: ${memory.joinToString()}")
    })

    return memory[ (i+1) % memory.size ]
}

fun solution2(jump_size: Int) : Int {
    var i = 0
    var size = 1
    var current = -1
    repeat(50_000_000, {
        i = (i + jump_size) % size++
        if (++i == 1)
            current = it + 1
        // println("$i / ${size -1} and 1 : $current")
    })

    return current
}
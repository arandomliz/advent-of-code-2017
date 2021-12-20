package Day15

import kotlin.test.assertEquals

fun main(args : Array<String>) {
    assertEquals(588, solution1(65, 8921))
    println(solution1(722, 354))  // old input
    println(solution1(634, 301))  // new input

    assertEquals(309, solution2(65, 8921))
    println(solution2(722, 354))  // old input
    println(solution2(634, 301))  // new input
}

fun solution1(startA : Int, startB : Int) : Int {
    var valA = startA.toLong()
    var valB = startB.toLong()
    val mask = (Math.pow(2.0, 16.0).toLong()) - 1

    var count = 0

    repeat(40000000, {
        valA = (valA * 16807) % 2147483647
        valB = (valB * 48271) % 2147483647

        if (valA and mask == valB and mask)
            count++
    })

    return count
}

fun solution2(startA: Int, startB: Int) : Int {
    var valA = startA.toLong()
    var valB = startB.toLong()
    val mask = (Math.pow(2.0, 16.0).toLong()) - 1

    var count = 0

    repeat(5000000, {
        do { valA = (valA * 16807) % 2147483647
        } while (valA % 4 != 0L)

        do { valB = (valB * 48271) % 2147483647
        } while (valB % 8 != 0L)

        if (valA and mask == valB and mask)
            count++
    })

    return count
}
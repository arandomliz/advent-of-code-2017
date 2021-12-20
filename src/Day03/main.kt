package Day03

import Day02.src
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun main(args : Array<String>) {

    //for (i in 0..23)
    //    println("$i -> ${solution1(i)}")

    /*println(solution1(1))
    println(solution1(12))
    println(solution1(19))
    println(solution1(23))
    println(solution1(15))*/
    // println(solution1(1024))
    println(solution1(square = 347991))
    println(solution1(square = 277678))

}

/**
 * @param square This is the square number, the default value is my puzzle number.
 */
fun solution1(square : Int = 347991) : Int {
    var cache = square
    var width = 2
    var steps = 0

    while (cache > width) {
        cache -= width - 1
        width += steps % 2
        steps++
    }

    return when {
        (steps % 2) == 1 && (width % 2) == 0 ->
            abs(cache - width / 2) +  // y
                    abs(width / 2)    // x
        (steps % 2) == 0 && (width % 2) == 0 ->
            abs(floor((width - 1)/ 2.0).toInt()) +  // y
                    abs(cache - width / 2)          // x
        (steps % 2) == 1 && (width % 2) == 1 ->
                abs(cache - ceil(width / 2.0).toInt()) +  // y
                        abs(floor(width / 2.0).toInt())      // x
        (steps % 2) == 0 && (width % 2) == 1 ->
            abs( (width - 1) / 2) +                      // y
                    abs(cache - ceil(width / 2.0).toInt())   // x
        else -> 0
    }
}


fun solution2(square : Int) : Int {
    TODO("See https://oeis.org/a141481")
}
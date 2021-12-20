package Day21

import java.io.File
import kotlin.test.assertEquals

/*
 * For every shape, there're 7 ways to change
 * it, simply by applying the operations:
 *      | , - , / and \
 * to it.
 * These are:
 *      - , | , / or \ and
 *      + ,
 *      - . \ and - . /
 * That works for 2x2 squares and 3x3 ones
 *
 *
 * Each array is field is represented by a
 * single BooleanArray. Whereby the length of
 * the array is image size ^ 2
 * The IntArray class is extended for better
 * handling.
 */


val src = "src/Day21/"

fun main(args : Array<String>) {
    assertEquals(12, solution1(File(src + "example.txt"), iterations = 2))
    println(solution1(File(src + "input-new.txt")))

    println(solution1(File(src + "input-new.txt"), iterations = 18))
}

fun solution1(ruleFile : File, iterations : Int = 5) : Int {
    val book = buildRulebook(ruleFile)

    var shape = parseString(".#./..#/###")

    repeat(iterations) {
        shape = shape.fragmentate().map { book[it.to1DString()]!! }.joinFragments()
    }

    return shape.count { it }
}

fun buildRulebook(ruleFile : File) =
        ruleFile.readLines().flatMap {
            val split = it.split(" => ")
            getPermutations(split[0]).map { Pair(it.to1DString(), parseString(split[1])) }
        }.toMap()

fun getPermutations(original : String) : List<BooleanArray> {
    val orig = parseString(original)
    val perms = arrayListOf(orig)  // 0:

    perms.add( orig.swapColumns() )  // 1: |
    perms.add( orig.swapRows() )     // 2: -
    perms.add( orig.swapRows().swapColumns() )  // 3: +
    perms.add( orig.transpose() )    // 4: \
    perms.add( perms[3].transpose() )  // 5: / = + . \
    perms.add( orig.swapColumns().transpose() )  // 6: | . \  = - . /
    perms.add( orig.swapRows().transpose() )  // 7: - . \

    return perms
}

fun parseString(field : String) =
        field.replace("/", "").map { it == '#' }.toBooleanArray()

data class Point(val x : Int, val y : Int) {
    fun flip() = Point(y, x)
}

fun BooleanArray.imgSize() = Math.sqrt(size.toDouble()).toInt()
operator fun BooleanArray.get(p : Point) = get(imgSize() * p.y + p.x)
operator fun BooleanArray.set(p : Point, v : Boolean) = set(imgSize() * p.y + p.x, v)

fun BooleanArray.coordinates() = cartesianProduct(0, imgSize())

fun BooleanArray.to1DString(delimiter : Char = '\n') = mapIndexed { index, b ->
    (if (b) "#" else ".") + if ( (index + 1) % imgSize() == 0 && index != lastIndex) delimiter else "" }
        .joinToString(separator = "")

/** \ **/
fun BooleanArray.transpose() : BooleanArray {
    val transposed = BooleanArray(size)
    coordinates().forEach { transposed[it] = get(it.flip())}
    return transposed
}

/** - **/
fun BooleanArray.swapRows() : BooleanArray {
    val edited = BooleanArray(size)
    coordinates().forEach { edited[it] = get(Point(it.x, imgSize() - it.y - 1)) }
    return edited
}

/** | **/
fun BooleanArray.swapColumns() : BooleanArray {
    val edited = BooleanArray(size)
    coordinates().forEach { edited[it] = get(Point(imgSize() - it.x - 1, it.y)) }
    return edited
}

/* And finally / = - . | . \ */

fun BooleanArray.fragmentate() : List<BooleanArray> {
    val factor = if (imgSize() % 2 == 0) 2 else 3
    val amount = imgSize() / factor
    val fragments = List(amount * amount, { BooleanArray(factor * factor) })

    cartesianProduct(0, amount).forEach { z ->
        val f = fragments[z.x + z.y * amount]
        f.coordinates().forEach {
            f[it] = get(Point(z.x * factor + it.x, z.y * factor + it.y))
        }
    }

    return fragments
}

fun List<BooleanArray>.joinFragments() : BooleanArray {
    val joined = BooleanArray(size * get(0).size)
    val listW = Math.sqrt(size.toDouble()).toInt()
    val fragW = get(0).imgSize()
    val width = listW * fragW

    cartesianProduct(0, width).forEach {
        val i = it.x / fragW + it.y / fragW * listW
        joined[it] = get(i)[Point(it.x % fragW, it.y % fragW)]
    }

    return joined
}

fun cartesianProduct(from : Int, to : Int) =
        (from until to).flatMap { x -> (from until to).map { y -> Point(x, y) } }
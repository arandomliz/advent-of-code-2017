package Day10

import java.io.File
import kotlin.test.assertEquals

val src = "src/Day10/"

fun IntArray.reverseLoopedArray(position : Int, length : Int) {
    val pos = position % size

    val array = (0 until length).map { get( (it + pos) % size) }.reversed()
    array.mapIndexed { index, i -> set((index + pos) % size, i) }
}


fun main(args : Array<String>) {
    assertEquals(12, solution1(content = "3, 4, 1, 5", size = 5))
    println("Solution 1: ${solution1(file = File(src + "input-new.txt"))}")

    assertEquals("a2582a3a0e66e6e86e3812dcb672a272", solution2(content = ""))
    assertEquals("33efeb34ea91902bb2f59c9920caa6cd", solution2(content = "AoC 2017"))
    assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", solution2(content = "1,2,3"))
    assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", solution2(content = "1,2,4"))
    println("Solution 2: ${solution2(file = File(src + "input-new.txt"))}")
}


fun solution1(file : File? = null, content : String = "", size : Int = 256) : Int {
    val sequence = (file?.readText() ?: content).trim().split("""(, |,)""".toRegex()).map { it.toInt() }
    val string = IntArray(size, {i -> i})

    var pos = 0
    var skip_size = 0
    sequence.forEach {
        string.reverseLoopedArray(pos, it)
        pos += it + skip_size
        skip_size++
    }

    return string[0] * string[1]
}

fun solution2(file : File? = null, content : String = "", size : Int = 256) : String {
    val sequence = (file?.readText() ?: content).trim().toByteArray() +
            byteArrayOf(17, 31, 73, 47, 23)  // static ending

    val string = IntArray(size, {i -> i})

    var pos = 0
    var skip_size = 0
    repeat(64, {
        sequence.forEach {
            string.reverseLoopedArray(pos, it.toInt())
            pos += it + skip_size
            skip_size++
        }
    })

    val denseHash = (0 until string.size / 16).map {
        string.copyOfRange(it * 16, (it + 1) * 16).reduce { acc, i -> (acc xor i) }
    }

    return denseHash.joinToString(separator = "", transform = { v -> String.format("%02x", v) })
}

package Day16

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

val src = "src/Day16/"

fun main(args : Array<String>) {
    assertEquals("baedc", solution(content = "s1,x3/4,pe/b", length = 5, debug = true))
    println(solution(File(src + "Day18/input.txt")))

    // assertEquals("baedc", solution2(content = "s1,x3/4,pe/b", length = 5))
    println(solution2(File(src + "Day18/input.txt")))
}

fun solution(file : File? = null, content : String = "", length : Int = 16, debug : Boolean = false) : String {
    val cmd = (file?.readText() ?: content).split(",")
    var prgms = (0 until length).map { (it + 97).toChar() }.toTypedArray()

    if (debug)
        print("Start:".padEnd(8, ' '))

    cmd.forEach {
        if (debug)
            print("${prgms.contentToString()} \n${(it + ":").padEnd(8, ' ')}")

        when (it[0]) {
            's' -> {  // spin
                val amount = parseSpin(it)
                val a = prgms.copyOfRange(length - amount, length)
                val b = prgms.copyOfRange(0, length - amount)

                prgms = a + b
            }
            'x' -> {  // swap position
                val indexes = parseSwapX(it)
                val a = prgms[indexes.first]

                prgms[indexes.first] = prgms[indexes.second]
                prgms[indexes.second] = a
            }
            'p' -> {  // swap program
                val which = parseSwapP(it)
                val i1 = prgms.indexOf(which.first)
                val i2 = prgms.indexOf(which.second)

                prgms[i1] = which.second
                prgms[i2] = which.first
            }
        }
    }

    if (debug)
        println(prgms.contentToString())

    return prgms.joinToString(separator = "")
}

val ONE_BILLION = 1_000_000_000

fun solution2(file : File? = null, content : String = "", length : Int = 16, times : Int = ONE_BILLION) : String {
    val cmd = (file?.readText() ?: content).split(",")
    val start = (0 until length).map { (it + 97).toChar() }.toTypedArray()
    var prgms = start.clone()

    val loop = (0..times).find {
        prgms = runCycle(cmd, prgms)
        prgms contentEquals start
    }!!

    (0 until times % (loop + 1)).forEach {
        prgms = runCycle(cmd, prgms)
    }

    return prgms.joinToString(separator = "")
}

fun runCycle(cmd : List<String>, prgms : Array<Char>) : Array<Char> {
    var ret = prgms.copyOf()
    cmd.forEach {
        when (it[0]) {
            's' -> {  // spin
                val amount = parseSpin(it)
                val a = ret.copyOfRange(prgms.size - amount, prgms.size)
                val b = ret.copyOfRange(0, prgms.size - amount)

                ret = a + b
            }
            'x' -> {  // swap position
                val indexes = parseSwapX(it)
                val a = ret[indexes.first]

                ret[indexes.first] = ret[indexes.second]
                ret[indexes.second] = a
            }
            'p' -> {  // swap program
                val which = parseSwapP(it)
                val i1 = ret.indexOf(which.first)
                val i2 = ret.indexOf(which.second)

                ret[i1] = which.second
                ret[i2] = which.first
            }
        }
    }

    return ret
}

/** UNUSED, can be removed **/
fun solutionFast(file : File? = null, content : String = "", length : Int = 16, times : Int = 1000000000) : String {
    val cmd = (file?.readText() ?: content).split(",").map {
        when (it[0]) {
            's' -> {
                Triple(it[0], length - parseSpin(it), -1)
            }
            'x' -> {
                val indexes = parseSwapX(it)
                Triple(it[0], indexes.first, indexes.second)
            }
            'p' -> {
                val which = parseSwapP(it)
                Triple(it[0], which.first.toInt(), which.second.toInt())
            }
            else -> Triple(' ', -1, -1)
        }
    }

    var prgms = (0 until length).map { (it + 97).toChar() }.toMutableList()

    val elapsed = mutableMapOf(Pair('s', 0L), Pair('x', 0L), Pair('p', 0L))
    val amount = mutableMapOf(Pair('s', 0), Pair('x', 0), Pair('p', 0))

    repeat(times) {
        if (it % 10000000 == 0) println("$it: ${System.currentTimeMillis()}")
        if (it % 10000 == 0 && it != 0)
            println("s: ${elapsed['s']!! / amount['s']!!.toDouble()}\n" +
                    "x: ${elapsed['x']!! / amount['x']!!.toDouble()}\n" +
                    "p: ${elapsed['p']!! / amount['p']!!.toDouble()}\n")

        cmd.forEach {
            amount[it.first] = amount[it.first]!! + 1
            elapsed[it.first] = elapsed[it.first]!! + measureNanoTime {
                when (it.first) {
                    's' -> {  // spin - 216ns
                        /*prgms = prgms.mapIndexed { index, c ->
                            prgms[(index + length - it.second) % length]
                        }*/
                        val a = prgms.subList(0, it.second)
                        prgms = prgms.drop(it.second).toMutableList()
                        prgms.addAll(a)
                    }
                    'x' -> {  // swap position - 49ns
                        val a = prgms[it.second]
                        prgms[it.second] = prgms[it.third]
                        prgms[it.third] = a
                    }
                    'p' -> {  // swap program - 91ns
                        val i1 = prgms.indexOf(it.second.toChar())
                        val i2 = prgms.indexOf(it.third.toChar())

                        prgms[i1] = it.third.toChar()
                        prgms[i2] = it.second.toChar()
                    }
                }
            }
        }


    }

    return prgms.joinToString(separator = "")
}

fun parseSpin(a : String) : Int = a.substring(1).toInt()
fun parseSwapX(a : String) : Pair<Int, Int> {
    val i = a.indexOf('/')
    val n1 = a.substring((1 until i)).toInt()
    val n2 = a.substring(i + 1).toInt()
    return Pair(n1, n2)
}
fun parseSwapP(a : String) = Pair(a[1], a[3])
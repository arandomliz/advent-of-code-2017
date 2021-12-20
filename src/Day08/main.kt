package Day08

import java.io.File
import kotlin.math.max

val src = "src/Day08/"

fun main(args : Array<String>) {
    println(solution1(File(src + "example01.txt")))
    println(solution1(File(src + "input-new.txt")))
}

enum class IncDec {
    inc,
    dec;

    fun getValue(x: Int): Int = if (this == inc) x else -x
}

data class SolutionReturnValue(val highest : Int, val highestEver : Int)

fun solution1(file : File) : SolutionReturnValue {
    val lines = file.readLines()

    val registers = HashMap<String, Int>()
    val regex = """^(\w+) (inc|dec) (-?\d+) if (\w+) (>=|<=|>|<|!=|==) (-?\d+)$""".toRegex()

    var highestEver = Int.MIN_VALUE

    lines.forEach {
        val groups = (regex.find(it, 0)?.groupValues ?: listOf()).toTypedArray()


        val amount = IncDec.valueOf(groups[2]).getValue(groups[3].toInt())
        val compareToV = registers.getOrDefault(groups[4], 0).compareTo(groups[6].toInt())
        val shouldDo = when(groups[5]) {
            ">=" -> compareToV >= 0
            ">"  -> compareToV > 0
            "<=" -> compareToV <= 0
            "<"  -> compareToV < 0
            "==" -> compareToV == 0
            "!=" -> compareToV != 0
            else -> throw IllegalArgumentException("$it couldn't be read")
        }

        if (shouldDo) {
            registers[groups[1]] = registers.getOrDefault(groups[1], 0) + amount
            highestEver = max(highestEver, registers[groups[1]]!!)
        }
    }

    return SolutionReturnValue(registers.maxByOrNull { it.value }!!.value, highestEver)
}
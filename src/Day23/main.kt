package Day23

import java.io.File

val src = "src/Day23/"

fun main(args : Array<String>) {
    println(solution1(File(src + "input.txt")))
    println(solution2(File(src + "test.txt")))
    // println(solution2(File(src + "efficient.txt")))
}



fun solution1(file : File) : Int {
    val cmds = file.readLines().map {
        val groups = it.split(" ")
        when(groups[0]) {
            "set" -> SET(groups[1], groups[2])
            "sub" -> SUB(groups[1], groups[2])
            "mul" -> MUL(groups[1], groups[2])
            else ->  JNZ(groups[1], groups[2])
        }
    }

    val state = ComputerState()

    var mulTriggered = 0

    while (state.executionLine >= 0 && state.executionLine < cmds.size) {
        if (cmds[state.executionLine] is MUL) mulTriggered++
        cmds[state.executionLine].execute(state)

        state.executionLine++
    }

    return mulTriggered
}


/**
 * Solution is 905 using this command in the
 * Wolfram Language:
 * `Count[Map[PrimeQ, Table[i, {i, 106700, 123700, 17}]], False]`
 */
fun solution2(file : File) : Long? {
    val cmds = file.readLines().map {
        val groups = it.split(" ")
        when(groups[0]) {
            "set" -> SET(groups[1], groups[2])
            "sub" -> SUB(groups[1], groups[2])
            "mul" -> MUL(groups[1], groups[2])
            else ->  JNZ(groups[1], groups[2])
        }
    }

    val state = ComputerState()
    state.registers["a"] = 1

    while (state.executionLine >= 0 && state.executionLine < cmds.size) {
        if (state.executionLine == 18)
            println(state.registers.toList().toTypedArray().contentDeepToString())

        cmds[state.executionLine].execute(state)
        state.executionLine++
    }

    return state.registers["h"]
}
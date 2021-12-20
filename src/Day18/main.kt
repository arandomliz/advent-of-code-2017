package Day18

import java.io.File
import java.util.concurrent.*
import kotlin.concurrent.thread
import kotlin.test.assertEquals

val src = "src/Day18/"

fun main(args : Array<String>) {
    assertEquals(4, solution1(File(src + "example01.txt")))
    assertEquals(16, solution1(File(src + "example02.txt")))
    println(solution1(File(src + "input-new.txt")))

    //println(solution2_2(File(src + "example03.txt")))
    println(solution2_2(File(src + "input-new.txt")))
    //solution2(File(src + "input.txt"))
}

data class ComputerState(
        val registers : MutableMap<String, Long> = mutableMapOf(),
        var lastPlayed : Long = 0,
        var executionLine : Int = 0)

fun solution1(file : File) : Long? {
    val cmds = file.readLines().map {
        val groups = it.split(" ")
        when(groups[0]) {
            "snd" -> SND(groups[1])
            "set" -> SET(groups[1], groups[2])
            "add" -> ADD(groups[1], groups[2])
            "mul" -> MUL(groups[1], groups[2])
            "mod" -> MOD(groups[1], groups[2])
            "rcv" -> RCV(groups[1])
            else ->  JGZ(groups[1], groups[2])
        }
    }

    val state = ComputerState()
    while (state.executionLine >= 0 && state.executionLine < cmds.size) {
        val r = cmds[state.executionLine].execute(state)
        if (r != null) return r

        state.executionLine++
    }

    return null
}

/**
 * Problem was. There's the line "jgz 1 3" in the input.
 * So I defined 1 with the value 0. I think the consequences
 * are without further explanation obvious.
 */
fun solution2_2(file : File) : Int {
    val cmds = file.readLines().map {
        val groups = it.split(" ")
        when (groups[0]) {
            "snd" -> SND(groups[1])
            "set" -> SET(groups[1], groups[2])
            "add" -> ADD(groups[1], groups[2])
            "mul" -> MUL(groups[1], groups[2])
            "mod" -> MOD(groups[1], groups[2])
            "rcv" -> RCV(groups[1])
            else -> JGZ(groups[1], groups[2])
        }
    }

    val s0 = ComputerState(mutableMapOf(Pair("p", 0L)).withDefault { 0 })
    val s1 = ComputerState(mutableMapOf(Pair("p", 1L)).withDefault { 0 })

    var amount = 0
    var queue = arrayOf<Long>()

    bigLoop@ while (s0.executionLine >= 0 && s0.executionLine < cmds.size) {
        val c0 = cmds[s0.executionLine]

        when(c0) {
            is SND -> queue += c0.getNumber(s0, c0.register)
            is RCV -> {
                loop@ while (s1.executionLine >= 0 && s1.executionLine < cmds.size) {
                    val c1 = cmds[s1.executionLine]
                    s1.executionLine++

                    when (c1) {
                        is SND -> {
                            s0.registers[c0.register] = c1.getNumber(s1, c1.register)
                            amount++
                            break@loop
                        }

                        is RCV -> {
                            if (queue.isEmpty())
                                break@bigLoop

                            s1.registers[c1.register] = queue[0]
                            queue = queue.drop(1).toTypedArray()

                        }

                        else -> c1.execute(s1)
                    }
                }

            }

            else -> c0.execute(s0)
        }

        s0.executionLine++
    }

    return amount
}

fun solution2(file : File) {
    val cmds = file.readLines().map {
        val groups = it.split(" ")
        when(groups[0]) {
            "snd" -> SND2(groups[1])
            "set" -> SET(groups[1], groups[2])
            "add" -> ADD(groups[1], groups[2])
            "mul" -> MUL(groups[1], groups[2])
            "mod" -> MOD(groups[1], groups[2])
            "rcv" -> RCV(groups[1])
            else ->  JGZ(groups[1], groups[2])
        }
    }

    val queue0 = LinkedBlockingQueue<Long>()
    val queue1 = LinkedBlockingQueue<Long>()

    var amount = 0

    fun runThread(id : Long) = thread {
        val state = ComputerState(hashMapOf(Pair("p", id)))

        val myQueue = if (id == 1L) queue1 else queue0

        loop@ while (state.executionLine >= 0 && state.executionLine < cmds.size) {
            val c = cmds[state.executionLine]
            when(c) {
                is SND2 -> {
                    if (id == 1L) {
                        amount++
                        queue0.put(c.execute(state))
                    } else
                        queue1.put(c.execute(state))
                }
                is RCV -> {
                    if (queue0.isEmpty() && queue1.isEmpty())
                        println("$id : $amount")

                    state.registers[c.register] = myQueue.take()
                }
                else -> c.execute(state)
            }

            state.executionLine++
        }

        println("$id: $amount")
    }

    runThread(0)
    runThread(1)
}


sealed class Cmd {
    abstract fun execute(state : ComputerState) : Long?
    fun getNumber(state: ComputerState, n : String) =
            state.registers.getOrElse(n, { n.toLongOrNull() ?: 0L })
}
class SND(val register : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.lastPlayed = state.registers.getOrPut(register) { 0 }
        return null
    }
}
data class SET(val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, y)
        return null
    }
}
data class ADD(val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, y) + state.registers.getOrPut(register) { 0 }
        return null
    }
}
data class MUL(val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, y) * state.registers.getOrPut(register) { 0 }
        return null
    }
}
data class MOD(val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = state.registers.getOrPut(register) { 0 } % getNumber(state, y)
        return null
    }
}
data class RCV(val register: String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        if (state.registers.getOrPut(register) { 0L } != 0L)
            return state.lastPlayed
        return null
    }
}
data class JGZ(val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        if (getNumber(state, register) > 0)
            state.executionLine += (getNumber(state, y) - 1).toInt()
        return null
    }
}

data class SND2(val x : String) : Cmd() {
    override fun execute(state: ComputerState): Long? = getNumber(state, x)
}
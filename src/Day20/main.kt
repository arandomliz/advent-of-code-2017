package Day20

import java.io.File
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

val src = "src/Day20/"

fun main(args : Array<String>) {
    assertEquals(0, solution1(File(src + "example01.txt")))
    println(solution1(File(src + "input.txt")))

    assertEquals(1, solution2(File(src + "example02.txt")))
    println(solution2(File(src + "input.txt")))
}

fun solution1(file : File) : Int {
    val particles = file.readLines().mapIndexed { index, s -> Particle(s, index) }
    repeat(10_000, { particles.forEach { it.iterate() } })
    return particles.minBy { it.distanceFromOrigin() }?.id ?: -1
}

fun solution2(file : File) : Int {
    val particles = file.readLines().mapIndexed { index, s -> Particle(s, index) }.toMutableList()
    repeat(1_000, {
        particles.forEach { it.iterate() }

        var remove = setOf<Particle>()
        (0 until particles.size).flatMap { i1 -> ((i1 + 1) until particles.size).map { i2 -> Pair(i1, i2) } }.forEach {
            assert(it.first != it.second)
            if (particles[it.first].position == particles[it.second].position) {
                remove += particles[it.first]
                remove += particles[it.second]
            }
        }
        remove.forEach { particles.remove(it) }
    })

    return particles.size
}

data class Vector3d(var x : Int = 0, var y : Int = 0, var z : Int = 0) {
    operator fun plusAssign(v : Vector3d) {
        x += v.x; y += v.y; z += v.z
    }
}

data class Particle(var position : Vector3d, var velocity : Vector3d, var acceleration : Vector3d, var id : Int = -1) {

    companion object {
        // val regex = """p=<((?:-?\d+,?){3})>, v=<((?:-?\d+,?){3})>, a=<((?:-?\d+,?){3})>""".toRegex()
        val regex = """([pva])=<([\d,-]+)>""".toRegex()
    }

    constructor(string: String, id : Int) : this(Vector3d(), Vector3d(), Vector3d()) {
        val matches = regex.findAll(string).map { it.groupValues }
        for (values in matches) {
            val variable = when(values[1]) {
                "p" -> position
                "v" -> velocity
                "a" -> acceleration
                else -> Vector3d()
            }

            val integers = values[2].split(",").map { it.toInt() }
            variable.x = integers[0]
            variable.y = integers[1]
            variable.z = integers[2]
        }

        this.id = id
    }

    fun iterate() {
        velocity += acceleration
        position += velocity
    }

    fun distanceFromOrigin() = with(velocity, {
        x.absoluteValue + y.absoluteValue + z.absoluteValue
    })
}
package Day12

import java.io.File
import kotlin.test.assertEquals

val src = "src/Day12/"

fun main(args : Array<String>) {
    assertEquals(6, solution1(File(src + "example01.txt")))
    println(solution1(File(src + "Day18/input.txt")))

    assertEquals(2, solution2(File(src + "example01.txt")))
    println(solution2(File(src + "Day18/input.txt")))
}



class Vertex private constructor(val id : Int, val connectedTo : IntArray) {
    companion object {  // making it singleton
        private var vertices = Array(0, { Vertex(0, IntArray(0)) })

        fun getOrCreate(id: Int, connectedTo: IntArray = IntArray(0)) : Vertex {
            if (vertices.getOrNull(id) == null)
                vertices += Vertex(id, connectedTo)

            return vertices[id]
        }

        fun amount() = vertices.size

        fun reset() {
            vertices = Array(0, { Vertex(0, IntArray(0)) })
        }
    }

    fun fire(firedVertices : BooleanArray) {
        firedVertices[id] = true

        connectedTo.filter { !firedVertices[it] }.forEach {
            vertices[it].fire(firedVertices)
        }
    }
}

fun solution1(file : File) : Int {
    Vertex.reset()
    val input = file.readLines()

    val regex = """(\d+) <-> (.*)""".toRegex()

    input.forEach {
        val match = regex.matchEntire(it)?.groupValues ?: List(0, { "" })

        val id = match[1].toInt()
        val connectedTo = match[2].split(", ").map { it.toInt() }.toIntArray()

        Vertex.getOrCreate(id, connectedTo)
    }

    val firedVertices = BooleanArray(Vertex.amount(), { false })
    Vertex.getOrCreate(0).fire(firedVertices)

    return firedVertices.count { it }
}


fun solution2(file : File) : Int {
    Vertex.reset()
    val input = file.readLines()

    val regex = """(\d+) <-> (.*)""".toRegex()

    input.forEach {
        val match = regex.matchEntire(it)?.groupValues ?: List(0, { "" })

        val id = match[1].toInt()
        val connectedTo = match[2].split(", ").map { it.toInt() }.toIntArray()

        Vertex.getOrCreate(id, connectedTo)
    }

    var groups = 0
    val firedVertices = BooleanArray(Vertex.amount(), { false })
    while (firedVertices.count { !it } != 0) {
        Vertex.getOrCreate(firedVertices.indexOfFirst { !it } ).fire(firedVertices)
        groups++
    }

    return groups
}

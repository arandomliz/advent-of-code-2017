package Day07

import java.io.File

val src = "src/Day07/"

fun main(args : Array<String>) {
    println(solution1(File(src + "example01.txt")))
    println(solution2(File(src + "example01.txt")))
    println(solution2(File(src + "Day18/input.txt")))
}

fun solution1(file : File) : String {
    val prgrms = HashMap<String, ArrayList<String>>()
    var elements = arrayOf<String>()
    val regex = """(\w+) \((\d+)\)(?: -> ((?:(?:\w+)(?:, )?)+))?""".toRegex()


    file.readLines().forEach {  // read file into HashMap
        val groups = (regex.find(it, 0)?.groupValues ?: listOf()).toTypedArray()
        /**
         * name = groups[1]
         * weight = groups[2]
         * children = groups[3]
         */

        for (e in groups[3].split(", ")) {
            if (prgrms.containsKey(e))
                prgrms[e]!!.add(groups[1])
            else
                prgrms.put(e, arrayListOf(groups[1]))
        }

        elements += groups[1]
    }

    return elements.single { !prgrms.containsKey(it) }
}

fun solution2(file : File) : String {
    val parents = HashMap<String, List<String>>()
    val weights = HashMap<String, Int>()
    val load = HashMap<String, List<Int>>()
    val regex = """(\w+) \((\d+)\)(?: -> ((?:(?:\w+)(?:, )?)+))?""".toRegex()

    val root = solution1(file)

    file.readLines().forEach {  // read file into HashMap
        val groups = (regex.find(it, 0)?.groupValues ?: listOf()).toTypedArray()
        /**
         * name = groups[1]
         * weight = groups[2]
         * children = groups[3]
         */

        var children : List<String> = groups[3].split(", ")

        parents.put(groups[1], children)
        weights.put(groups[1], groups[2].toInt())
    }

    println(getWeight(root, ElementInformation(
            parents, weights, load
    )))

    load.forEach { key, value ->
        val s = value.sum() / value.size

        if (value.any { s != it }) {
            println("Program $key is unbalanced, it's weights are [${value.joinToString(", ")}], " +
                    "this corresponds to [${(parents[key]!!.joinToString { weights[it].toString() })}]")

        }
    }

    return ""
}

data class ElementInformation(val parents : HashMap<String, List<String>>,
                              val weight : HashMap<String, Int>,
                              val load : HashMap<String, List<Int>>)

fun getWeight(from : String, info : ElementInformation) : Int {
    if (from.isBlank())
        return 0

    info.load.put(from, info.parents[from]?.map { getWeight(it, info) }
            ?: listOf(0))

    return info.weight[from]!! + info.load.get(from)!!.sum()
}

// fun isChild()
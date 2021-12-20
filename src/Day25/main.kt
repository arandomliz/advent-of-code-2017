package Day25

import java.io.File
import kotlin.experimental.and
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

val src = "src/Day25/"

fun main(args : Array<String>) {
    assertEquals(3, solution1(getInstructionsExample()))
    println(solution1(getInstructionsInputNew()))
}

fun solution1(insts : Instructions) : Int {
    val ram = InfiniteBoolArray()
    var state = insts.start

    repeat(insts.checksum) {
        val inst = if (ram.get())
            insts.blueprint[state]!!.second
        else
            insts.blueprint[state]!!.first

        ram.set(inst.first)
        ram.position += inst.second
        state = inst.third
    }

    return ram.countTrue()
}

/*
fun m6277a(bArr:ByteArray):String {
    val cArr = CharArray((bArr.size * 2))
    for (i in bArr.indices)
    {
        bArr[i] and 255.toByte()
        cArr[i * 2] = f4041a[i2.ushr(4)]
        cArr[(i * 2) + 1] = f4041a[i2 and 15]
    }
    return String(cArr)
}
*/
package Day25

class InfiniteBoolArray {
    var data = BooleanArray(0)
    var position = 0

    operator fun get(i : Int = position) = i >= 0 && i < data.size && data[i]

    operator fun set(i : Int, v : Boolean) : Int {
        var i = i
        if (i < 0) {
            data = BooleanArray(-i) + data
            i = 0
        } else if (i >= data.size)
            data += BooleanArray(i - data.size + 1)

        data[i] = v
        return i
    }

    fun set(v : Boolean) {
        position = set(position, v)
    }

    fun goLeft(steps : Int = 1) {
        position -= steps
    }
    fun goRight(steps : Int = 1) {
        position += steps
    }

    fun countTrue() = data.count { it }

    override fun toString() : String {
        var string = "["
        data.forEachIndexed { index, b ->
            val v = if (b) '1' else '0'
            if (index == position)
                string += "[$v]"
            else
                string += " $v "
        }

        return string + "]"
    }
}
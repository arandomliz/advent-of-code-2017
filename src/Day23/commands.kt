package Day23

data class ComputerState(
        val registers : MutableMap<String, Long> = mutableMapOf(),
        var lastPlayed : Long = 0,
        var executionLine : Int = 0)


sealed class Cmd {
    abstract fun execute(state : ComputerState) : Long?
    fun getNumber(state: ComputerState, n : String) =
            state.registers.getOrElse(n, { n.toLongOrNull() ?: 0L })
}

data class SET(private val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, y)
        return null
    }
}

data class SUB(private val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, register) - getNumber(state, y)
        return null
    }
}

data class MUL(private val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        state.registers[register] = getNumber(state, y) * getNumber(state, register)
        return null
    }
}

data class JNZ(private val register: String, val y : String) : Cmd() {
    override fun execute(state: ComputerState) : Long? {
        if (getNumber(state, register) != 0L)
            state.executionLine += (getNumber(state, y) - 1).toInt()
        return null
    }
}

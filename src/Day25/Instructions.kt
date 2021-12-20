package Day25

data class Instructions(var start : Char, var checksum : Int,
                        var blueprint : Map<Char, Pair<Triple<Boolean, Int, Char>, Triple<Boolean, Int, Char>>>)

fun getInstructionsExample() = Instructions('A', 6,
        mapOf(
                Pair('A', Pair(
                        Triple(true, 1, 'B'),
                        Triple(false, -1, 'B')
                )),
                Pair('B', Pair(
                        Triple(true, -1, 'A'),
                        Triple(true, 1, 'A')
                ))
        ))

fun getInstructionsInput() = Instructions('A', 12134527,
        mapOf(
                Pair('A', Pair(
                        Triple(true, 1, 'B'),
                        Triple(false, -1, 'C')
                )),
                Pair('B', Pair(
                        Triple(true, -1, 'A'),
                        Triple(true, 1, 'C')
                )),
                Pair('C', Pair(
                        Triple(true, 1, 'A'),
                        Triple(false, -1, 'D')
                )),
                Pair('D', Pair(
                        Triple(true, -1, 'E'),
                        Triple(true, -1, 'C')
                )),
                Pair('E', Pair(
                        Triple(true, 1, 'F'),
                        Triple(true, 1, 'A')
                )),
                Pair('F', Pair(
                        Triple(true, 1, 'A'),
                        Triple(true, 1, 'E')
                ))
        ))

fun getInstructionsInputNew() = Instructions('A', 12523873,
        mapOf(
                Pair('A', Pair(
                        Triple(true, 1, 'B'),
                        Triple(true, -1, 'E')
                )),
                Pair('B', Pair(
                        Triple(true, 1, 'C'),
                        Triple(true, 1, 'F')
                )),
                Pair('C', Pair(
                        Triple(true, -1, 'D'),
                        Triple(false, 1, 'B')
                )),
                Pair('D', Pair(
                        Triple(true, 1, 'E'),
                        Triple(false, -1, 'C')
                )),
                Pair('E', Pair(
                        Triple(true, -1, 'A'),
                        Triple(false, 1, 'D')
                )),
                Pair('F', Pair(
                        Triple(true, 1, 'A'),
                        Triple(true, 1, 'C')
                ))
        ))

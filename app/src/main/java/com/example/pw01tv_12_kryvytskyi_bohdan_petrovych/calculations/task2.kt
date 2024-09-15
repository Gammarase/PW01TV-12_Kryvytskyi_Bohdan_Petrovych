package com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations

data class MazutComponents(
    val C: Double,
    val H: Double,
    val O: Double,
    val S: Double,
    val W: Double,
    val A: Double,
    val V: Double
){
    fun prettyString(): String {
        return """
            Вуглець (C): $C
            Водень (H): $H
            Кисень (O): $O
            Сірка (S): $S
            Волога (W): $W
            Зола (A): $A
            Ванадій (V): $V
        """.trimIndent()
    }
}

fun calculateMazutOnWorkingMass(mazut: MazutComponents): MazutComponents {
    val coefficient = (100 - mazut.W - mazut.A) / 100
    return MazutComponents(
        C = mazut.C * coefficient,
        H = mazut.H * coefficient,
        O = mazut.O * coefficient,
        S = mazut.S * coefficient,
        W = mazut.W,
        A = mazut.A,
        V = mazut.V * (100 - mazut.W) / 100
    )
}

fun calculateMazutHeatingValue(Qdaf: Double, W: Double): Double {
    return Qdaf * (100 - W) / 100
}

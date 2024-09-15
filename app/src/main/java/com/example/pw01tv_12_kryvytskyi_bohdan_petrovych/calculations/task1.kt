package com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations

data class FuelComponents(
    val H: Double,  // Водень
    val C: Double,  // Вуглець
    val S: Double,  // Сірка
    val N: Double,  // Азот
    val O: Double,  // Кисень
    val W: Double,  // Волога
    val A: Double   // Зола
){
    fun toReadableFormat(): String {
        return """
            Водень (H): $H%
            Вуглець (C): $C%
            Сірка (S): $S%
            Азот (N): $N%
            Кисень (O): $O%
            Волога (W): $W%
            Зола (A): $A%
        """.trimIndent()
    }
}

fun calculateDryMass(fuel: FuelComponents): FuelComponents {
    val KRS = 100 / (100 - fuel.W)  // Коефіцієнт для переходу на суху масу
    return FuelComponents(
        H = fuel.H * KRS,
        C = fuel.C * KRS,
        S = fuel.S * KRS,
        N = fuel.N * KRS,
        O = fuel.O * KRS,
        W = 0.0,     // Волога відсутня у сухій масі
        A = fuel.A * KRS
    )
}

fun calculateCombustibleMass(fuel: FuelComponents): FuelComponents {
    val KRG = 100 / (100 - fuel.W - fuel.A)  // Коефіцієнт для переходу на горючу масу
    return FuelComponents(
        H = fuel.H * KRG,
        C = fuel.C * KRG,
        S = fuel.S * KRG,
        N = fuel.N * KRG,
        O = fuel.O * KRG,
        W = 0.0,     // Волога відсутня у горючій масі
        A = 0.0      // Зола відсутня у горючій масі
    )
}

fun calculateLowerHeatingValue(fuel: FuelComponents): Double {
    // Формула Мендєлєєва
    return (339 * fuel.C + 1030 * fuel.H - 108.8 * (fuel.O - fuel.S) - 25 * fuel.W)/1000
}

fun calculateDryHeatingValue(Qr: Double, W: Double): Double {
    // Перерахунок теплоти згоряння на суху масу
    return (Qr + 0.025 * W) * 100 / (100 - W)
}

fun calculateCombustibleHeatingValue(Qr: Double, W: Double, A: Double): Double {
    // Перерахунок теплоти згоряння на горючу масу
    return (Qr + 0.025 * W) * 100 / (100 - W - A)
}

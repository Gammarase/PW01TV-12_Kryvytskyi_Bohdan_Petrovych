package com.example.pw01tv_12_kryvytskyi_bohdan_petrovych

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.FuelComponents
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateCombustibleHeatingValue
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateCombustibleMass
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateDryHeatingValue
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateDryMass
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateLowerHeatingValue

class Task1Fragment : Fragment(R.layout.fragment_task1) {
    private lateinit var buttonTask1: Button
    private lateinit var valH: EditText
    private lateinit var valC: EditText
    private lateinit var valS: EditText
    private lateinit var valN: EditText
    private lateinit var valO: EditText
    private lateinit var valW: EditText
    private lateinit var valA: EditText
    private lateinit var resultText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTask1 = view.findViewById(R.id.button_calculate_task1)
        valH = view.findViewById(R.id.input_h)
        valC = view.findViewById(R.id.input_c)
        valS = view.findViewById(R.id.input_s)
        valN = view.findViewById(R.id.input_n)
        valO = view.findViewById(R.id.input_o)
        valW = view.findViewById(R.id.input_w)
        valA = view.findViewById(R.id.input_a)
        resultText = view.findViewById(R.id.output_result_task1)

        buttonTask1.setOnClickListener {
            val hValue = validateInput(valH, "H")
            val cValue = validateInput(valC, "C")
            val sValue = validateInput(valS, "S")
            val nValue = validateInput(valN, "N")
            val oValue = validateInput(valO, "O")
            val wValue = validateInput(valW, "W")
            val aValue = validateInput(valA, "A")

            if (hValue == null || cValue == null || sValue == null || nValue == null || oValue == null || wValue == null || aValue == null) {
                resultText.text = "Будь ласка, введіть дійсні, невід'ємні значення для всіх полів."
                return@setOnClickListener
            }

            if (hValue + cValue + sValue + nValue + oValue + wValue + aValue != 100.0)
            {
                resultText.text = "Сума всіх компонентів повинна дорівнювати 100."
                return@setOnClickListener
            }

            val comps = FuelComponents(
                H = hValue,
                C = cValue,
                S = sValue,
                N = nValue,
                O = oValue,
                W = wValue,
                A = aValue,
            )

            val dryMass = calculateDryMass(comps)
            val combustibleMass = calculateCombustibleMass(comps)
            val lowerHeatingValue = calculateLowerHeatingValue(comps)
            val dryMas = calculateDryHeatingValue(lowerHeatingValue, comps.W)
            val flameMas = calculateCombustibleHeatingValue(lowerHeatingValue, comps.W, comps.A)
            resultText.text = "Сухий склад: ${dryMass.toReadableFormat()}\nГорючий склад: ${combustibleMass.toReadableFormat()}\nНижча теплота згоряння:$lowerHeatingValue МДж/кг\nСуха маса $dryMas\nГорюча маса$flameMas"
        }
    }

    private fun validateInput(editText: EditText, fieldName: String): Double? {
        val input = editText.text.toString()
        if (input.isBlank()) {
            editText.error = "$fieldName не може бути порожнім"
            return null
        }

        return try {
            val value = input.toDouble()
            if (value < 0) {
                editText.error = "$fieldName не може бути від'ємним"
                null
            } else {
                value
            }
        } catch (e: NumberFormatException) {
            editText.error = "$fieldName має бути дійсним числом"
            null
        }
    }
}
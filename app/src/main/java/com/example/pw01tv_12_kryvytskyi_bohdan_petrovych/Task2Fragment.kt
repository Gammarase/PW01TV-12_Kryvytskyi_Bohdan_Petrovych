package com.example.pw01tv_12_kryvytskyi_bohdan_petrovych

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.MazutComponents
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateMazutHeatingValue
import com.example.pw01tv_12_kryvytskyi_bohdan_petrovych.calculations.calculateMazutOnWorkingMass

class Task2Fragment : Fragment(R.layout.fragment_task2) {
    private lateinit var buttonTask2: Button
    private lateinit var valH: EditText
    private lateinit var valC: EditText
    private lateinit var valS: EditText
    private lateinit var valO: EditText
    private lateinit var valV: EditText
    private lateinit var valW: EditText
    private lateinit var valA: EditText
    private lateinit var resultText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonTask2 = view.findViewById(R.id.button_calculate_task2)
        valH = view.findViewById(R.id.input_h_mazut)
        valC = view.findViewById(R.id.input_c_mazut)
        valS = view.findViewById(R.id.input_s_mazut)
        valO = view.findViewById(R.id.input_o_mazut)
        valV = view.findViewById(R.id.input_v_mazut)
        valW = view.findViewById(R.id.input_w_mazut)
        valA = view.findViewById(R.id.input_a_mazut)
        resultText = view.findViewById(R.id.output_result_task2)

        buttonTask2.setOnClickListener{
            val h = validateInput(valH, "H")
            val c = validateInput(valC, "C")
            val s = validateInput(valS, "S")
            val o = validateInput(valO, "O")
            val v = validateInput(valV, "V")
            val w = validateInput(valW, "W")
            val a = validateInput(valA, "A")

            if (h == null || c == null || s == null || o == null || v == null || w == null || a == null)
            {
                resultText.text = "Будь ласка, введіть дійсні, невід'ємні значення для всіх полів."
                return@setOnClickListener
            }

            val mazut = MazutComponents(
                H = h,
                C = c,
                S = s,
                O = o,
                V = v,
                W = w,
                A = a,
            )
            val workingMazut = calculateMazutOnWorkingMass(mazut)
            val heatingValue = calculateMazutHeatingValue(40.40, mazut.W)

            resultText.text = "Робоча маса мазуту:\n${workingMazut.prettyString()}\nНижча теплота згоряння: $heatingValue МДж/кг"
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
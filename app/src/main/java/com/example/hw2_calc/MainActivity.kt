package com.example.hw2_calc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.os.bundleOf
import com.example.hw2_calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var firstOperand = 0.0
    private var secondOperand= 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            buttonPlus.setOnClickListener{
                onOperatorButtonClick(PLUS)
            }
            buttonMinus.setOnClickListener{
                onOperatorButtonClick(MINUS)
            }
            buttonMultiplication.setOnClickListener{
                onOperatorButtonClick(MULTIPLICATION)
            }
            buttonDivision.setOnClickListener{
                onOperatorButtonClick(DIVISION)
            }
            buttonAbout.setOnClickListener{
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(RESULT_BUNDLE_KEY, binding.resultText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.resultText.text = savedInstanceState.getString(RESULT_BUNDLE_KEY) ?: "null"
    }

    private fun onOperatorButtonClick(clickedOperator: String) {
        with(binding){
            val firstOperandString = operandFirst.text.toString()
            val secondOperandString = operandSecond.text.toString()
            var resultString = ""

            if(firstOperandString.isNotEmpty() && secondOperandString.isNotEmpty()) {
                firstOperand =firstOperandString.toDouble()
                secondOperand = secondOperandString.toDouble()
                val result = when (clickedOperator) {
                    PLUS -> firstOperand + secondOperand
                    MINUS -> firstOperand - secondOperand
                    MULTIPLICATION -> firstOperand * secondOperand
                    DIVISION -> if (secondOperand != 0.0) firstOperand / secondOperand
                    else {
                        resultText.text = getString(R.string.division_by_zero)
                        return
                    }
                    else -> {
                        resultString = ""
                        return
                    }
                }
                resultText.text = getString(R.string.result_format, firstOperand.toString(), clickedOperator, secondOperand.toString(), result.toString())
            } else {
                resultText.text = getString(R.string.empty_warning)
            }
        }

    }

    companion object{
        const val RESULT_BUNDLE_KEY = "result"
        const val PLUS = "+"
        const val MINUS = "-"
        const val MULTIPLICATION = "*"
        const val DIVISION = "/"
    }
}
package com.example.hw2_calc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.os.bundleOf
import com.example.hw2_calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btnPlus.setOnClickListener{
                operatorBtnClick(getString(R.string.plus))
            }
            btnMinus.setOnClickListener{
                operatorBtnClick(getString(R.string.minus))
            }
            btnMulti.setOnClickListener{
                operatorBtnClick(getString(R.string.multiplication))
            }
            btnDiv.setOnClickListener{
                operatorBtnClick(getString(R.string.division))
            }
            btnAbout.setOnClickListener{
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

    private fun operatorBtnClick(clickedOperator: String) {
        operator = clickedOperator
        with(binding){
            val operand1Str = operandFirst.text.toString()
            val operand2Str = operandSecond.text.toString()

            if(operand1Str.isNotEmpty() && operand2Str.isNotEmpty()) {
                operand1 = operand1Str.toDouble()
                operand2 = operand2Str.toDouble()
                val result = when (operator) {
                    getString(R.string.plus) -> operand1 + operand2
                    getString(R.string.minus) -> operand1 - operand2
                    getString(R.string.multiplication) -> operand1 * operand2
                    getString(R.string.division) -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
                    else -> Double.NaN
                }
                val resultFormat = getString(R.string.result_format)
                val resultStr = String.format(resultFormat, operand1, operator, operand2, result)
                resultText.text = resultStr
            } else {
                resultText.text = ""
            }
        }

    }

    companion object{
        const val RESULT_BUNDLE_KEY = "result"
    }
}
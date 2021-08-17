package com.example.sumcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var input: String = "empty"
    private var number1: Int = 0
    private var number2: Int = 0
    private var operator:String = "empty"
    private var calculationResult: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun calculate() {
        val textView = findViewById<TextView>(R.id.result)

        //depending on the operator, calculate the result
        when(operator){
            "+" -> calculationResult = number1 + number2
            "-" -> calculationResult = number1 - number2
            "*" -> calculationResult = number1 * number2
        }
        //Set the default values to the operator and number2 and set the calculated sum to number1
        operator = "empty"
        number1 = calculationResult
        number2 = 0

        //Show the sum in the UI
        textView.text = calculationResult.toString()
    }

    fun numberInput(view: View) {
        //Get the user input
        val textView = findViewById<TextView>(R.id.result)
        input = (view as Button).text.toString()

        //If input is C, set default values to the variables
        if (input == "C"){
            number1 = 0
            number2 = 0
            operator = "empty"
            calculationResult = 0
            textView.text = "0"
            return //This is so that the rest of the function won't be executed
        }
        //If operator is inputted, save the operator to a variable and show it in the UI
        if(input == "+" || input == "-" || input == "*"){
            operator = input
            textView.append(operator)
        }
        //Check if the user wants to calculate
        else if (input == "="){
            calculate()
        }
        //Otherwise save inputted numbers to variables so that they can be calculated later
        else {
            if (number1 == 0) { //If no number has been inputted earlier, save the inputted number to number1 variable
                number1 = input.toInt()
                textView.text = number1.toString()
            } else if (operator == "empty") { //If the user hasn't inputted any operator yet, concat then newly inputted number to number1 variable
                val number1String = number1.toString()
                number1 = "$number1String$input".toInt()
                textView.append(input)
            } else if (input == "+" || input == "-" || input == "*" ) { //If the user has inputted operator earlier, save the number to number2 variable
                number2 = input.toInt()
                textView.append(number2.toString())
            } else if (input !== "=") { //If the user doesn't want to calculate the numbers yet, concat the newly inputted number to number2 variable
                val number2String = number2.toString()
                number2 = "$number2String$input".toInt()
                textView.append(input)
            }
        }

    }
}


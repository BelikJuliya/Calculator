package android.example.calculator.model

import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class CalculationModel {
    private val bracesQueue = LinkedList<String>()
    private val numbersWithoutBraces = LinkedList<String>()
    private val resultOfBraces = LinkedList<Pair<String, String>>()
    val result = MutableLiveData<Int>()

    fun calculate(expression: String) {
        //cutUnbracedNumbers(expression)
        //findBraces(expression)
        removeBraces(expression)
        calculateBraces(bracesQueue)
        calculateExpression(numbersWithoutBraces)
    }

    private fun findBraces(expression: String) {
        //bracesQueue.add(expression as String)
        val pattern = Pattern.compile("\\(.*\\)")
        val matcher = pattern.matcher(expression)
        cutUnbracedNumbers(expression)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val newExpression = expression.substring(start + 1, end - 1)
            bracesQueue.add(newExpression)
            findBraces(newExpression)
        }
    }

    private fun calculateFirstPriority(expression: String) {
//        val pattern = Pattern.compile("\\*")
//        val matcher = pattern.matcher(expression)
//        while (matcher.find()){
//            val start = matcher.start()
//            val end = matcher.end()
//            val subStr = expression.substring(start + 1, end - 1)
//            val tmp = subStr.split(" ").toMutableList()
//            val result = tmp[1].toInt() * tmp[2].toInt()
//
//        }

        val symbols = ArrayList<String>()
        val charArray = expression.toCharArray().toMutableList()
        for (char in charArray) {
            symbols.add(char.toString())
        }
        //val charArray = expression.split(" ").toMutableList()
        for (i in 1 until charArray.size - 1) {
            when {
                symbols[i] == "*" -> {
                    symbols[i] = (symbols[i - 1].toInt() * symbols[i + 1].toInt()).toString()
                    symbols.removeAt(i + 1)
                    symbols.removeAt(i - 1)
                }
                symbols[i] == "/" -> {
                    symbols[i] = (symbols[i - 1].toInt() / symbols[i + 1].toInt()).toString()
                    symbols.removeAt(i + 1)
                    symbols.removeAt(i - 1)
                }
            }
        }
        calculateSecondPriority(symbols)
    }

    private fun calculateSecondPriority(expression: MutableList<String>) {
        for (i in 1 until expression.size - 1) {
            when {
                expression[i] == "+" -> {
                    expression[i] =
                        (expression[i - 1].toInt() + expression[i + 1].toInt()).toString()
                    expression.removeAt(i + 1)
                    expression.removeAt(i - 1)
                }
                expression[i] == "-" -> {
                    expression[i] =
                        (expression[i - 1].toInt() - expression[i + 1].toInt()).toString()
                    expression.removeAt(i + 1)
                    expression.removeAt(i - 1)
                }
            }
        }
        
        numbersWithoutBraces.add(expression.joinToString())
    }

    private fun calculateBraces(expression: LinkedList<String>) {
        expression.forEach { _ -> calculateFirstPriority(expression.pollLast()) }
    }

    private fun calculateExpression(expression: LinkedList<String>) {

    }

    private fun cutUnbracedNumbers(expression: String) {
        if (expression.contains("(")) {
            val start = expression.indexOf("(")
            val end = expression.lastIndexOf(")")
            val beforeBracesString = expression.substring(0, start)
            val afterBracesString = expression.substring(end + 1)
            numbersWithoutBraces.add(beforeBracesString)
            numbersWithoutBraces.add(afterBracesString)
        }
    }

    private fun removeBraces(expression: String) {
        if (expression.contains("(") || expression.contains(")")){
            val start = expression.indexOf("(")
            val end = expression.lastIndexOf(")")
            val beforeBracesString = expression.substring(0, start)
            val afterBracesString = expression.substring(end + 1)
            numbersWithoutBraces.add(beforeBracesString)
            numbersWithoutBraces.add(afterBracesString)
            val inBracesExpression = expression.substring(start + 1, end)
            bracesQueue.add(inBracesExpression)
            removeBraces(inBracesExpression)
        }
    }
}


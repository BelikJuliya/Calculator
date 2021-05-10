package android.example.calculator.model

import androidx.lifecycle.MutableLiveData
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class CalculationModel {
    private val bracesQueue = LinkedList<String>()
    private val numbersWithoutBraces = LinkedList<String>()
    private val resultOfBraces = LinkedHashMap<String, String>()
    val result = MutableLiveData<Int>()
    val sb = StringBuilder()
    val inBraceExpressionBuilder = StringBuilder()
    val afterBracesQueue = LinkedList<String>()
    val beforeBracesQueue = LinkedList<String>()
    var beforeBracesString = ""
    var afterBracesString = ""
    var tmpResult = ""

    fun calculate(expression: String) {
        findBraces(expression)
//        calc(expression)
        //cutUnbracedNumbers(expression)
        //findBraces(expression)
//        removeBraces(expression)
//        calculateBraces(bracesQueue)
//        calculateExpression(numbersWithoutBraces)
    }

    private fun findBraces(expression: String) {
        if (expression.contains("(")) {
            val start = expression.indexOf("(")
            val end = expression.lastIndexOf(")")
            beforeBracesString = expression.substring(0, start)
            afterBracesString = expression.substring(end + 1)
        }
//        if (beforeBracesString != "") beforeBracesQueue.add(beforeBracesString)
//        if (afterBracesString != "") afterBracesQueue.add(afterBracesString)

        val pattern = Pattern.compile("\\(.*\\)")
        val matcher = pattern.matcher(expression)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val newExpression = expression.substring(start + 1, end - 1)
            //bracesQueue.add(newExpression)
//            if (newExpression.contains("(")) {
            findBraces(newExpression)
        }
        tmpResult = calculateFirstPriority(expression);
        val stringForCalculation = beforeBracesString + tmpResult + afterBracesString
        tmpResult = calculateFirstPriority(stringForCalculation)

//            }
        }

//        if (beforeBracesString != "") {
//            inBraceExpressionBuilder.append(beforeBracesString)
//        }
//        inBraceExpressionBuilder.append(tmpResult)
//        if (afterBracesString != "") {
//            inBraceExpressionBuilder.append(afterBracesString)
//        }
        //println(inBraceExpressionBuilder.toString())
        //val tmpResult = calculateFirstPriority(expression);

//    }

    private fun calculateFirstPriority(expression: String): String {
        val initialExpression = expression
        //val symbols = ArrayList<String>()
        //val charArray = expression.toCharArray().toMutableList()
        val symbols = expression.split("\\D".toRegex()).toMutableList()
//        for (char in charArray) {
//            symbols.add(char.toString())
//        }
        //val charArray = expression.split(" ").toMutableList()
        for (i in 1 until symbols.size - 1) {
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
        return calculateSecondPriority(symbols)
        //calculateSecondPriority(Pair(initialExpression, symbols))
    }

    private fun calculateSecondPriority(expression: MutableList<String>): String {
//    private fun calculateSecondPriority(pair: Pair<String, MutableList<String>>): Pair<String, String> {
//    private fun calculateSecondPriority(pair: Pair<String, MutableList<String>>): String {
        //val expression = pair.second
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
//        resultOfBraces.put(pair.first, expression.joinToString())
//        numbersWithoutBraces.add(expression.joinToString())
//        return Pair(pair.first, expression.joinToString())
        val result = expression.joinToString()
        return result
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
        if (expression.contains("(") || expression.contains(")")) {
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

    private fun calc(expression: String) {
        if (expression.contains("(") || expression.contains(")")) {
            val start = expression.indexOf("(")
            val end = expression.lastIndexOf(")")
            val beforeBracesString = expression.substring(0, start)
            val afterBracesString = expression.substring(end + 1)
            if (beforeBracesString != "") beforeBracesQueue.add(beforeBracesString)
            if (afterBracesString != "") afterBracesQueue.add(afterBracesString)
            val inBracesExpression = expression.substring(start + 1, end)
            calc(inBracesExpression)
            inBraceExpressionBuilder.append(beforeBracesQueue.pollLast())
                .append(calculateFirstPriority(inBracesExpression))
                .append(afterBracesQueue.pollLast())
            println(inBraceExpressionBuilder.toString())
            calc(inBraceExpressionBuilder.toString())
            //sb.append(calculateFirstPriority(inBracesExpression))
        }
    }
}


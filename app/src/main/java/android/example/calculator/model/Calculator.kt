package android.example.calculator.model

import java.util.*
import javax.inject.Inject


class Calculator @Inject constructor() {

    fun calculate(expression: String): String {
        return rpnToAnswer(expressionToRPN(expression)).toString()
    }

    private fun expressionToRPN(expression: String): String {
        var current = ""
        val stack = Stack<Char>()
        var priority: Int

        for (i in expression.indices) {
            priority = getPriority(expression[i])
            if (priority == 0) current += expression[i]
            if (priority == 1) stack.push(expression[i])
            if (priority > 1) {
                current += ' '
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) current += stack.pop()
                    else break
                }
                stack.push(expression[i])
            }
            if (priority == -1) {
                current += ' '
                while (getPriority(stack.peek()) != 1) {
                    current += stack.pop()
                }
                stack.pop()
            }
        }
        while (!stack.isEmpty()) {
            current += stack.pop()
        }
        return current
    }

    private fun rpnToAnswer(rpn: String): Double? {
        var operand = String()
        val stack = Stack<Double>()

        var i = 0
        while (i < rpn.length) {
            if ((rpn[i]) == ' ') {
                i++
                continue
            }
            if (getPriority(rpn[i]) == 0) {
                while (rpn[i] != ' ' && getPriority(rpn[i]) == 0) {
                    operand += rpn[i++]
                    if (i == rpn.length) break
                }
                stack.push(operand.toDouble())
                operand = String()
            }

            if (getPriority(rpn[i]) > 1) {
                val a = stack.pop()
                val b = stack.pop()

                when (rpn[i]) {
                    '+' -> stack.push(b + a)
                    '-' -> stack.push(b - a)
                    '*' -> stack.push(b * a)
                    '/' -> stack.push(b / a)
                }
            }
            i++
        }

        return stack.pop()
    }

    private fun getPriority(token: Char): Int {
        return if (token == '*' || token == '/') 3
        else if (token == '+' || token == '-') 2
        else if (token == '(') 1
        else if (token == ')') -1
        else 0
    }
}
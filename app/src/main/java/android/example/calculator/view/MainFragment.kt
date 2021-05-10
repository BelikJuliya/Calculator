package android.example.calculator.view

import android.example.calculator.R
import android.example.calculator.model.Calculator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import java.util.*

class MainFragment : Fragment() {
    private var sb = java.lang.StringBuilder()
    private val PASSWORD = "123"
    private val DELAY = 6000L
    val secretSb = java.lang.StringBuilder()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        var isSecretModeOn = false

        val inputText = view.findViewById<TextView>(R.id.input_text_view)
        val oneBtn = view.findViewById<Button>(R.id.one_btn)
        val twoBtn = view.findViewById<Button>(R.id.two_btn)
        val threeBtn = view.findViewById<Button>(R.id.three_btn)
        val fourBtn = view.findViewById<Button>(R.id.four_btn)
        val fiveBtn = view.findViewById<Button>(R.id.five_btn)
        val sixBtn = view.findViewById<Button>(R.id.six_btn)
        val sevenBtn = view.findViewById<Button>(R.id.seven_btn)
        val eightBtn = view.findViewById<Button>(R.id.eight_btn)
        val nineBtn = view.findViewById<Button>(R.id.nine_btn)
        val plusBtn = view.findViewById<Button>(R.id.plus_btn)
        val minusBtn = view.findViewById<Button>(R.id.minus_btn)
        val multiplyBtn = view.findViewById<Button>(R.id.multiply_btn)
        val davideBtn = view.findViewById<Button>(R.id.devide_btn)
        val openBracketBtn = view.findViewById<Button>(R.id.open_bracket_btn)
        val closeBracketBtn = view.findViewById<Button>(R.id.close_bracket_btn)
        val resultBtn = view.findViewById<Button>(R.id.equals_btn)

        val buttons = arrayListOf<Button>()
        buttons.add(oneBtn)
        buttons.apply {
            add(oneBtn)
            add(twoBtn)
            add(threeBtn)
            add(fourBtn)
            add(fiveBtn)
            add(sixBtn)
            add(sevenBtn)
            add(eightBtn)
            add(nineBtn)
            add(plusBtn)
            add(minusBtn)
            add(multiplyBtn)
            add(davideBtn)
            add(openBracketBtn)
            add(closeBracketBtn)
        }

        sb = StringBuilder()
        buttons.forEach { button ->
            button.setOnClickListener {
                if (!isSecretModeOn) inputText.text = sb?.append(button.text)
                else {
                    secretSb.append(button.text)
                }
            }
        }

        resultBtn.setOnClickListener {
            inputText.text = Calculator().calculate(inputText.text.toString()).toString()
        }

        val navController = NavHostFragment.findNavController(this)
        resultBtn.setOnLongClickListener {
            isSecretModeOn = true
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (secretSb.toString() == PASSWORD) {
                        navController.navigate(R.id.secretFragment)
                    } else {
                        isSecretModeOn = false
                    }
                }
            }, DELAY)
            return@setOnLongClickListener true
        }

        return view
    }
}
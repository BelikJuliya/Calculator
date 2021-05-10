package android.example.calculator.view

import android.example.calculator.DaggerCalculatorComponent
import android.example.calculator.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

const val DELAY = 5000L
const val PASSWORD = "123"

class MainFragment : Fragment() {
    private lateinit var inputText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

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

        val sb = StringBuilder()
        val secretSb = StringBuilder()
        var isSecretModeOn = false
        inputText = view.findViewById(R.id.input_text_view)
        buttons.forEach { button ->
            button.setOnClickListener {
                if (!isSecretModeOn) inputText.text = sb?.append(button.text)
                else {
                    secretSb.append(button.text)
                }
            }
        }

        resultBtn.setOnClickListener {
            CoroutineScope(Default).launch { setCalculationResult() }
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

    private suspend fun setCalculationResult() {
        val calculator = DaggerCalculatorComponent.create().getCalculator()
        withContext(Main) {
            inputText.text = calculator.calculate(inputText.text as String)
        }
    }
}
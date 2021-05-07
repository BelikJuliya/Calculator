package android.example.calculator.view

import android.example.calculator.R
import android.example.calculator.model.CalculationModel
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var sb: StringBuilder? = null
    private val DELAY_IN_MILLISECONDS = 5000L
    private val currentOperation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        //navController.navigate(R.id.mainFragment)
        var result: String? = null
        val inputText = findViewById<TextView>(R.id.input_text_view)
        val oneBtn = findViewById<Button>(R.id.one_btn)
        val twoBtn = findViewById<Button>(R.id.two_btn)
        val threeBtn = findViewById<Button>(R.id.three_btn)
        val fourBtn = findViewById<Button>(R.id.four_btn)
        val fiveBtn = findViewById<Button>(R.id.five_btn)
        val sixBtn = findViewById<Button>(R.id.six_btn)
        val sevenBtn = findViewById<Button>(R.id.seven_btn)
        val eightBtn = findViewById<Button>(R.id.eight_btn)
        val nineBtn = findViewById<Button>(R.id.nine_btn)

        val plusBtn = findViewById<Button>(R.id.plus_btn)
        val minusBtn = findViewById<Button>(R.id.minus_btn)
        val multiplyBtn = findViewById<Button>(R.id.multiply_btn)
        val davideBtn = findViewById<Button>(R.id.devide_btn)
        val openBracketBtn = findViewById<Button>(R.id.open_bracket_btn)
        val closeBracketBtn = findViewById<Button>(R.id.close_bracket_btn)



        val resultBtn = findViewById<Button>(R.id.equals_btn)

        sb = StringBuilder()

        val availableOperations = hashMapOf<Char, Int>()
        availableOperations.apply {
            put('+', 2)
            put('-', 2)
            put('*', 1)
            put('/', 1)
        }

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
        }

        buttons.forEach { button ->
            button.setOnClickListener {
                inputText.text = sb?.append(button.text)?.append(" ")
            }
        }

        plusBtn.setOnClickListener { view ->
            inputText.text = sb?.append(plusBtn.text)?.append(" ")
        }

        minusBtn.setOnClickListener { view ->
            inputText.text = sb?.append(minusBtn.text)?.append(" ")
        }

        multiplyBtn.setOnClickListener { view ->
            inputText.text = sb?.append(multiplyBtn.text)?.append(" ")
        }

        devide_btn.setOnClickListener { view ->
            inputText.text = sb?.append(devide_btn.text)?.append(" ")
        }

        openBracketBtn.setOnClickListener { view ->
            inputText.text = sb?.append(openBracketBtn.text)
        }

        closeBracketBtn.setOnClickListener { view ->
            inputText.text = sb?.append(closeBracketBtn.text)
        }


//        resultBtn.setOnClickListener {
//            val timer = Timer()
//            timer.schedule(object : TimerTask() {
//                override fun run() {
//
//                }
//            }, DELAY_IN_MILLISECONDS)
//            //sb?.clear()
//        }

//        resultBtn.setOnClickListener { view ->
//            CalculationModel().calculate(inputText.text)
//            // do something
//        }
        CalculationModel().calculate("100/((6+3*4)/3)-6")
    }
}

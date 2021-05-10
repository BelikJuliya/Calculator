package android.example.calculator

import android.example.calculator.model.Calculator
import android.example.calculator.view.MainFragment
import dagger.Component

@Component
interface CalculatorComponent {
    fun inject( mainFragment: MainFragment)
    fun getCalculator() : Calculator
}
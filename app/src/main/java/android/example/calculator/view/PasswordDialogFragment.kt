package android.example.calculator.view

import android.example.calculator.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment

class PasswordDialogFragment : DialogFragment() {
    private val PASSWORD = "1234"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)

        val inputPassword = view.findViewById<EditText>(R.id.edit_password)

//        if (inputPassword.text.toString() == PASSWORD) {
//            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_passwordDialogFragment)
//        } else {
//            NavHostFragment.findNavController(this).navigate(R.id.home)
//        }
        return view
    }
}
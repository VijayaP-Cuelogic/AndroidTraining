package com.example.oopsconceptskotlin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.activity_java.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
           // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            Intent(this, JavaActivity::class.java)
        }
    }

    private fun Intent(secondFragment: SecondFragment, java: Class<JavaActivity>) {
        val Intent = Intent(this.activity, JavaActivity::class.java)
        // To pass any data to next activity
        Intent.putExtra("keyIdentifier", "Anjali")
       // start your next activity
        startActivity(Intent)

    }
}


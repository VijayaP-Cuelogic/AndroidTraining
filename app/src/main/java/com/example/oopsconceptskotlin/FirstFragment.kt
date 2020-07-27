package com.example.oopsconceptskotlin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
//import sun.jvm.hotspot.utilities.IntArray


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    var loginButton = button
    var b2 = button2
    var userName = editText
    var password = editText2
    var txt1 = textView
//    var text2 = textView2
//    var text3 = textView3

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            Intent(secondFragment=this, java=ActivitySecond::class.java)
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            val context = getContext()
            Toast.makeText(context,"Show Toast",Toast.LENGTH_SHORT).show()

        }
    }

    private fun Intent(secondFragment: FirstFragment, java: Class<ActivitySecond>) {
        val Intent = Intent(this.activity, ActivitySecond::class.java)
        // To pass any data to next activity
       // Intent.putExtra("keyIdentifier", "Anjali")
        // start your next activity
            val context = getContext()
            Toast.makeText(context,"Redirecting",Toast.LENGTH_SHORT).show()
            startActivity(Intent)

    }
}

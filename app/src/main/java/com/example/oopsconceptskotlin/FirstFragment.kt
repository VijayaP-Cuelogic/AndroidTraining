package com.example.oopsconceptskotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
//import sun.jvm.hotspot.utilities.IntArray


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    var b1 = button
    var b2 = button2
    var ed1 = editText
    var ed2 = editText2
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
//        val button = button2
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            Intent(secondFragment=this, java=ActivitySecond::class.java)
        }

    }

    private fun Intent(secondFragment: FirstFragment, java: Class<ActivitySecond>) {
        val Intent = Intent(this.activity, ActivitySecond::class.java)
        // To pass any data to next activity
       // Intent.putExtra("keyIdentifier", "Anjali")
        // start your next activity
        startActivity(Intent)

    }
}

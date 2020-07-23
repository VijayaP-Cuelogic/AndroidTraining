package com.example.oopsconceptskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myArray = arrayOf<String>("true","false")
        main(myArray)
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
    // outer class declaration
    class outerClass {
        var str = "Outer class"
        // innerClass declaration with using inner keyword
        inner class innerClass {
            var s1 = "Inner class"
            fun nestfunc(): String {
                // can access the outer class property str
                var s2 = str
                return s2
            }
        }
    }
    // main function
    fun main(args: Array<String>) {
        // creating object for inner class
        val inner= outerClass().innerClass()
        // inner function call using object
        println(inner.nestfunc()+" property accessed successfully from inner class ")
    }
}

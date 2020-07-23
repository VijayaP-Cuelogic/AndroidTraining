package com.example.oopsconceptskotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import java.math.BigInteger

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
        main()
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
    // Interface

    interface Vehicle {
        // uninitialized variables
        var name : String
        var medium : String

        // non-abstract method
        fun runsWhere() {
            println("The vehicle, $name, runs in $medium")
        }

        // abstract method
        fun howItRuns()
    }


    // Interface 2
    interface ArielVehicle {
        // uninitialized variables
        var maximum_altitude:Int // in feet
        var medium:String // in feet
    }

    // implementing interface

    class Aeroplane : Vehicle, ArielVehicle {
        // override abstract variables and functions of interface
        override var name:String = "Aeroplane"
        override var medium: String = "air"
        override var maximum_altitude: Int = 10000

        override fun howItRuns() {
            println("$name flies based on buoyancy force.")
        }
    }

    /**
     * Abstract Class
     */
    abstract class Vehicle1 {
        // regular variable
        var name : String = "Not Specified"
        // abstract variable
        abstract var medium : String
        // regular function
        fun runsWhere() {
            println("The vehicle, $name, runs on $medium")
        }
        // abstract function
        abstract fun howItRuns()
    }

    /**
     * inheriting abstract class
     */
    class Aeroplane1 : Vehicle1() {
        // override abstract variables and functions of the
        // abstract class that is inherited
        override var medium: String = "air"

        override fun howItRuns() {
            println("Aeroplane fly based on buoyancy force.")
        }

    }


    // functions in Kotlin
    fun avg(a: Double, b: Double): Double {
        return  (a + b)/2
    }
    //single expression function
    // fun avg(a: Double, b: Double) = (a + b)/2

    // Unit returning Functions (return void) type : unit is optional
    fun printAverage(a: Double, b: Double): Unit {
        println("Avg of ($a, $b) = ${(a + b)/2}")
    }
    //function with default arguments
    fun displayGreeting(message: String, name: String = "Guest") {
        println("Hello $name, $message")
    }
    //Function Named Arguments
    fun arithmeticSeriesSum(a: Int = 1, n: Int, d: Int = 1): Int {
        return n/2 * (2*a + (n-1)*d)
    }

    //Variable Number of Arguments (Varargs)

    fun sumOfNumbers(vararg numbers: Double): Double {
        var sum: Double = 0.0
        for(number in numbers) {
            sum += number
        }
        return sum
    }

    fun sumOfNumbers(vararg numbers: Double, initialSum: Double): Double {
        var sum = initialSum
        for(number in numbers) {
            sum += number
        }
        return sum
    }

    // recursive function
    fun factorial(n: Int): Long {
        return if (n == 1) n.toLong() else n*factorial(n-1)
    }
    //Factorial Using Tail Recursion
    tailrec fun factorialNew(n: Int, run: Int = 1): Long {
        return if (n == 1) run.toLong() else factorialNew(n-1, run*n)
    }

    //trail recusive func
    tailrec fun fibonacci(n: Int, a: BigInteger, b: BigInteger): BigInteger {
        return if (n == 0) a else fibonacci(n-1, b, a+b)
    }

    /* Higher order functions */
    //Passing a function to another function
    fun func(str: String, myfunc: (String) -> Unit) {
        print("Welcome to Kotlin tutorial at ")
        myfunc(str)
    }
    fun demo(str: String) {
        println(str)
    }

    //function returns another function
    fun function(num: Int): (Int) -> Int = {num2 -> num2 + num}

    //Remove First and Last Character of String using extension function
    fun String.removeFirstLastChar(): String =  this.substring(1, this.length - 1)

    // class extension
    // A sample class to demonstrate extension functions
    class Circle (val radius: Double){
        // member function of class
        fun area(): Double{
            return Math.PI * radius * radius;
        }
    }

    fun main()
    {
        // calling  extension function
        val myString= "Hello Everyone"
        val resultString = myString.removeFirstLastChar()
        println("First character is: $resultString")

        // Extension function created for a class Circle
        fun Circle.perimeter(): Double{
            return 2*Math.PI*radius;
        }
        // create object for class Circle
        val newCircle = Circle(2.5);
        // invoke member function
        println("Area of the circle is ${newCircle.area()}")
        //invoke extension function
        println("Perimeter of the circle is ${newCircle.perimeter()}")


        //class object
        print("main executed")
        var vehicle1 = Aeroplane1()
        vehicle1.howItRuns()
        // object creation for class
        var obj1 = Aeroplane() // create obj1 object of SetValues class
        obj1.howItRuns()

        println("Summing all numbers")

        //function calling
        var average = avg(4.6, 9.0)
        print(average)
        printAverage(5.6,9.3)

        displayGreeting("Welcome to function", "John") //override name value
        displayGreeting("Welcome to function")//it will get default value for name

        //calling func named argument
        arithmeticSeriesSum(n=10)  // Result = 55
        arithmeticSeriesSum(a=3, n=10, d=2)  // Result = 120
        arithmeticSeriesSum(n=10, d=2, a=3)  // Result = 120
        arithmeticSeriesSum(3, n=10)
        // arithmeticSeriesSum(n=10, 2) // error: mixing named and positioned arguments is not allowed

        //variable number of arguments
        sumOfNumbers(1.5, 2.0)  // Result = 3.5
        sumOfNumbers(1.5, 2.0, 3.5, 4.0, 5.8, 6.2)  // Result = 23.0

        sumOfNumbers(1.5, 2.5, initialSum=100.0) // Result = 104.0

        //recursive function call
        val number1 = 4
        val result: Long

        result = factorial(number1)
        println("Factorial of $number1 = $result")

        //trail recusive call
        val n = 100
        val first = BigInteger("0")
        val second = BigInteger("1")

        println(fibonacci(n, first, second))

        val number = 5
        println("Factorial of $number = ${factorialNew(number)}")

        //lambda function
        val sum = {num1: Int, num2: Int -> num1 + num2}
        println("10+5: ${sum(10,5)}")

        //passing func to other func
        func("BeginnersBook", ::demo)

        //function returns another function
        val sumOfTwo = function(10)
        println("10 + 20: ${sumOfTwo(20)}")
    }
}

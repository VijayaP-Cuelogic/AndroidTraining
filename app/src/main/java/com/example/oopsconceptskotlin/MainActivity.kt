package com.example.oopsconceptskotlin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.oopsconceptskotlin.R.menu

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val myArray = arrayOf<String>("true","false")
        main(myArray)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    class SetValues {

        // member
        private var isSet: Boolean = false

        // member function
        fun setTrue() {
            isSet = true
        }

        // member function
        fun setFlase() {
            isSet = false
        }

        fun displayStatus(value: String) {
            if (isSet == true)
                println("$value set is true.")
            else
                println("$value set is false.")
        }
    }

    fun main(args: Array<String>) {

        // object creation for class
        val obj1 = SetValues() // create obj1 object of SetValues class
        val obj2 = SetValues() // create obj2 object of SetValues class

        obj1.setTrue()
        obj2.setFlase()

        obj1.displayStatus("obj1")
        obj2.displayStatus("obj2")

        // object for inheritance class
        val t1 = MathTeacher(25, "Jack")
        t1.teachMaths()
        println()
        val f1 = Footballer(29, "Christiano")
        f1.playFootball()

        //Overriding Member Function using inheritance
        val newPerson = NewPerson()
        newPerson.displayAge(31)
        newPerson.yearPassout = 2020
        println("year of passout change ${newPerson.yearPassout}.")

        //Polymorhism fun object
        val a : Number = 99
        val b = 1
        val c = 3.1

        //Using compile time polymorphism
        printNumber(a)
        printNumber(b)
        printNumber(c)

        //Using runtime polymorphism
        println("Summing all numbers")
        println(sum(listOf(a, b, c)))
    }
    }

    // inheritance code snippet
    //person base class
    open class Person(age: Int, name: String) {
        init {
            println("My name is $name.")
            println("My age is $age")
        }
    }
    //class MathTeacher inherited from Person class
    class MathTeacher(age: Int, name: String): Person(age, name) {

        fun teachMaths() {
            println("I teach in primary school.")
        }
    }
    //class Footballer inherited from Person class
    class Footballer(age: Int, name: String): Person(age, name) {
        fun playFootball() {
            println("I play for LA Galaxy.")
        }
    }

    //Overriding Member and Member Functions
    // Empty primary constructor
    open class PersonNew() {
        open var yearPassout: Int = 0
            get() = field

            set(value) {
                field = value
            }
        open fun displayAge(age: Int) {
            println("My age is $age.")
        }
    }

    class NewPerson: PersonNew() {
        override var yearPassout: Int = 0
            get() = field

            set(value) {
                field = value - 5
            }
        // calling function of base class
       // super.displayAge(age)

        override fun displayAge(age: Int) {
            println("My new age is ${age - 5}.")
        }
    }
    // compile time Polymorphism
    fun printNumber(n : Number){
        println("Using printNumber(n : Number)")
        println(n.toString() + "\n")
    }

    fun printNumber(n : Int){
         println("Using printNumber(n : Int)")
         println(n.toString() + "\n")
    }

    fun printNumber(n : Double){
         println("Using printNumber(n : Double)")
         println(n.toString() + "\n")
    }

    //run time polymorphism
    fun sum(numbers : List<Number>) : Number {
         return numbers.sumByDouble { it.toDouble() }
    }




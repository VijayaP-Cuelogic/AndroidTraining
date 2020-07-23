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
//    class SetValues {
//
//        // member
//        private var isSet: Boolean = false
//
//        // member function
//        fun setTrue() {
//            isSet = true
//        }
//
//        // member function
//        fun setFlase() {
//            isSet = false
//        }
//
//        fun displayStatus(value: String) {
//            if (isSet == true)
//                println("$value set is true.")
//            else
//                println("$value set is false.")
//        }
//    }

    fun main(args: Array<String>) {

        //Array in kotlin
        val array1 = arrayOf(1,2,3,4)
        val array2 = arrayOf<Long>(11,12,13,14)
        array1.set(0,5)
        array1[2] = 6
        array2.set(2,10)
        array2[3] = 8
        for(element in array1){
            println(element)
        }
        println()
        for(element in array2){
            println(element)
        }

        val arraySample1 = arrayOf(7,8,9,0)
        val arraySample2 = arrayOf<Long>(21,22,23,24)
        println(arraySample1.get(0))
        println(arraySample1[2])
        println()
        println(arraySample2.get(2))
        println(arraySample2[3])

        //String use
        val str = "Hello"
        println(str[0])
        println(str[1])
        println(str[str.length-1])

        //String templates
        val i =10
        print("i = $i")// print i=10  as string

        //Escaped String can contain escape characters like '\n', '\t', '\b' ,'\r','\$'etc. using ""
        val escStr = "Vijaya"

        //Raw String written """-"""
        val text = """Kotlin is the official language  
                      announce by Google for  
                      Android application development
                      """
        println(text)

        //Structural equality of string
        val string1 = "Hello, World!"
        val string2 = "Hello, World!"
        println(string1==string2) //true
        println(string1!=string2) //false

        //Different ways to define Array
        val arrayOfNumbers = arrayOf(2, 3, 5, 6, 10)
        println(arrayOfNumbers)
        val firstValue = arrayOfNumbers[0] // Resolves to 2
        println(firstValue)
        arrayOfNumbers[0] = 100 // the first element of the array is now `100`
        val newFirstValue  = arrayOfNumbers[0]
        println(newFirstValue)
        //iterate through the elements of the array
        arrayOfNumbers.forEach { number -> println(number) }
        //Or this
        for (number in arrayOfNumbers) {
            println(number)
        }

        val someOtherArray = Array(5) { "" } //define empty array of size 5
        println(someOtherArray)

        //Lists in Kotlin
        //Immutable Lists
        val listContant = listOf(2, 3, 5, 6, 7)
        println(listContant)

       // list[2] = 100 // immutable list cant be modified

        //Mutable Lists
        val list = mutableListOf(2, 3, 5, 6, 7)
        list[2] = 100 // works now
        println(list[2]) // 100
        list.add(index = 3, element = 500)
        println(list[3]) // 500
        list.remove(7)
        println(list) // [2, 3, 100, 500, 6]
        list.removeAt(0)
        println(list) // [3, 100, 500, 6]
        list.add(80)
        println(list)

        //Sets (add unique elements no duplicates)
        val workers = mutableSetOf(
            Worker(id = 5, name = "Filip"),
            Worker(id = 3, name = "Mike"),
            Worker(id = 5, name = "Filip"),
            Worker(id = 4, name = "Filip")
        )
        println(workers) // [Worker(id=5, name=Filip), Worker(id=3, name=Mike), Worker(id=4, name=Filip)]
        val removedWorker = Worker(id = 5, name = "Filip")
        workers.remove(removedWorker)
        println(workers) // [Worker(id=3, name=Mike), Worker(id=4, name=Filip)]

        //Creating a Map (similar to dictionar, key:value pair)
        val httpHeaders = mutableMapOf(
            "Authorization" to "your-api-key",
            "ContentType" to "application/json",
            "UserLocale" to "US")
             httpHeaders["Authorization"] = "something else"
             println(httpHeaders["Authorization"]) // something else
             httpHeaders.put("Hello", "World") //add new key :value pair
        //or
             httpHeaders["HelloWorld"] = "HelloWorld" ////add new key :value pair
             println(httpHeaders)


    }
    data class Worker(
        val id: Int,
        val name: String
    )
    }




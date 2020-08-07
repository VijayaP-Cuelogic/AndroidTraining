package com.example.viewmodeldemo

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainActivityViewModel: ViewModel() {
    private var tag: String = this.javaClass.simpleName
    var myRandomNumber: String? = null

    fun getNumber():String{
        print(tag + "get random number")
        if (myRandomNumber == null)
        {
            createNumber()
        }
    return myRandomNumber.toString()
    }
    fun createNumber(){
        print(tag + "create random number")
        myRandomNumber = (1..100).random().toString()
    }

}
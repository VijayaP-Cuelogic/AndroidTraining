package com.example.viewmodeldemo

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

//import androidx.lifecycle.get

class MainActivity : AppCompatActivity() {
    private var tag: String = this.javaClass.simpleName
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vieModelProvider = MainActivityViewModel()
        var model = ViewModelProviders.of(this).get(vieModelProvider.javaClass)

        var randomNumber = model.getNumber()
        textView.text = "Number : "+ randomNumber.toString()
        print(tag + "random number set")
    }
}
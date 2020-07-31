package com.example.networkconnectivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView

class MainActivity : AppCompatActivity() {
   // private
    var jsonURL = "https://jsonplaceholder.typicode.com/users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gv = findViewById<GridView>(R.id.myGridView)

        val downloadBtn = findViewById<Button>(R.id.downloadBtn)
        downloadBtn.setOnClickListener({ JSONDownloader(this@MainActivity, jsonURL, gv).execute() })
    }

}
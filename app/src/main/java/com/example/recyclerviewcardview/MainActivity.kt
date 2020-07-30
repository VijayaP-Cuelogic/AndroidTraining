package com.example.recyclerviewcardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    var listOfusers: ArrayList<Users> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.my_recycler_view) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<Users>()

        //adding some dummy data to the list
        users.add(Users("Belal Khan", "Ranchi Jharkhand"))
        users.add(Users("Ramiz Khan", "Ranchi Jharkhand"))
        users.add(Users("Faiz Khan", "Ranchi Jharkhand"))
        users.add(Users("Yashar Khan", "Ranchi Jharkhand"))

        //creating our adapter
       // val Adapter = Myadapter(users)
        val adapter = Myadapter.Myadapter(users)
        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }
}
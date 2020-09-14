package com.example.cuelogicinventoryassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_employee_device_list.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

internal lateinit var listView: ListView

class MainActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
//            Context.MODE_PRIVATE)
//        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        val sharedPreference =  getSharedPreferences("kotlinsharedpreference",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("user_type","employee")
        editor.commit()
        buttonEmployee.setOnClickListener {
            editor.clear()
            editor.putString("user_type","employee")
            editor.apply()
            editor.commit()
            startActivity(Intent(this, LoginActivity::class.java))
          //  finish()
        }

        buttonAdmin.setOnClickListener {
            editor.clear()
            editor.putString("user_type","admin")
            editor.apply()
            editor.commit()
            startActivity(Intent(this,LoginActivity::class.java))
            //finish()
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this,DashboardActivity::class.java))
          //  finish()
        }

    }

    override fun onStart() {
        super.onStart()

    }

}
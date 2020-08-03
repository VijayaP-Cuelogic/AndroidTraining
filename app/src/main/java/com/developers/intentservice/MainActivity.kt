package com.developers.intentservice

//import android.content.Context
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val intent = Intent(this, MyIntentService::class.java)
//        intent.putExtra("data", "Hello Intent service used for handling asynchronous task")
//        startService(intent)
//
//        // create ans save shared preferences
//        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//        var editor = sharedPreference.edit()
//        editor.putString("username","Vijaya")
//        editor.putLong("l",100L)
//        editor.commit()
//
//        //Retrieve shared preference
//        val name  = sharedPreference.getString("username","defaultName")
//        val valueL = sharedPreference.getLong("l",1L)
//
//        print("User name :"+ name)
//        print("Value :$valueL")
//
//    }
//}

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.developers.intentservice.PreferenceHelper.clearValues
import com.developers.intentservice.PreferenceHelper.customPreference
import com.developers.intentservice.PreferenceHelper.defaultPreference
import com.developers.intentservice.PreferenceHelper.password
import com.developers.intentservice.PreferenceHelper.userId


import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("data", "Hello Intent service used for handling asynchronous task")
        startService(intent)

        btnSave.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnShow.setOnClickListener(this)
        btnShowDefault.setOnClickListener(this)
        btnCreatefile.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val prefs = customPreference(this, CUSTOM_PREF_NAME)
        when (v?.id) {
            R.id.btnSave -> {
                prefs.password = inPassword.text.toString()
                prefs.userId = inUserId.text.toString().toInt()
            }
            R.id.btnClear -> {
                prefs.clearValues

            }
            R.id.btnShow -> {
                inUserId.setText(prefs.userId.toString())
                inPassword.setText(prefs.password)
            }
            R.id.btnShowDefault -> {

                val defaultPrefs = defaultPreference(this)
                inUserId.setText(defaultPrefs.userId.toString())
                inPassword.setText(defaultPrefs.password)
            }
            R.id.btnCreatefile ->{

                val fileName = "data.txt"

                var file = File(fileName)

                // create a new file
                val isNewFileCreated :Boolean = file.createNewFile()

                if(isNewFileCreated){
                    println("$fileName is created successfully.")
                } else{
                    println("$fileName already exists.")
                }

                // try creating a file that already exists
                val isFileCreated :Boolean = file.createNewFile()

                if(isFileCreated){
                    println("$fileName is created successfully.")
                } else{
                    println("$fileName already exists.")
                }

                //write to file
                file.writeText("This is some text for file writing operations")

                //read file
                var content:String = file.readText()
                println(content)
            }
        }
    }


}

object PreferenceHelper {

    val USER_ID = "USER_ID"
    val USER_PASSWORD = "PASSWORD"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.userId
        get() = getInt(USER_ID, 0)
        set(value) {
            editMe {
                it.putInt(USER_ID, value)
            }
        }

    var SharedPreferences.password
        get() = getString(USER_PASSWORD, "")
        set(value) {
            editMe {
                it.putString(USER_PASSWORD, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }

}
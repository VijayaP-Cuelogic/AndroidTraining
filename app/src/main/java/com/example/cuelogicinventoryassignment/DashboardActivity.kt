package com.example.cuelogicinventoryassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.setCustomView(R.layout.actionbar_layout)

        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("isComingFrom","MyDevice")
        editor.commit()
        btnMyDevice.setOnClickListener{
            editor.putString("isComingFrom","MyDevice")
            editor.apply()
            editor.commit()
            val intent = Intent(this, EmployeeDeviceListActivity::class.java)
            //intent.putExtra("isComingFrom", "MyDevice")
            startActivity(intent)
        }
        btnAllDevice.setOnClickListener{
            val intent = Intent(this, ActivityAllDeviceListView::class.java)
          //  intent.putExtra("isComingFrom", "AllDevice")
            editor.putString("isComingFrom","AllDevice")
            editor.apply()
            editor.commit()
            startActivity(intent)
        }
        btnRegisterComplain.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "email")
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Type your message here")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
//            startActivity(Intent(this, ActivitySendEmail::class.java))
//            finish()
//            startActivity(Intent(this, AddNewDeviceActivity::class.java))
//            finish()

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
              //  onBackPressed()
                return false
            }
        }
        return false
    }

}
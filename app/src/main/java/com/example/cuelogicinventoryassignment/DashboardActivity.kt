package com.example.cuelogicinventoryassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar!!.setCustomView(R.layout.actionbar_layout)
        btnMyDevice.setOnClickListener{
            startActivity(Intent(this,EmployeeDeviceListActivity::class.java))
           // finish()
        }
        btnAllDevice.setOnClickListener{
            startActivity(Intent(this,ActivityAllDeviceListView::class.java))
           // finish()
        }
        btnRegisterComplain.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "email")
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "message")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
//            startActivity(Intent(this, ActivitySendEmail::class.java))
//            finish()
        }
    }

}
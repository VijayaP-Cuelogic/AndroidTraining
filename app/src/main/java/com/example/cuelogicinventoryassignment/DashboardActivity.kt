package com.example.cuelogicinventoryassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnMyDevice.setOnClickListener{
            startActivity(Intent(this,EmployeeDeviceListActivity::class.java))
            finish()
        }
        btnAllDevice.setOnClickListener{
            startActivity(Intent(this,ActivityAllDeviceListView::class.java))
            finish()
        }
        btnRegisterComplain.setOnClickListener{
            startActivity(Intent(this, AddNewDeviceActivity::class.java))
            finish()
        }
    }
}
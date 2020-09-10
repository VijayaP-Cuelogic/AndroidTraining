package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_employee_device_list.*

class EmployeeDeviceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_device_list)

          val adapter = ViewPagerAdapter(supportFragmentManager)
          adapter.addFragment(AndroidDeviceListFragment(), "Android")
          adapter.addFragment(iOSDeviceListFragment(), "iOS")
          viewPager.adapter = adapter
          tabs.setupWithViewPager(viewPager)
    }
}
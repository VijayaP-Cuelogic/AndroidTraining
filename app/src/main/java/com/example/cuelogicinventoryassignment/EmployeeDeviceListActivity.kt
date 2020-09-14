package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_employee_device_list.*

class EmployeeDeviceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_device_list)

          getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

          val adapter = ViewPagerAdapter(supportFragmentManager)
          adapter.addFragment(AndroidDeviceListFragment(), "Android")
          adapter.addFragment(iOSDeviceListFragment(), "iOS")
          viewPager.adapter = adapter
          tabs.setupWithViewPager(viewPager)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
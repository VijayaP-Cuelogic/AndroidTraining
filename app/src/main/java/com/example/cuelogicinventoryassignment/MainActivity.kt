package com.example.cuelogicinventoryassignment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_employee_device_list.*
internal lateinit var listView: ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_devices_listview)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AndroidDeviceListFragment(), "Android")
        adapter.addFragment(iOSDeviceListFragment(), "iOS")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }

    override fun onStart() {
        super.onStart()
    }
}
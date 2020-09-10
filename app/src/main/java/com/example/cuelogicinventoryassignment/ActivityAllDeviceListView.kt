package com.example.cuelogicinventoryassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_employee_device_list.*

class ActivityAllDeviceListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_devices_listview)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AndroidDeviceListFragment(), "Android")
        adapter.addFragment(iOSDeviceListFragment(), "iOS")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

}
package com.example.cuelogicinventoryassignment

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_employee_device_list.*

class ActivityAllDeviceListView : AppCompatActivity() {

    private lateinit var user_type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_devices_listview)

        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        user_type = sharedPreference.getString("user_type","").toString()
        if (user_type.equals("employee")) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        }

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
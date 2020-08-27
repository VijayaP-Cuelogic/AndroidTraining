package com.example.cuelogicinventoryassignment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ios_device_list)


//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(AndroidDeviceListFragment(), "Android")
//        adapter.addFragment(iOSDeviceListFragment(), "iOS")
//       // adapter.addFragment(BestContentAppFragment(), "BestContentApp")
//        viewPager.adapter = adapter
//        tabs.setupWithViewPager(viewPager)


        var listView : ListView = findViewById<ListView>(R.id.iOSDeviceList)
        var list = mutableListOf<Model>()
        list.add(Model("iPhone X","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 8","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 7","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 6","description", R.drawable.ic_launcher_foreground))

        listView.adapter = MyAdapter(this, R.layout.row, list)

    }
}
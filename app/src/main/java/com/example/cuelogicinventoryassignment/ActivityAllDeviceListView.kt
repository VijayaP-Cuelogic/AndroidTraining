package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class ActivityAllDeviceListView : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_devices_listview)

        // Enables Always-on
        setAmbientEnabled()
    }
}
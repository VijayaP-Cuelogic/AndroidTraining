package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class ActivityDeviceDetails : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        // Enables Always-on
        setAmbientEnabled()
    }
}
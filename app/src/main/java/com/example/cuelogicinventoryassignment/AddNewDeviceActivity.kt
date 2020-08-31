package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class AddNewDeviceActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_device)

        // Enables Always-on
        setAmbientEnabled()
    }
}
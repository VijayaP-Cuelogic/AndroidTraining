package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class ActivityForgotPassword : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Enables Always-on
        setAmbientEnabled()
    }
}
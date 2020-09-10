package com.example.cuelogicinventoryassignment

import android.bluetooth.BluetoothClass
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_device.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class AddNewDeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_device)

        buttonAddDevice.setOnClickListener{

            if (deviceName.text.toString().isEmpty()){
                deviceName.error = "Please enter device name"
                deviceName.requestFocus()
                return@setOnClickListener
            }
            if (EditTextPlatform.text.toString().isEmpty()){
                EditTextPlatform.error = "Please enter platform"
                EditTextPlatform.requestFocus()
                return@setOnClickListener
            }
            if (OSInstalled.text.toString().isEmpty()){
                OSInstalled.error = "Please enter OS Installed"
                OSInstalled.requestFocus()
                return@setOnClickListener
            }
            if (editTextDate.text.toString().isEmpty()){
                editTextDate.error = "Please enter date"
                editTextDate.requestFocus()
                return@setOnClickListener
            }
            var device = Device(
                deviceName.text.toString(),
                EditTextPlatform.text.toString(),
                OSInstalled.text.toString(),
                editTextDate.text.toString()
            )
            var ref = FirebaseDatabase.getInstance().getReference("device/Android")

            var deviceId = ref.push().key
            ref.child(deviceId!!).setValue(device).addOnCompleteListener{

            }
        }

    }
}

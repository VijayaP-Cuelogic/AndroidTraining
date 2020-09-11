package com.example.cuelogicinventoryassignment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.bluetooth.BluetoothClass
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_device.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddNewDeviceActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_device)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        buttonSelectDate.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                editTextDate.setText("" + dayOfMonth + "/ " + monthOfYear + "/ " + year)

            }, year, month, day)

            dpd.show()
        }
        buttonAddDevice.setOnClickListener {

            if (deviceName.text.toString().isEmpty()) {
                deviceName.error = "Please enter device name"
                deviceName.requestFocus()
                return@setOnClickListener
            }
            if (EditTextPlatform.text.toString().isEmpty()) {
                EditTextPlatform.error = "Please enter platform"
                EditTextPlatform.requestFocus()
                return@setOnClickListener
            }
            if (OSInstalled.text.toString().isEmpty()) {
                OSInstalled.error = "Please enter OS Installed"
                OSInstalled.requestFocus()
                return@setOnClickListener
            }
            if (editTextDate.text.toString().isEmpty()) {
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
            ref.child(deviceId!!).setValue(device).addOnCompleteListener {

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }

        }
    }
}

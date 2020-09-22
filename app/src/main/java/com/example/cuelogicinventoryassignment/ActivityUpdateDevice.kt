package com.example.cuelogicinventoryassignment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_new_device.*
import kotlinx.android.synthetic.main.activity_check_in__check_out.*
import kotlinx.android.synthetic.main.activity_device_details.*
import kotlinx.android.synthetic.main.activity_device_details.EditTextPlatform
import kotlinx.android.synthetic.main.activity_device_details.OSInstalled
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignedDate
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignee
import kotlinx.android.synthetic.main.activity_device_details.deviceName
import kotlinx.android.synthetic.main.activity_device_details.editTextDate
import kotlinx.android.synthetic.main.activity_update_device.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ActivityUpdateDevice : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var date : String
    lateinit var userName : String
    lateinit var deviceNameVar : String
    lateinit var dateAdded : String
    lateinit var osInstalled : String
    lateinit var platformT : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_device)

        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Update Device")
        progressDialog.setMessage("Updating device info, please wait")

        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        date = simpleDateFormat.format(Date()).toString()
        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        userName = sharedPreference.getString("user_name","").toString()

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        var intent = intent
        val key = intent.getStringExtra("key").toString()
        val platform = intent.getStringExtra("platform").toString()
        if (key != null && platform != null) {
            val path = "device/"+platform+"/"+key
            ref = FirebaseDatabase.getInstance().getReference(path)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {

                        if (p0.exists()) {
                            //   for (d in p0.children){
                            deviceNameVar = p0.child("deviceName").getValue().toString()
                            platformT = p0.child("platform").getValue().toString()
                            osInstalled = p0.child("OS").getValue().toString()
                            dateAdded = p0.child("date").getValue().toString()
                            editTextDateAdded.setText(dateAdded)
                            EditTextOSInstalled.setText(osInstalled)
                            EditTextdeviceName.setText(deviceNameVar)
                            editTextPlatform.setText(platformT)
                        }
                }
            })

        }
        buttonUpdateDevice.setOnClickListener {

            if (EditTextdeviceName.text.toString().isEmpty()) {
                EditTextdeviceName.error = "Please enter device name"
                EditTextdeviceName.requestFocus()
                return@setOnClickListener
            }
            if (editTextPlatform.text.isEmpty()) {
                editTextPlatform.error = "Please enter platform"
                return@setOnClickListener
            }
            if (EditTextOSInstalled.text.toString().isEmpty()) {
                EditTextOSInstalled.error = "Please enter OS Installed"
                EditTextOSInstalled.requestFocus()
                return@setOnClickListener
            }
            if (editTextDateAdded.text.toString().isEmpty()) {
                editTextDateAdded.error = "Please enter date"
                editTextDateAdded.requestFocus()
                return@setOnClickListener
            }

            progressDialog.show()

            ref = FirebaseDatabase.getInstance().getReference("device/"+platform)
            var deviceDetails: HashMap<String, String> = hashMapOf("deviceName" to EditTextdeviceName.text.toString(),
                "platform" to editTextPlatform.text.toString(), "OS" to EditTextOSInstalled.text.toString(), "date" to editTextDateAdded.text.toString()
                 )

            ref.child(key).setValue(deviceDetails).addOnCompleteListener {
                progressDialog.dismiss()
                startActivity(Intent(this, ActivityAllDeviceListView::class.java))
                finish()
            }

        }
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
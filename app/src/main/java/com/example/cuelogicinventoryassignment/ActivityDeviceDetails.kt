package com.example.cuelogicinventoryassignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_check_in__check_out.*
import kotlinx.android.synthetic.main.activity_device_details.*
import kotlinx.android.synthetic.main.activity_device_details.EditTextPlatform
import kotlinx.android.synthetic.main.activity_device_details.OSInstalled
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignedDate
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignee
import kotlinx.android.synthetic.main.activity_device_details.deviceName
import kotlinx.android.synthetic.main.activity_device_details.editTextDate

class ActivityDeviceDetails : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var date : String
    lateinit var assigneeName : String
    lateinit var deviceNameVar : String
    lateinit var dateAdded : String
    lateinit var osInstalled : String
    lateinit var platformT : String
    lateinit var AssignedDate : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        var intent = intent
        val key = intent.getStringExtra("key").toString()
        val platform = intent.getStringExtra("platform").toString()

        if (key != null && platform != null) {
            val path = "device/"+platform +"/"+key
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
                        editTextDate.text = "Device Date : " + dateAdded
                        OSInstalled.text = "OS Installed : " + osInstalled
                        deviceName.setText("Device Name : " + deviceNameVar)
                        EditTextPlatform.text = "Platform : " + platformT
                        assigneeName = p0.child("AssignedTo").getValue().toString()
                        AssignedDate = p0.child("AssignedDate").getValue().toString()
                        if (!AssignedDate.equals("null") && !assigneeName.equals("null") && (AssignedDate.isNotEmpty() || assigneeName.isNotEmpty())) {
                            TextViewAssignedDate.text = "Assigned Date : " + AssignedDate
                            TextViewAssignee.text = "Assigned to : " + assigneeName
                        } else {
                            TextViewAssignedDate.text = "Assigned Date : NA"
                            TextViewAssignee.text = "Assigned to : NA"
                        }

                    }
                }
            })
            buttonRemove.setOnClickListener {
                ref = FirebaseDatabase.getInstance().getReference("device/"+platform)
                ref.child(key).removeValue().addOnCompleteListener {
                    startActivity(Intent(this, ActivityAllDeviceListView::class.java))
                    // finish()
                }

            }
            buttonUpdate.setOnClickListener{
                val intent = Intent(this, ActivityUpdateDevice::class.java)
                intent.putExtra("key",key)
                intent.putExtra("platform", platformT)
                startActivity(intent)
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

package com.example.cuelogicinventoryassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_check_in__check_out.*
import kotlinx.android.synthetic.main.activity_device_details.*
import kotlinx.android.synthetic.main.activity_device_details.EditTextPlatform
import kotlinx.android.synthetic.main.activity_device_details.OSInstalled
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignedDate
import kotlinx.android.synthetic.main.activity_device_details.TextViewAssignee
import kotlinx.android.synthetic.main.activity_device_details.deviceName
import kotlinx.android.synthetic.main.activity_device_details.editTextDate

class ActivityCheckIn_CheckOut : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in__check_out)

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
                        val name = "Device Name : " + p0.child("deviceName").getValue().toString()
                        editTextDate.text =
                            "Device Date : " + p0.child("date").getValue().toString()
                        OSInstalled.text = "OS Installed : " + p0.child("os").getValue().toString()
                        deviceName.setText(name)
                        EditTextPlatform.text =
                            "Platform : " + p0.child("platform").getValue().toString()

                        val AssignedTo = p0.child("AssignedTo").getValue().toString()
                        val AssignedDate = p0.child("AssignedDate").getValue().toString()
                        if(!AssignedDate.equals("null") && !AssignedTo.equals("null")){
                            TextViewAssignedDate.text = "Assigned Date : " + AssignedDate
                            TextViewAssignee.text = "Assigned to : " + AssignedTo
                            buttonCheckIn_CheckOut.setText("CheckOut")
                            buttonCheckIn_CheckOut.isEnabled = false
                        }else{
                            TextViewAssignedDate.text = "Assigned Date : NA"
                            TextViewAssignee.text = "Assigned to : NA"
                        }

                    }
                }
            })
            buttonCheckIn_CheckOut.setOnClickListener {
                ref = FirebaseDatabase.getInstance().getReference("device/"+platform)
                ref.child(key).removeValue()

            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

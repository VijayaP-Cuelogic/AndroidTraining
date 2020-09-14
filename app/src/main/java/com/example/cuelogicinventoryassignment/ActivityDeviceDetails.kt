package com.example.cuelogicinventoryassignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_device_details.*

class ActivityDeviceDetails : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        var intent = Intent()
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
                        val name = "Device Name :" + p0.child("deviceName").getValue().toString()
                        editTextDate.text =
                            "Device Date : " + p0.child("date").getValue().toString()
                        OSInstalled.text = "OS Installed : " + p0.child("os").getValue().toString()
                        deviceName.setText(name)
                        EditTextPlatform.text =
                            "Platform : " + p0.child("platform").getValue().toString()
                        TextViewAssignee.text = "Assigned to : NA"
                        TextViewAssignedDate.text = "Assigned Date : NA"

                    }
                }
            })
            buttonRemove.setOnClickListener {
                ref = FirebaseDatabase.getInstance().getReference("device/"+platform)
                ref.child(key).removeValue()

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

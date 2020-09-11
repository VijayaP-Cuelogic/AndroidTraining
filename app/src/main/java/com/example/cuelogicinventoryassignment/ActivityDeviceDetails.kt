package com.example.cuelogicinventoryassignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_device_details.*

class ActivityDeviceDetails : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        ref = FirebaseDatabase.getInstance().getReference("device/Android/-MGZOF8rKzMITUZDpf9w")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()){
                 //   for (d in p0.children){
                    val name = "Device Name :" + p0.child("deviceName").getValue().toString()
                        editTextDate.text = "Device Date : " + p0.child("date").getValue().toString()
                        OSInstalled.text = "OS Installed : " + p0.child("os").getValue().toString()
                        deviceName.setText(name)
                        EditTextPlatform.text = "Platform : " + p0.child("platform").getValue().toString()
                        TextViewAssignee.text = "Assigned to : NA"
                        TextViewAssignedDate.text = "Assigned Date : NA"

                }
            }
        })

        buttonRemove.setOnClickListener{
            ref = FirebaseDatabase.getInstance().getReference("device/Android")
            ref.child("-MGZOF8rKzMITUZDpf9w").removeValue()

        }
    }
}

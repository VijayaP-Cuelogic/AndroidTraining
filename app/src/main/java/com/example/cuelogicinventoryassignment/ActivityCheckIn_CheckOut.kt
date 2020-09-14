package com.example.cuelogicinventoryassignment

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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ActivityCheckIn_CheckOut : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var date : String
    lateinit var userName : String
    lateinit var assigneeName : String
    lateinit var deviceNameVar : String
    lateinit var dateAdded : String
    lateinit var osInstalled : String
    lateinit var platformT : String
    lateinit var AssignedDate : String
    var n = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in__check_out)

        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        date = simpleDateFormat.format(Date()).toString()
        val sharedPreference =  getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        userName = sharedPreference.getString("user_name","").toString()

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

                    if (n == 1){
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
                            if (!AssignedDate.equals("null") && !assigneeName.equals("null")) {
                                TextViewAssignedDate.text = "Assigned Date : " + AssignedDate
                                TextViewAssignee.text = "Assigned to : " + assigneeName
                                buttonCheckIn_CheckOut.setText("CheckOut")
                                if (assigneeName.equals(userName, true)) {
                                   // buttonCheckIn_CheckOut.isEnabled = true
                                    buttonCheckIn_CheckOut.visibility
                                } else {
                                    buttonCheckIn_CheckOut.isEnabled = false
                                }
                            } else {
                                TextViewAssignedDate.text = "Assigned Date : NA"
                                TextViewAssignee.text = "Assigned to : NA"
                                buttonCheckIn_CheckOut.isEnabled = true
                            }

                        }
                    n = 2
                }
                }
            })

        }
        buttonCheckIn_CheckOut.setOnClickListener {
            ref = FirebaseDatabase.getInstance().getReference("device/"+platform)
            var deviceDetails: HashMap<String, String> = hashMapOf("deviceName" to deviceNameVar,
                "platform" to platform, "OS" to osInstalled, "date" to dateAdded,
                "AssignedDate" to date, "AssignedTo" to userName)

            // var deviceId = ref.push().key
            if (buttonCheckIn_CheckOut.text.toString() == "CheckOut") {
                deviceDetails = hashMapOf("deviceName" to deviceNameVar,
                "platform" to platform, "OS" to osInstalled, "date" to dateAdded)
            }
            ref.child(key).setValue(deviceDetails).addOnCompleteListener {
                startActivity(Intent(this, DashboardActivity::class.java))
               // finish()
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

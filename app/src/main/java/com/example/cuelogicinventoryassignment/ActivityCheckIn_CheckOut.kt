package com.example.cuelogicinventoryassignment

import android.app.ProgressDialog
import android.graphics.Color
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
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
    lateinit var userID : String
    private lateinit var auth: FirebaseAuth
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

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Checkin Device")
        progressDialog.setMessage("Checking in device, please wait")

        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        date = simpleDateFormat.format(Date()).toString()
        val sharedPreference = getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        userName = sharedPreference.getString("user_name", "").toString()

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        var intent = intent
        val key = intent.getStringExtra("key").toString()
        val platform = intent.getStringExtra("platform").toString()
        if (key != null && platform != null) {
            auth = FirebaseAuth.getInstance()
            var currentUser = auth.currentUser
            userID = currentUser?.uid.toString()
            val path = "device/" + platform + "/" + key
            ref = FirebaseDatabase.getInstance().getReference(path)
            if(n == 1){
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
                        if (!AssignedDate.equals("null") || !assigneeName.equals("null")) {
                            TextViewAssignedDate.text = "Assigned Date : " + AssignedDate
                            TextViewAssignee.text = "Assigned to : " + assigneeName
//                            buttonCheckIn_CheckOut.setText("CheckOut")
                            if (assigneeName.equals(userName)) {
                                buttonCheckIn_CheckOut.visibility = View.VISIBLE
                                buttonCheckIn_CheckOut.setText("CheckOut")
                            } else if (assigneeName.isEmpty()){
                               // buttonCheckIn_CheckOut.visibility = View.GONE
                                TextViewAssignee.text = "Assigned to : NA"
                                buttonCheckIn_CheckOut.setText("CheckIn")
                                buttonCheckIn_CheckOut.visibility = View.VISIBLE
                            }else{
                               // buttonCheckIn_CheckOut.visibility = View.GONE
                                buttonCheckIn_CheckOut.isEnabled = false
                                buttonCheckIn_CheckOut.setBackgroundColor(Color.parseColor("#DCDCDC"))
                            }
                        } else {
                            TextViewAssignedDate.text = "Assigned Date : NA"
                            TextViewAssignee.text = "Assigned to : NA"
                            buttonCheckIn_CheckOut.setText("CheckIn")
                            buttonCheckIn_CheckOut.visibility = View.VISIBLE
                        }

                    }
                }
            })
                n = 2

        }
    }
        buttonCheckIn_CheckOut.setOnClickListener {
            val isConnected = isAvailableNetwork(this)
            if (isConnected){
            progressDialog.show()
            ref = FirebaseDatabase.getInstance().getReference("device/" + platform)
            var currentUser = auth.currentUser
            var deviceDetails: HashMap<String, String> = hashMapOf(
                "deviceName" to deviceNameVar,
                "platform" to platform, "OS" to osInstalled, "date" to dateAdded,
                "AssignedDate" to date, "AssignedTo" to userName
            )

            // var deviceId = ref.push().key
            if (buttonCheckIn_CheckOut.text.toString() == "CheckOut") {
                deviceDetails = hashMapOf(
                    "deviceName" to deviceNameVar,
                    "platform" to platform, "OS" to osInstalled, "date" to dateAdded
                )
            }
            ref.child(key).setValue(deviceDetails)
            val path = "users/employee/" + userID + "/device/" + platform
            ref = FirebaseDatabase.getInstance().getReference(path)
            if (buttonCheckIn_CheckOut.text.toString() == "CheckOut") {
                ref.child(key).removeValue().addOnCompleteListener {
                    progressDialog.dismiss()
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            } else {
                progressDialog.dismiss()
                ref.child(key).setValue(deviceNameVar).addOnCompleteListener {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }

        }else{
                showAlert("No internet connection, Please try again")
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
    fun isAvailableNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
    fun showAlert(message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setNegativeButton("Close", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.show()
    }
}

package com.example.cuelogicinventoryassignment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.bluetooth.BluetoothClass
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_device.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddNewDeviceActivity : AppCompatActivity() {

    lateinit var platformSelected : String
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_device)

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Add Device")
        progressDialog.setMessage("Adding device to inventory, please wait")

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        val platforms = resources.getStringArray(R.array.platform)

        platformSelected = "select platform"
        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, platforms)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
//                    Toast.makeText(this,
//                        getString(R.string.selected_item) + " " +
//                                "" + languages[position], Toast.LENGTH_SHORT).show()
                    Toast.makeText(
                        baseContext,
                        "selected platform",
                        Toast.LENGTH_SHORT
                    ).show()
                    platformSelected = platforms[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                    Toast.makeText(
                        baseContext,
                        "nothing selected",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
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

            val isConnected = isAvailableNetwork(this)
            if (isConnected) {
                if (deviceName.text.toString().isEmpty()) {
                    deviceName.error = "Please enter device name"
                    deviceName.requestFocus()
                    return@setOnClickListener
                }
                if (platformSelected.isEmpty() || platformSelected.equals(
                        "select platform",
                        true
                    )
                ) {
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

                progressDialog.show()
                var deviceDetails: HashMap<String, String> = hashMapOf(
                    "deviceName" to deviceName.text.toString(),
                    "platform" to platformSelected,
                    "OS" to OSInstalled.text.toString(),
                    "date" to editTextDate.text.toString(),
                    "AssignedDate" to "",
                    "AssignedTo" to ""
                )
                var ref = FirebaseDatabase.getInstance().getReference("device/" + platformSelected)

                var deviceId = ref.push().key.toString()
                ref.child(deviceId).setValue(deviceDetails).addOnCompleteListener {

                    progressDialog.dismiss()
                    startActivity(Intent(this, ActivityAllDeviceListView::class.java))
                    // finish()
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

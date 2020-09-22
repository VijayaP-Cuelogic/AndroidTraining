package com.example.cuelogicinventoryassignment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*
import kotlinx.android.synthetic.main.fragment_ios_device_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [iOSDeviceListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class iOSDeviceListFragment : Fragment() {

    private lateinit var user_type: String
    lateinit var deviceList: MutableList<Device>
    lateinit var emp_device : ArrayList<String>
    lateinit var ref : DatabaseReference
    lateinit var listView: ListView
    lateinit var layout:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var intent = Intent()
       // val MyDevice = intent.getStringExtra("isComingFrom").toString()

        val sharedPreference =  this.getActivity()!!.getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        user_type = sharedPreference.getString("user_type","").toString()

        val MyDevice = sharedPreference.getString("isComingFrom","").toString()

        if (MyDevice.equals("MyDevice",true)) {
            var auth = FirebaseAuth.getInstance()
            var currentUser = auth.currentUser
            val userID = currentUser?.uid.toString()
            val path = "users/employee/"+userID+"/device/iOS"
            emp_device = arrayListOf<String>()

            ref = FirebaseDatabase.getInstance().getReference(path)
            ref.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    emp_device = arrayListOf<String>()
                    if (p0.exists()) {

                        p0.children.forEachIndexed { index, element ->
                            if (p0.exists()) {
                                for (d in p0.children) {
                                    val device = d.key.toString()
                                    emp_device.add(device)
                                }
                            }
                        }
                    }
                }
            })
        }
        val progressDialog = ProgressDialog(this.activity)
        progressDialog.setTitle("Loading Device")
        progressDialog.setMessage("Loading Devices, please wait")
        progressDialog.show()

        val view = inflater.inflate(R.layout.fragment_ios_device_list, container, false);
        listView  = view.findViewById<ListView>(R.id.iOSDeviceList)


        var list = mutableListOf<Model>()

        deviceList = mutableListOf<Device>()
        ref = FirebaseDatabase.getInstance().getReference("device/iOS")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                deviceList = mutableListOf<Device>()
                if (p0.exists()){
                    for (d in p0.children){
                        val device = d.getValue(Device::class.java)!!
                        device.deviceKey = d.key.toString()
                        if (MyDevice.equals("MyDevice",true)) {
                            for (dv in emp_device) {
                                if (dv.equals(d.key.toString())) {
                                    deviceList.remove(device)
                                    deviceList.add(device)
                                }
                            }
                        }else{
                            deviceList.remove(device)
                            deviceList.add(device)
                        }
                    }
                }

                val adapter = MyAdapter(view.context, R.layout.row, deviceList)
                listView.adapter = adapter
                progressDialog.dismiss()
                listView.setOnItemClickListener { parent, view, position, id ->
                    val element = adapter.getItem(position)
                    if (user_type.equals("employee")) {
                        val intent = Intent(activity, ActivityCheckIn_CheckOut::class.java)
                        intent.putExtra("key", element!!.deviceKey.toString())
                        intent.putExtra("platform", "iOS")
                        startActivity(intent)
                    }else{
                        val intent = Intent(activity, ActivityDeviceDetails::class.java)
                        intent.putExtra("key", element!!.deviceKey.toString())
                        intent.putExtra("platform", "iOS")
                        startActivity(intent)
                    }
                }
            }
        })

        return view

    }

}
package com.example.cuelogicinventoryassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_android_device_list.*

class AndroidDeviceListFragment : Fragment() {
//    // TODO: Rename and change types of parameters
    private lateinit var user_type: String
    lateinit var deviceList: MutableList<Device>
    lateinit var emp_device : ArrayList<String>
    lateinit var ref : DatabaseReference
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      //  var intent = Intent()
      //  val MyDevice = intent.getStringExtra("isComingFrom").toString()

        val sharedPreference =  this.getActivity()!!.getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)
        user_type = sharedPreference.getString("user_type","").toString()

        val MyDevice = sharedPreference.getString("isComingFrom","").toString()

        if (MyDevice.isNotEmpty() && MyDevice.equals("MyDevice",true)) {
            var auth = FirebaseAuth.getInstance()
            var currentUser = auth.currentUser
            val userID = currentUser?.uid.toString()
            val path = "users/employee/"+userID+"/device/Android"
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_android_device_list, container, false);
        listView  = view.findViewById<ListView>(R.id.androidDeviceList)
//        llProgressBar.visibility = View.VISIBLE
        deviceList = mutableListOf<Device>()
        ref = FirebaseDatabase.getInstance().getReference("device/Android")
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
                        println(deviceList)
                        if (MyDevice.isNotEmpty() && MyDevice.equals("MyDevice",true)) {
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
                listView.setOnItemClickListener { parent, view, position, id ->
                    val element = adapter.getItem(position)
                    if (user_type.equals("employee")) {
                        val intent = Intent(activity, ActivityCheckIn_CheckOut::class.java)
                        intent.putExtra("key", element!!.deviceKey)
                        intent.putExtra("platform", "Android")
                        startActivity(intent)
                    }else{
                        val intent = Intent(activity, ActivityDeviceDetails::class.java)
                        intent.putExtra("key", element!!.deviceKey)
                        intent.putExtra("platform", "Android")
                        startActivity(intent)
                    }
                }
            }
        })
        return view
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment AndroidDeviceListFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            AndroidDeviceListFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
}
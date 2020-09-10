package com.example.cuelogicinventoryassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*

class AndroidDeviceListFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    lateinit var deviceList: MutableList<Device>
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
        deviceList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("device/Android")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (d in p0.children){
                        val device = d.getValue(Device::class.java)!!
                        deviceList.add(device)
                        println(deviceList)
                    }
                }
            }
        })
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_android_device_list, container, false);
        listView  = view.findViewById<ListView>(R.id.androidDeviceList)
//        var list = mutableListOf<Model>()
//        list.add(Model("Android AP","description", R.drawable.ic_launcher_foreground))
//        list.add(Model("Pixel","description", R.drawable.ic_launcher_foreground))
//        list.add(Model("Redmi 8","description", R.drawable.ic_launcher_foreground))

        listView.adapter = MyAdapter(view.context, R.layout.row, deviceList)
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
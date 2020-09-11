package com.example.cuelogicinventoryassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
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

        deviceList = mutableListOf<Device>()
        ref = FirebaseDatabase.getInstance().getReference("device/iOS")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (d in p0.children){
                        val device = d.getValue(Device::class.java)!!
                        deviceList.add(device)
                    }
                }
            }
        })
        val view = inflater.inflate(R.layout.fragment_ios_device_list, container, false);
        listView  = view.findViewById<ListView>(R.id.iOSDeviceList)
        var list = mutableListOf<Model>()

        list.add(Model("iPhone X","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 8","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 7","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 6","description", R.drawable.ic_launcher_foreground))
        list.add(Model("iPhone 6 +","description", R.drawable.ic_launcher_foreground))

        val adapter = MyAdapter(view.context, R.layout.row, list)
        listView.adapter = adapter
        return view
        listView.setOnClickListener{
            val intent = Intent(this.activity, ActivityDeviceDetails::class.java)
            startActivity(intent)
            //  finish()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment iOSDeviceListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            iOSDeviceListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
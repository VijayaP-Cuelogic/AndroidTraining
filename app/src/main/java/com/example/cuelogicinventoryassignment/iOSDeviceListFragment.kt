package com.example.cuelogicinventoryassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
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

        val progressBar = ProgressBar(this.context)
        //setting height and width of progressBar
        progressBar.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
            )


        val view = inflater.inflate(R.layout.fragment_ios_device_list, container, false);
        listView  = view.findViewById<ListView>(R.id.iOSDeviceList)

        layout = view.findViewById(R.id.layout)
        // Add ProgressBar to our layout
        layout?.addView(progressBar)
        var list = mutableListOf<Model>()

        //val visibility = if (progressBar.visibility == View.GONE){
            View.VISIBLE
       // }else
            View.GONE
       // progressBar.visibility = visibility
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
                        device.deviceKey = d.key.toString()
                        deviceList.add(device)
                    }
                }

                val adapter = MyAdapter(view.context, R.layout.row, deviceList)
                listView.adapter = adapter
                listView.setOnItemClickListener { parent, view, position, id ->
                    val element = adapter.getItem(position)
                    val intent = Intent (activity, ActivityCheckIn_CheckOut::class.java)
                    intent.putExtra("key", element!!.deviceKey.toString())
                    intent.putExtra("platform", "iOS")
                    startActivity(intent)
                }
            }
        })

       // View.GONE
        return view

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
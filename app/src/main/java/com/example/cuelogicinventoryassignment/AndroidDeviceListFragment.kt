package com.example.cuelogicinventoryassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class AndroidDeviceListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_android_device_list, container, false);
        var listView  = view.findViewById<ListView>(R.id.androidDeviceList)
        var list = mutableListOf<Model>()
        list.add(Model("Android AP","description", R.drawable.ic_launcher_foreground))
        list.add(Model("Pixel","description", R.drawable.ic_launcher_foreground))
        list.add(Model("Redmi 8","description", R.drawable.ic_launcher_foreground))

        listView.adapter = MyAdapter(view.context, R.layout.row, list)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AndroidDeviceListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AndroidDeviceListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
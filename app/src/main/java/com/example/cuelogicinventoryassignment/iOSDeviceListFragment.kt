package com.example.cuelogicinventoryassignment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_ios_device_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [iOSDeviceListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class iOSDeviceListFragment : Fragment() {

//    val context: Context
//        @JvmName("MainActivity")
//        get() = getContext()!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        // List View Object


      //  var listView : ListView = findViewById<ListView>(R.id.iOSDeviceList)
       // iOSDeviceList
//        var list = mutableListOf<Model>()
//        list.add(Model("iPhone X","description", R.drawable.ic_launcher_foreground))
//        list.add(Model("iPhone 8","description", R.drawable.ic_launcher_foreground))
//        list.add(Model("iPhone 7","description", R.drawable.ic_launcher_foreground))
//        list.add(Model("iPhone 6","description", R.drawable.ic_launcher_foreground))
//
//        iOSDeviceList?.adapter = MyAdapter(context, R.layout.row, list)
//      //  var ListView : ListView = clearFindViewByIdCache(R.id.li)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ios_device_list, container, false)
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
package com.example.cuelogicinventoryassignment

import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(var mCtx:Context, var resources:Int, var items:List<Model>):ArrayAdapter<Model>(mCtx,resources, items){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
            val  view:View = layoutInflater.inflate(resources, null)

            val imageView:ImageView = view.findViewById(R.id.imageV)
            val title:TextView = view.findViewById(R.id.textName)
            val description:TextView = view.findViewById(R.id.textDescriptin)

        var mItem: Model = items[position]
        imageView.setImageDrawable(mCtx.resources.getDrawable(R.drawable.ic_launcher_foreground))
        title.text = mItem.title
        description.text = mItem.description
        return view
    }
}

package com.example.recyclerviewcardview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Myadapter {
    class Myadapter(val userList: ArrayList<Users>) :
        RecyclerView.Adapter<Myadapter.ViewHolder>() {

        //this method is returning the view for each item in the list
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): Myadapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
            return ViewHolder(v)
        }

        //this method is binding the data on the list
        override fun onBindViewHolder(holder: Myadapter.ViewHolder, position: Int) {
            holder.bindItems(userList[position])
        }

        //this method is giving the size of the list
        override fun getItemCount(): Int {
            return userList.size
        }

        //the class is hodling the list view
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindItems(user: Users) {
                val textViewName = itemView.findViewById(R.id.textViewUsername) as TextView
                val textViewAddress = itemView.findViewById(R.id.textViewAddress) as TextView
                textViewName.text = user.name
                textViewAddress.text = user.address
            }
        }
    }
}
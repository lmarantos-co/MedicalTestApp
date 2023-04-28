package com.example.cvdriskestimator.customClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.R

class CustomTestListAdapter(private val nameList : ArrayList<String>  , private val dateList : ArrayList<String>) : RecyclerView.Adapter<CustomTestListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.patient_test_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view

    override fun onBindViewHolder(holder: CustomTestListAdapter.ViewHolder, position: Int) {
        // sets the text to the textview from our itemHolder class
        holder.nameTxtV.text = nameList.get(position)
        holder.dateTxtV.text = dateList.get(position)
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return dateList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val nameTxtV: TextView = ItemView.findViewById(R.id.patientTestNameTxtCV)
        val dateTxtV: TextView = itemView.findViewById(R.id.patientTestDateTxtCV)
    }

}


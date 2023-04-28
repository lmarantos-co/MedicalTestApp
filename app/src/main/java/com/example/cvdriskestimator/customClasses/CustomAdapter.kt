package com.example.cvdriskestimator.customClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.R

class CustomAdapter(private val dateList : ArrayList<String>  , private val scoreList : ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_patient_data_recycler_txtv, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.dateTxtV.text = dateList.get(position)
        holder.scoreTxtV.text = scoreList.get(position)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return dateList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dateTxtV: TextView = ItemView.findViewById(R.id.testDateTxtV)
        val scoreTxtV: TextView = itemView.findViewById(R.id.testScoreTxtV)
    }
}
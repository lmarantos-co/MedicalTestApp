package com.example.cvdriskestimator.CustomClasses

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R


class leaderBoardRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_ITEM = 0
    val VIEW_HEADER = 1

    private lateinit var prActivity: MainActivity

    var nameDataSet = ArrayList<String>()
    var participantAvatars = ArrayList<Drawable>()
    private var currentPartId = 1

    fun setActivity(mainActivity: MainActivity)
    {
        prActivity = mainActivity
    }

    override fun getItemViewType(position: Int): Int {
        var view_type = (position % 4)
        if (view_type == 0)
            return VIEW_HEADER
        else
            return  VIEW_ITEM
        return VIEW_HEADER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType)
        {
            1 ->
            {
                val view : View = LayoutInflater.from(prActivity.applicationContext).inflate(R.layout.leaderboard_header_item_layout, parent , false)
                return leaderHeaderViewHolder(view)
            }
            0 ->
            {
                val view : View =  LayoutInflater.from(prActivity.applicationContext).inflate(R.layout.leaderboard_item_layout , parent , false)
                return leaderBoardViewHolder(view)
            }
        }
        val view = View(prActivity.applicationContext)
        return leaderBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position))
        {
            0 ->
            {
                //populate data for the viewHolder
                var leaderBoardViewHolder = holder as leaderBoardViewHolder
                leaderBoardViewHolder.part_name.text = nameDataSet[currentPartId - 1]
                leaderBoardViewHolder.part_order_id.text = (currentPartId).toString()
                leaderBoardViewHolder.part_avatar.setImageDrawable(participantAvatars[currentPartId-1])
                currentPartId ++
                leaderBoardViewHolder.part_score.text = leaderBoardViewHolder.calculateScore(currentPartId).toString()
            }
            1 ->
            {
                var leaderHeaderViewHolder = holder as leaderHeaderViewHolder
                currentPartId ++
                leaderHeaderViewHolder.leader_header_txtV.text = leaderHeaderViewHolder.setGroupLetter(currentPartId-1)
            }
        }


    }

    override fun getItemCount(): Int {
        return nameDataSet.size
    }

}

open class leaderBoardViewHolder : RecyclerView.ViewHolder
{
    var part_order_id : TextView
    var part_name : TextView
    var part_avatar : ImageView
    var part_rel_layout : RelativeLayout
    var part_score : TextView

    constructor(itemView: View) : super(itemView) {
        part_rel_layout = itemView.findViewById(R.id.partRelLayout)
        part_order_id = itemView.findViewById(R.id.partorderTxtV)
        part_score = itemView.findViewById(R.id.partScoreTxtV)
        part_name = itemView.findViewById(R.id.parttxtV)
        part_avatar = itemView.findViewById(R.id.partAvatorImgV)
    }



    fun calculateScore(id : Int) : Int
    {
        val score = 10000  - (id * 500)
        return score
    }
}


class leaderHeaderViewHolder : RecyclerView.ViewHolder
{
    var leader_header_txtV : TextView

    constructor(itemView : View) : super(itemView) {
        leader_header_txtV = itemView.findViewById(R.id.groupleadTxtV)
    }

    fun setGroupLetter(position : Int) : String
    {
        var result = (position / 3)
        var letter = "A"
        when(result)
        {
            0 ->
            {
                letter = "GROUP A"
            }
            1 ->
            {
                letter = "GROUP B"
            }
            2 ->
            {
                letter = "GROUP C"
            }
            3 ->
            {
                letter = "GROUP D"
            }
        }
        return letter
    }
}

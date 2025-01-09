package com.example.cvdriskestimator.customClasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.Fragments.OPQOLCheckFragment
import com.example.cvdriskestimator.R

class RandomQuestonRecyclerViewAdapter(private var opqolCheckFragment: OPQOLCheckFragment , private val questions : ArrayList<Int> , private var questionsList : ArrayList<String> , private var testData : ArrayList<Int?> , private var recyclerView: RecyclerView) : RecyclerView.Adapter<RandomQuestionViewHolder>()
{
    private  var questionsNumbers : ArrayList<Int>
    private  var questionsTextList : ArrayList<String>
    private var opqolFragment : OPQOLCheckFragment
    private var data : ArrayList<Int?>
    private var recView : RecyclerView

    init {
        opqolFragment = opqolCheckFragment
        questionsNumbers = questions
        questionsTextList = questionsList
        data = testData
        recView = recyclerView
    }

    fun updateTestData(newTestData: ArrayList<Int?>) {
        data = newTestData
        notifyDataSetChanged()
    }

    fun requestFocus(position: Int) {
        // Get the ViewHolder at the specified position
// Delay before scrolling
        recView.postDelayed({
            // Scroll to position
            recView.scrollToPosition(position)
        }, 100) // Adjust the delay time as needed//        val viewHolder = recView.findViewHolderForAdapterPosition(position)
//        // Request focus for the RadioGroup in the ViewHolder
//        viewHolder?.itemView!!.findViewById<RadioGroup>(R.id.OPQOLDummyRadioGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomQuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.random_item_layout, parent, false)
        return RandomQuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RandomQuestionViewHolder, position: Int) {
        holder.textView.text = questionsTextList.get(questions.get(position))

        if (data.get(position) != null)
        {
            if ((questions.get(position) != 3) && (questions.get(position) != 5) &&
                (questions.get(position) != 6) && (questions.get(position) != 15) &&
                (questions.get(position) != 28) && (questions.get(position) != 32))
            {
                when (data.get(position))
                {
                    0 ->  (holder.radioGroup.getChildAt(0) as RadioButton).isChecked = true
                    1 ->  (holder.radioGroup.getChildAt(1) as RadioButton).isChecked = true
                    2 ->  (holder.radioGroup.getChildAt(2) as RadioButton).isChecked = true
                    3 ->  (holder.radioGroup.getChildAt(3) as RadioButton).isChecked = true
                    4 -> (holder.radioGroup.getChildAt(4) as RadioButton).isChecked = true
                }

            }
            else
            {
                when (data.get(position))
                {
                    0 ->  (holder.radioGroup.getChildAt(4) as RadioButton).isChecked = true
                    1 ->  (holder.radioGroup.getChildAt(3) as RadioButton).isChecked = true
                    2 ->  (holder.radioGroup.getChildAt(2) as RadioButton).isChecked = true
                    3 ->  (holder.radioGroup.getChildAt(1) as RadioButton).isChecked = true
                    4 -> (holder.radioGroup.getChildAt(0) as RadioButton).isChecked = true
                }
            }
        }


        holder.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if ((questions.get(position) != 3) && (questions.get(position) != 5) &&
                (questions.get(position) != 6) && (questions.get(position) != 15) &&
                (questions.get(position) != 28) && (questions.get(position) != 32))
            {
                val index = radioGroup.indexOfChild(radioGroup.findViewById(i))

                when (index)
                {
                    0 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 1)
                    1 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 2)
                    2 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 3)
                    3 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 4)
                    4 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 5)
                }

            }
            else
            {
                val index = radioGroup.indexOfChild(radioGroup.findViewById(i))

                when (index)
                {
                    0 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 5)
                    1 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 4)
                    2 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 3)
                    3 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 2)
                    4 ->  opqolCheckFragment.setPatientAnswerFromRecyclerAdapter(position , 1)
                }
            }
        }

    }

    override fun getItemCount() = questions.size
}

// ViewHolder class
class RandomQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.opqolDummyTxtView)
    val radioGroup: RadioGroup = itemView.findViewById(R.id.OPQOLDummyRadioGroup)
}
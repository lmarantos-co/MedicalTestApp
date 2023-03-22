package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.databinding.FragmentPatietTestListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [PatietTestListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatietTestListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<String>? = null
    private var param2: ArrayList<String>? = null
    private var param3 : String? = null
    private var mainActivity = MainActivity()
    private lateinit var namesArrayAdapter : ArrayAdapter<String>
    private lateinit var datesArrayAdapter : ArrayAdapter<String>
    private lateinit var patietTestListBinding: FragmentPatietTestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            param1 = it.getStringArrayList("param1")
            param2 = it.getStringArrayList("param2")
            param3 = it.getString("param3")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        patietTestListBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_patiet_test_list , container ,false)
        return patietTestListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patietTestListBinding.patientNameTxtV.setText(param3!!)
        namesArrayAdapter = ArrayAdapter<String>(mainActivity.applicationContext , R.layout.textcenter , param1!!)
        datesArrayAdapter = ArrayAdapter<String>(mainActivity.applicationContext , R.layout.textcenter , param2!!)
        namesArrayAdapter.notifyDataSetChanged()
        datesArrayAdapter.notifyDataSetChanged()
        patietTestListBinding.alltestsNameResultsistView.adapter = namesArrayAdapter
        patietTestListBinding.alltestsDatesResultsistView.adapter = datesArrayAdapter
        patietTestListBinding.okBtn.setOnClickListener {
            mainActivity.backToActivity()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: ArrayList<String>, param2: ArrayList<String> , param3 : String) =
            PatietTestListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM1, param1)
                    putStringArrayList(ARG_PARAM2, param2)
                    putString(ARG_PARAM3 , param3)
                }
            }
    }
}
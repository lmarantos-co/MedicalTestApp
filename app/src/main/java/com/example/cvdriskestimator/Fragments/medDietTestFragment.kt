package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.databinding.FragmentMedDietTestBinding
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMedDietTestViewModel
import com.example.cvdriskestimator.viewModels.CheckMedDietTestViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [medDietTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class medDietTestFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var medDietTestViewModel: CheckMedDietTestViewModel
    private lateinit var medDietTestBinding: FragmentMedDietTestBinding
    private lateinit var checkMedDietTestViewModelFactory: CheckMedDietTestViewModelFactory

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
        medDietTestViewModel = CheckMedDietTestViewModel()
        checkMedDietTestViewModelFactory = CheckMedDietTestViewModelFactory()
        medDietTestViewModel =  ViewModelProvider(this, checkMedDietTestViewModelFactory).get(CheckMedDietTestViewModel::class.java)
        medDietTestBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_med_diet_test, container , false)
        medDietTestBinding.lifecycleOwner = this
        return medDietTestBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            medDietTestFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
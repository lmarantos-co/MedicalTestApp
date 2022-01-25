package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentMDICheckBinding
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MDICheckFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class MDICheckFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var mdiCheckBinding: FragmentMDICheckBinding
    private lateinit var mdiPatientViewModel: CheckMDIPatientViewModel
    private lateinit var mdiPatientViewModelFactory : CheckMDIPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu

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
        mdiCheckBinding = FragmentMDICheckBinding.inflate(inflater , container , false)
        mdiPatientViewModel = CheckMDIPatientViewModel()
        mdiPatientViewModelFactory = CheckMDIPatientViewModelFactory()
        mdiPatientViewModel =  ViewModelProvider(this, mdiPatientViewModelFactory).get(CheckMDIPatientViewModel::class.java)
        mdiCheckBinding.checkMDIPatientViewModel = mdiPatientViewModel
        mdiCheckBinding.lifecycleOwner = this
        return mdiCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mdiPatientViewModel.passActivity(mainActivity)
        mdiPatientViewModel.passFragment(this)
        mdiPatientViewModel.initialiseRealm()

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "userDummy")
        if (userName != "userDummy")
        {
            mdiPatientViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            mdiPatientViewModel.setUserDummyData()
            mdiPatientViewModel.setPatientDataOnForm(userName)
        }

        //set the observer for the patient mutable live data
        mdiPatientViewModel.patientData.observe(viewLifecycleOwner , {
            setPatientData(it)
        })

        mdiCheckBinding.submitBtn.setOnClickListener {
            mdiPatientViewModel.checkMDITestPatient(getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ1RG) , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ2RG)
                                                    , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ3RG), getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ4RG)
                                                    , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ5RG) ,
                                                    getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ6RG) , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ7RG)  ,
                                                    getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ8RG), getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ9RG) ,
                                                    getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ10RG)  , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ11RG)
                                                    , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ12RG)  , getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ13RG))
        }

        //set the PopUpMenu
        popupMenu = PopUpMenu(mdiCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment)

        mdiCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        mdiCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        mdiCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        mdiCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }
    }

    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(mdiCheckBinding.MDIQ1RG , patient.patientMDIQ1)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ2RG , patient.patientMDIQ2)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ3RG , patient.patientMDIQ3)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ4RG , patient.patientMDIQ4)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ5RG , patient.patientMDIQ5)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ6RG , patient.patientMDIQ6)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ7RG , patient.patientMDIQ7)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ8RG , patient.patientMDIQ8)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ9RG , patient.patientMDIQ9)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ10RG , patient.patientMDIQ10)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ11RG , patient.patientMDIQ11)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ12RG , patient.patientMDIQ12)
            setQuestionRadioGroup(mdiCheckBinding.MDIQ13RG , patient.patientMDIQ13)

        } , 1000)
    }

    private fun initialisePatientData()
    {
        mdiCheckBinding.MDIQ1RG.clearCheck()
        mdiCheckBinding.MDIQ2RG.clearCheck()
        mdiCheckBinding.MDIQ3RG.clearCheck()
        mdiCheckBinding.MDIQ4RG.clearCheck()
        mdiCheckBinding.MDIQ5RG.clearCheck()
        mdiCheckBinding.MDIQ6RG.clearCheck()
        mdiCheckBinding.MDIQ7RG.clearCheck()
        mdiCheckBinding.MDIQ8RG.clearCheck()
        mdiCheckBinding.MDIQ9RG.clearCheck()
        mdiCheckBinding.MDIQ10RG.clearCheck()
        mdiCheckBinding.MDIQ11RG.clearCheck()
        mdiCheckBinding.MDIQ12RG.clearCheck()
        mdiCheckBinding.MDIQ13RG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup  , answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            0 ->
            {
                rg[0].isSelected = true
            }
            1 ->
            {
                rg[1].isSelected = true
            }
            2 ->
            {
                rg[2].isSelected = true
            }
            3->
            {
                rg[3].isSelected = true
            }
            4 ->
            {
                rg[4].isSelected = true
            }
            5 ->
            {
                rg[5].isSelected = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(0).id == radioButtonId)
            result = 5
        if (rg.get(1).id == radioButtonId)
            result = 4
        if (rg.get(2).id == radioButtonId)
            result = 3
        if (rg.get(3).id == radioButtonId)
            result = 2
        if (rg.get(4).id == radioButtonId)
            result = 1
        if (rg.get(5).id == radioButtonId)
            result = 0
        return result
    }

    fun hidePopPutermsLayout()
    {
        mdiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String)
    {
        Toast.makeText(mainActivity.applicationContext ,  error  , Toast.LENGTH_LONG ,).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {
        /**

         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MDICheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
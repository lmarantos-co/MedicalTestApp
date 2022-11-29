package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
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
    private lateinit var registerFragment: RegisterFragment
    private lateinit var loginFragment: LoginFragment
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popUpMenu: PopUpMenu
    private  var allPatientValues = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1)

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
        medDietTestViewModel.setActivity(mainActivity)
        medDietTestViewModel.setFragment(this)
        medDietTestViewModel.initialiseRealm()
        medDietTestBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_med_diet_test, container , false)
        medDietTestBinding.lifecycleOwner = this
        return medDietTestBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medDietTestBinding.includeCvdTitleForm.userIcon.alpha = 1f

        medDietTestBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
        if (userName != "tempUser")
        {
            medDietTestViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            medDietTestViewModel.setUserDummyData()
            medDietTestViewModel.setPatientDataOnForm(userName)
        }

        //set the observer for the patient data coming from view model
        medDietTestViewModel.patientData.observe(viewLifecycleOwner) {
            setPatientData(it)
        }

        medDietTestBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }
        initUI(view)
    }



    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ1 , patient.patientMDSQ1)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ2 , patient.patientMDSQ2)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ3 , patient.patientMDSQ3)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ4 , patient.patientMDSQ4)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ5 , patient.patientMDSQ5)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ6 , patient.patientMDSQ6)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ7 , patient.patientMDSQ7)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ8 , patient.patientMDSQ8)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ9 , patient.patientMDSQ9)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ10 , patient.patientMDSQ10)
            setQuestionRadioGroup(medDietTestBinding.medDietScoreRGQ11 , patient.patientMDSQ11)
        } , 1000)
    }

    fun initialisePatientData()
    {
        medDietTestBinding.medDietScoreRGQ1.clearCheck()
        medDietTestBinding.medDietScoreRGQ2.clearCheck()
        medDietTestBinding.medDietScoreRGQ3.clearCheck()
        medDietTestBinding.medDietScoreRGQ4.clearCheck()
        medDietTestBinding.medDietScoreRGQ5.clearCheck()
        medDietTestBinding.medDietScoreRGQ6.clearCheck()
        medDietTestBinding.medDietScoreRGQ7.clearCheck()
        medDietTestBinding.medDietScoreRGQ8.clearCheck()
        medDietTestBinding.medDietScoreRGQ9.clearCheck()
        medDietTestBinding.medDietScoreRGQ10.clearCheck()
        medDietTestBinding.medDietScoreRGQ11.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup , checkRadioButton : Int?)
    {
        when (checkRadioButton)
        {
            0 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            1 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            2 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            3 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            4 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }

            5 ->
            {
                (rg.getChildAt(checkRadioButton) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(0).id == radioButtonId)
            result = 0
        if (rg.get(1).id == radioButtonId)
            result = 1
        if (rg.get(2).id == radioButtonId)
            result = 2
        if (rg.get(3).id == radioButtonId)
            result = 3
        if (rg.get(4).id == radioButtonId)
            result = 4
        if (rg.get(5).id == radioButtonId)
            result = 5
        return result
    }

    fun hidePopPutermsLayout()
    {
        medDietTestBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                medDietTestBinding.Question1TxtV.requestFocus()
            }

            2 ->
            {
                medDietTestBinding.Question2TxtV.requestFocus()
            }

            3 ->
            {
                medDietTestBinding.Question3TxtV.requestFocus()
            }

            4 ->
            {
                medDietTestBinding.Question4TxtV.requestFocus()
            }

            5 ->
            {
                medDietTestBinding.Question5TxtV.requestFocus()
            }

            7 ->
            {
                medDietTestBinding.Question7TxtV.requestFocus()
            }

            8 ->
            {
                medDietTestBinding.Question8TxtV.requestFocus()
            }

            9 ->
            {
                medDietTestBinding.Question9TxtV.requestFocus()
            }

            10 ->
            {
                medDietTestBinding.Question10TxtV.requestFocus()
            }

            11 ->
            {
                medDietTestBinding.Question11TxtV.requestFocus()
            }


        }
    }

    private fun initUI(view : View)
    {
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()

        medDietTestBinding.clearBtn.setOnClickListener {
            AlertDialog.Builder(this.activity)
                .setTitle("Clear All Data")
                .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation

                        initialisePatientData()

                    })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        popUpMenu = PopUpMenu(medDietTestBinding.includePopUpMenu.termsRelLayout , mainActivity , this , loginFragment , registerFragment , null , leaderBoardFragment)
//        medDietTestBinding.includeCvdTitleForm.userIcon.setOnClickListener {
//            medDietTestBinding.includePopUpMenu.includePopupMenu.visibility = View.VISIBLE
//        }

        medDietTestBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenu.showPopUp(medDietTestBinding.includeCvdTitleForm.userIcon)
        }

        medDietTestBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        medDietTestBinding.submitBtn.setOnClickListener {
            //get the patient values into the arraylist
            allPatientValues[0] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ1)
            allPatientValues[1] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ2)
            allPatientValues[2] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ3)
            allPatientValues[3] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ4)
            allPatientValues[4] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ5)
            allPatientValues[5] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ6)
            allPatientValues[6] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ7)
            allPatientValues[7] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ8)
            allPatientValues[8] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ9)
            allPatientValues[9] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ10)
            allPatientValues[10] = getAsnwerFromRadioGroup(medDietTestBinding.medDietScoreRGQ11)
            medDietTestViewModel.checkMDSTestPatient(allPatientValues)
        }
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
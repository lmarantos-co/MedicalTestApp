package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
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
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentGDSCheckBinding
import com.example.cvdriskestimator.databinding.FragmentMDICheckBinding
import com.example.cvdriskestimator.viewModels.CheckGDSPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGDSViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModelFactory

class GDSCheckFragment : Fragment() {

    private lateinit var gdsCheckBinding: FragmentGDSCheckBinding
    private lateinit var gdsPatientViewModel: CheckGDSViewModel
    private lateinit var gdsPatientViewModelFactory : CheckGDSPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var loginFragment = LoginFragment.newInstance()
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
//    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1, 1, 1, 1 ,1 , 1)

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
        gdsCheckBinding = FragmentGDSCheckBinding.inflate(inflater , container , false)
        gdsPatientViewModel = CheckGDSViewModel()
        gdsPatientViewModelFactory = CheckGDSPatientViewModelFactory()
        gdsPatientViewModel =  ViewModelProvider(this, gdsPatientViewModelFactory).get(
            CheckGDSViewModel::class.java)
        gdsCheckBinding.checkGDSViewModel = gdsPatientViewModel
        gdsCheckBinding.lifecycleOwner = this
        return gdsCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gdsPatientViewModel.passActivity(mainActivity)
        gdsPatientViewModel.passFragment(this)
        gdsPatientViewModel.initialiseRealm()

        gdsCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
        if (userName != "tempUser")
        {
            gdsPatientViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            gdsPatientViewModel.setUserDummyData()
            gdsPatientViewModel.setPatientDataOnForm(userName)
        }

        //set the observer for the patient mutable live data
        gdsPatientViewModel.patientData.observe(viewLifecycleOwner) {
            setPatientData(it)
        }

        gdsCheckBinding.clearBtn.setOnClickListener {

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
                .show()        }

        gdsCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS1QRG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS2QRG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS3QRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS4QRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS5QRG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS6QRG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS7QRG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS8QRG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS9QRG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS10QRG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS11QRG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS12QRG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS13QRG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS14QRG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(gdsCheckBinding.GDS15QRG)


            gdsPatientViewModel.checkGDSTestPatient(allPatientSelections)
        }

        gdsCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(gdsCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , null ,leaderBoardFragment)

        gdsCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        gdsCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        gdsCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        gdsCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }
    }

    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gdsCheckBinding.GDS1QRG , patient.patientGDSQ1)
            setQuestionRadioGroup(gdsCheckBinding.GDS2QRG , patient.patientGDSQ2)
            setQuestionRadioGroup(gdsCheckBinding.GDS3QRG , patient.patientGDSQ3)
            setQuestionRadioGroup(gdsCheckBinding.GDS4QRG , patient.patientGDSQ4)
            setQuestionRadioGroup(gdsCheckBinding.GDS5QRG , patient.patientGDSQ5)
            setQuestionRadioGroup(gdsCheckBinding.GDS6QRG , patient.patientGDSQ6)
            setQuestionRadioGroup(gdsCheckBinding.GDS7QRG , patient.patientGDSQ7)
            setQuestionRadioGroup(gdsCheckBinding.GDS8QRG , patient.patientGDSQ8)
            setQuestionRadioGroup(gdsCheckBinding.GDS9QRG , patient.patientGDSQ9)
            setQuestionRadioGroup(gdsCheckBinding.GDS10QRG , patient.patientGDSQ10)
            setQuestionRadioGroup(gdsCheckBinding.GDS11QRG , patient.patientGDSQ11)
            setQuestionRadioGroup(gdsCheckBinding.GDS12QRG , patient.patientGDSQ12)
            setQuestionRadioGroup(gdsCheckBinding.GDS13QRG , patient.patientGDSQ13)
            setQuestionRadioGroup(gdsCheckBinding.GDS14QRG , patient.patientGDSQ14)
            setQuestionRadioGroup(gdsCheckBinding.GDS15QRG , patient.patientGDSQ15)

        } , 1000)
    }

    fun initialisePatientData()
    {
        gdsCheckBinding.GDS1QRG.clearCheck()
        gdsCheckBinding.GDS2QRG.clearCheck()
        gdsCheckBinding.GDS3QRG.clearCheck()
        gdsCheckBinding.GDS4QRG.clearCheck()
        gdsCheckBinding.GDS5QRG.clearCheck()
        gdsCheckBinding.GDS6QRG.clearCheck()
        gdsCheckBinding.GDS7QRG.clearCheck()
        gdsCheckBinding.GDS8QRG.clearCheck()
        gdsCheckBinding.GDS9QRG.clearCheck()
        gdsCheckBinding.GDS10QRG.clearCheck()
        gdsCheckBinding.GDS11QRG.clearCheck()
        gdsCheckBinding.GDS12QRG.clearCheck()
        gdsCheckBinding.GDS13QRG.clearCheck()
        gdsCheckBinding.GDS14QRG.clearCheck()
        gdsCheckBinding.GDS15QRG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            1 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            0 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(1).id == radioButtonId)
            result = 1
        if (rg.get(0).id == radioButtonId)
            result = 0
        return result
    }

    fun hidePopPutermsLayout()
    {
        gdsCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                gdsCheckBinding.GDS1QRG.requestFocus()
            }

            2 ->
            {
                gdsCheckBinding.GDS2QRG.requestFocus()
            }

            3 ->
            {
                gdsCheckBinding.GDS3QRG.requestFocus()
            }

            4 ->
            {
                gdsCheckBinding.GDS4QRG.requestFocus()
            }

            5 ->
            {
                gdsCheckBinding.GDS5QRG.requestFocus()
            }

            6 ->
            {
                gdsCheckBinding.GDS6QRG.requestFocus()
            }

            7 ->
            {
                gdsCheckBinding.GDS7QRG.requestFocus()
            }

            8 ->
            {
                gdsCheckBinding.GDS8QRG.requestFocus()
            }

            9 ->
            {
                gdsCheckBinding.GDS9QRG.requestFocus()
            }

            10 ->
            {
                gdsCheckBinding.GDS10QRG.requestFocus()
            }

            11 ->
            {
                gdsCheckBinding.GDS11QRG.requestFocus()
            }

            12 ->
            {
                gdsCheckBinding.GDS12QRG.requestFocus()
            }

            13 ->
            {
                gdsCheckBinding.GDS13QRG.requestFocus()
            }

            14 ->
            {
                gdsCheckBinding.GDS14QRG.requestFocus()
            }

            15 ->
            {
                gdsCheckBinding.GDS15QRG.requestFocus()
            }
        }
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
            GDSCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}
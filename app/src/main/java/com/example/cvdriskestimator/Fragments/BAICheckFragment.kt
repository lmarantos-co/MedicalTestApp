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
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentBAICheckBinding
import com.example.cvdriskestimator.databinding.FragmentCheckBinding
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [BAICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BAICheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var baiPatientViewModel: CheckBAIPatientViewModel
    private lateinit var baiCheckBinding : FragmentBAICheckBinding
    private var allPatientSelections = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1, 1)
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //assign the binding to the xml layout
        baiCheckBinding = FragmentBAICheckBinding.inflate(inflater , container , false)
        baiPatientViewModel = CheckBAIPatientViewModel()
        val checkBAIPatientViewModelFactory = CheckBAIPatientViewModelFactory()
        baiPatientViewModel = ViewModelProvider(this , checkBAIPatientViewModelFactory).get(CheckBAIPatientViewModel::class.java)
        baiCheckBinding.checkBAIPatientViewModel = baiPatientViewModel
        baiCheckBinding.lifecycleOwner = this
        return baiCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baiPatientViewModel.passActivity(mainActivity)
        baiPatientViewModel.passFragment(this)
        baiPatientViewModel.initialiseRealm()

        baiCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
        if (userName != "tempUser")
        {
            baiPatientViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            baiPatientViewModel.setUserDummyData()
            baiPatientViewModel.setPatientDataOnForm(userName)
        }

        //set the observer for the patient mutable live data
        baiPatientViewModel.patientData.observe(viewLifecycleOwner) {
            setPatientData(it)
        }

        baiCheckBinding.clearBtn.setOnClickListener {
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

        baiCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ20RG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(baiCheckBinding.BAIQ21RG)



            baiPatientViewModel.checkBAITestPatient(allPatientSelections)
        }

        baiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(baiCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment)

        baiCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        baiCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        baiCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        baiCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }
    }

    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(baiCheckBinding.BAIQ1RG , patient.patientBAIQ1)
            setQuestionRadioGroup(baiCheckBinding.BAIQ2RG , patient.patientBAIQ2)
            setQuestionRadioGroup(baiCheckBinding.BAIQ3RG , patient.patientBAIQ3)
            setQuestionRadioGroup(baiCheckBinding.BAIQ4RG , patient.patientBAIQ4)
            setQuestionRadioGroup(baiCheckBinding.BAIQ5RG , patient.patientBAIQ5)
            setQuestionRadioGroup(baiCheckBinding.BAIQ6RG , patient.patientBAIQ6)
            setQuestionRadioGroup(baiCheckBinding.BAIQ7RG , patient.patientBAIQ7)
            setQuestionRadioGroup(baiCheckBinding.BAIQ8RG , patient.patientBAIQ8)
            setQuestionRadioGroup(baiCheckBinding.BAIQ9RG , patient.patientBAIQ9)
            setQuestionRadioGroup(baiCheckBinding.BAIQ10RG , patient.patientBAIQ10)
            setQuestionRadioGroup(baiCheckBinding.BAIQ11RG , patient.patientBAIQ11)
            setQuestionRadioGroup(baiCheckBinding.BAIQ12RG , patient.patientBAIQ12)
            setQuestionRadioGroup(baiCheckBinding.BAIQ13RG , patient.patientBAIQ13)
            setQuestionRadioGroup(baiCheckBinding.BAIQ14RG , patient.patientBAIQ14)
            setQuestionRadioGroup(baiCheckBinding.BAIQ15RG , patient.patientBAIQ15)
            setQuestionRadioGroup(baiCheckBinding.BAIQ16RG , patient.patientBAIQ16)
            setQuestionRadioGroup(baiCheckBinding.BAIQ17RG , patient.patientBAIQ17)
            setQuestionRadioGroup(baiCheckBinding.BAIQ18RG , patient.patientBAIQ18)
            setQuestionRadioGroup(baiCheckBinding.BAIQ19RG , patient.patientBAIQ19)
            setQuestionRadioGroup(baiCheckBinding.BAIQ20RG , patient.patientBAIQ20)
            setQuestionRadioGroup(baiCheckBinding.BAIQ21RG , patient.patientBAIQ21)
        } , 1000)
    }

    fun initialisePatientData()
    {
        baiCheckBinding.BAIQ1RG.clearCheck()
        baiCheckBinding.BAIQ2RG.clearCheck()
        baiCheckBinding.BAIQ3RG.clearCheck()
        baiCheckBinding.BAIQ4RG.clearCheck()
        baiCheckBinding.BAIQ5RG.clearCheck()
        baiCheckBinding.BAIQ6RG.clearCheck()
        baiCheckBinding.BAIQ7RG.clearCheck()
        baiCheckBinding.BAIQ8RG.clearCheck()
        baiCheckBinding.BAIQ9RG.clearCheck()
        baiCheckBinding.BAIQ10RG.clearCheck()
        baiCheckBinding.BAIQ11RG.clearCheck()
        baiCheckBinding.BAIQ12RG.clearCheck()
        baiCheckBinding.BAIQ13RG.clearCheck()
        baiCheckBinding.BAIQ14RG.clearCheck()
        baiCheckBinding.BAIQ15RG.clearCheck()
        baiCheckBinding.BAIQ16RG.clearCheck()
        baiCheckBinding.BAIQ17RG.clearCheck()
        baiCheckBinding.BAIQ18RG.clearCheck()
        baiCheckBinding.BAIQ19RG.clearCheck()
        baiCheckBinding.BAIQ20RG.clearCheck()
        baiCheckBinding.BAIQ21RG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            0 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }
            1 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            2 ->
            {
                (rg.getChildAt(2) as RadioButton).isChecked = true
            }
            3->
            {
                (rg.getChildAt(3) as RadioButton).isChecked = true
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
        return result
    }

    fun hidePopPutermsLayout()
    {
        baiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BAICheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
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
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1, 1, 1, 1)

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

        mdiCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")
        if (userName != "tempUser")
        {
            mdiPatientViewModel.setPatientDataOnForm(userName!!)
        }
        else
        {
            mdiPatientViewModel.setUserDummyData()
            mdiPatientViewModel.setPatientDataOnForm(userName)
        }

        //set the observer for the patient mutable live data
        mdiPatientViewModel.patientData.observe(viewLifecycleOwner) {
            setPatientData(it)
        }

        mdiCheckBinding.clearBtn.setOnClickListener {

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

        mdiCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(mdiCheckBinding.MDIQ13RG)


            mdiPatientViewModel.checkMDITestPatient(allPatientSelections)
        }

        mdiCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(mdiCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,  loginFragment, registerFragment , leaderBoardFragment)

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

    fun initialisePatientData()
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
                (rg.getChildAt(5) as RadioButton).isChecked = true
            }
            1 ->
            {
                (rg.getChildAt(4) as RadioButton).isChecked = true
            }
            2 ->
            {
                (rg.getChildAt(3) as RadioButton).isChecked = true
            }
            3->
            {
                (rg.getChildAt(2) as RadioButton).isChecked = true
            }
            4 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
            5 ->
            {
                (rg.getChildAt(0) as RadioButton).isChecked = true
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

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                mdiCheckBinding.MDIQ1TxtV.requestFocus()
            }

            2 ->
            {
                mdiCheckBinding.MDIQ2TxtV.requestFocus()
            }

            3 ->
            {
                mdiCheckBinding.MDIQ3TxtV.requestFocus()
            }

            4 ->
            {
                mdiCheckBinding.MDIQ4TxtV.requestFocus()
            }

            5 ->
            {
                mdiCheckBinding.MDIQ5TxtV.requestFocus()
            }

            6 ->
            {
                mdiCheckBinding.MDIQ6TxtV.requestFocus()
            }

            7 ->
            {
                mdiCheckBinding.MDIQ7TxtV.requestFocus()
            }

            8 ->
            {
                mdiCheckBinding.MDIQ8TxtV.requestFocus()
            }

            9 ->
            {
                mdiCheckBinding.MDIQ9TxtV.requestFocus()
            }

            10 ->
            {
                mdiCheckBinding.MDIQ10TxtV.requestFocus()
            }

            11 ->
            {
                mdiCheckBinding.MDIQ11TxtV.requestFocus()
            }

            12 ->
            {
                mdiCheckBinding.MDIQ12TxtV.requestFocus()
            }

            13 ->
            {
                mdiCheckBinding.MDIQ13TxtV.requestFocus()
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
            MDICheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
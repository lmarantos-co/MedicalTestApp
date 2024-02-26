package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.databinding.FragmentBAICheckBinding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheckBinding
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckBAIPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel



class OPQOLCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var opqolPatientViewModel: CheckOPQOLViewModel
    private lateinit var opqolCheckBinding : FragmentOPQOLCheckBinding
    private var allPatientSelections =
    arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1)
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginPatientFragment: LoginPatientFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment

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
        opqolCheckBinding = FragmentOPQOLCheckBinding.inflate(inflater , container , false)
        opqolPatientViewModel = CheckOPQOLViewModel()
        val checkBAIPatientViewModelFactory = CheckBAIPatientViewModelFactory()
        opqolPatientViewModel = ViewModelProvider(this , checkBAIPatientViewModelFactory).get(
            CheckOPQOLViewModel::class.java)
        opqolCheckBinding.checkOQPOLPatientViewModel = opqolPatientViewModel
        opqolCheckBinding.lifecycleOwner = this
        return opqolCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        opqolPatientViewModel.passActivity(mainActivity)
        opqolPatientViewModel.passFragment(this)
        opqolPatientViewModel.initialiseRealm()

        opqolCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.requireArguments().getString("patientId")
        var testDate = this.requireArguments().getString("testDate" , "")
        var openType = this.requireArguments().getString("openType")


        if (openType == "open_history")
        {
            var historyTest = Test()
            if (patientId != "")
            {
                if (testDate != "")
                {
                    //var date = convertStringToDate(testDate!!)
                    //default time zone
//                    val defaultZoneId: ZoneId = ZoneId.systemDefault()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
//                    var testDateFormated = convertStringToCalenderDate(testDate)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = opqolPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.patientBAITestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                opqolPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                opqolPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                opqolPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        opqolPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        opqolPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        opqolCheckBinding.clearBtn.setOnClickListener {
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

        opqolCheckBinding.submitBtn.setOnClickListener {


            allPatientSelections[0] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C1RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C1bRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C1cRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C1dRG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C2aRG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C2bRG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C2cRG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C2dRG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C3aRG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C3bRG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C3cRG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C3dRG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C3eRG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C4aRG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C4bRG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C4cRG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C4dRG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C5aRG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C5bRG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C5cRG)
            allPatientSelections[21] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C5dRG)
            allPatientSelections[22] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C6aRG)
            allPatientSelections[23] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C6bRG)
            allPatientSelections[24] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C6cRG)
            allPatientSelections[25] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C6dRG)
            allPatientSelections[26] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C7aRG)
            allPatientSelections[27] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C7bRG)
            allPatientSelections[28] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C7cRG)
            allPatientSelections[29] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C7dRG)
            allPatientSelections[30] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8aRG)
            allPatientSelections[31] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8bRG)
            allPatientSelections[32] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8cRG)
            allPatientSelections[33] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8dRG)
            allPatientSelections[34] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8eRG)
            allPatientSelections[35] = getAsnwerFromRadioGroup(opqolCheckBinding.OPQOLQ2C8fRG)


            opqolPatientViewModel.checkBAITestPatient(allPatientSelections)
        }

        opqolCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginPatientFragment = LoginPatientFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(opqolCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,   registerFragment , null , leaderBoardFragment)

        opqolCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        opqolCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun showSelectionError(error : String, qNo : Int) {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        {
            when (qNo) {
                1 -> opqolCheckBinding.OPQOLQ1RG.requestFocus()
                2 -> opqolCheckBinding.OPQOLQ2C1RG.requestFocus()
                3 -> opqolCheckBinding.OPQOLQ2C1bRG.requestFocus()
                4 -> opqolCheckBinding.OPQOLQ2C1cRG.requestFocus()
                5 -> opqolCheckBinding.OPQOLQ2C1dRG.requestFocus()
                6 -> opqolCheckBinding.OPQOLQ2C2aRG.requestFocus()
                7 -> opqolCheckBinding.OPQOLQ2C2bRG.requestFocus()
                8 -> opqolCheckBinding.OPQOLQ2C2cRG.requestFocus()
                9 -> opqolCheckBinding.OPQOLQ2C2dRG.requestFocus()
                10 -> opqolCheckBinding.OPQOLQ2C3aRG.requestFocus()
                11 -> opqolCheckBinding.OPQOLQ2C3bRG.requestFocus()
                12 -> opqolCheckBinding.OPQOLQ2C3cRG.requestFocus()
                13 -> opqolCheckBinding.OPQOLQ2C3dRG.requestFocus()
                14 -> opqolCheckBinding.OPQOLQ2C3eRG.requestFocus()
                15 -> opqolCheckBinding.OPQOLQ2C4aRG.requestFocus()
                16 -> opqolCheckBinding.OPQOLQ2C4bRG.requestFocus()
                17 -> opqolCheckBinding.OPQOLQ2C4cRG.requestFocus()
                18 -> opqolCheckBinding.OPQOLQ2C4dRG.requestFocus()
                19 -> opqolCheckBinding.OPQOLQ2C5aRG.requestFocus()
                20 -> opqolCheckBinding.OPQOLQ2C5bRG.requestFocus()
                21 -> opqolCheckBinding.OPQOLQ2C5cRG.requestFocus()
                22 -> opqolCheckBinding.OPQOLQ2C5dRG.requestFocus()
                23 -> opqolCheckBinding.OPQOLQ2C6aRG.requestFocus()
                24 -> opqolCheckBinding.OPQOLQ2C6bRG.requestFocus()
                25 -> opqolCheckBinding.OPQOLQ2C6cRG.requestFocus()
                26 -> opqolCheckBinding.OPQOLQ2C6dRG.requestFocus()
                27 -> opqolCheckBinding.OPQOLQ2C7aRG.requestFocus()
                28 -> opqolCheckBinding.OPQOLQ2C7bRG.requestFocus()
                29 -> opqolCheckBinding.OPQOLQ2C7cRG.requestFocus()
                30 -> opqolCheckBinding.OPQOLQ2C7dRG.requestFocus()
                31 -> opqolCheckBinding.OPQOLQ2C8aRG.requestFocus()
                32 -> opqolCheckBinding.OPQOLQ2C8bRG.requestFocus()
                33 -> opqolCheckBinding.OPQOLQ2C8cRG.requestFocus()
                34 -> opqolCheckBinding.OPQOLQ2C8dRG.requestFocus()
                35 -> opqolCheckBinding.OPQOLQ2C8eRG.requestFocus()
                36 -> opqolCheckBinding.OPQOLQ2C8fRG.requestFocus()
            }
        }
    }

    private fun getAsnwerFromRadioGroup(opqolQ1RG: RadioGroup): Int? {

        var result : Int? = null
        val radioButtonId = opqolQ1RG.checkedRadioButtonId
        if ((opqolQ1RG != opqolCheckBinding.OPQOLQ2C1dRG) && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C2bRG)
            && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C2cRG) && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C4cRG)
            && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C7dRG) && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C8dRG))
        {
            if (opqolQ1RG.get(0).id == radioButtonId)
            {
                result = 1
            }
            if (opqolQ1RG.get(1).id == radioButtonId)
            {
                result = 2
            }
            if (opqolQ1RG.get(2).id == radioButtonId)
            {
                result = 3
            }
            if (opqolQ1RG.get(3).id == radioButtonId)
            {
                result = 4
            }
            if (opqolQ1RG.get(4).id == radioButtonId)
            {
                result = 5
            }
        }
        else
        {
            if (opqolQ1RG.get(0).id == radioButtonId)
            {
                result = 5
            }
            if (opqolQ1RG.get(1).id == radioButtonId)
            {
                result = 4
            }
            if (opqolQ1RG.get(2).id == radioButtonId)
            {
                result = 3
            }
            if (opqolQ1RG.get(3).id == radioButtonId)
            {
                result = 2
            }
            if (opqolQ1RG.get(4).id == radioButtonId)
            {
                result = 1
            }
        }
        return result
    }


    fun hidePopPutermsLayout()
    {
        opqolCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun initialisePatientData()
    {
        opqolCheckBinding.OPQOLQ1RG.clearCheck()
        opqolCheckBinding.OPQOLQ2C1RG.clearCheck()
        opqolCheckBinding.OPQOLQ2C1bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C1cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C1dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C2aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C2bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C2cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C2dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C3aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C3bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C3cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C3dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C3eRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C4aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C4bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C4cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C4dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C5aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C5bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C5cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C5dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C6aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C6bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C6cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C6dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C7aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C7bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C7cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C7dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8aRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8bRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8cRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8dRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8eRG.clearCheck()
        opqolCheckBinding.OPQOLQ2C8fRG.clearCheck()
    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            OPQOLCheckFragment().apply {

            }
    }
}
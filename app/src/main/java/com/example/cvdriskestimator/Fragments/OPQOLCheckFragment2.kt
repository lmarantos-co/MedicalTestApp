package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2Binding
import com.example.cvdriskestimator.viewModels.CheckOPQOLPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel


class OPQOLCheckFragment2 : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var opqolCheckFragment: OPQOLCheckFragment
    private lateinit var opqolPatientViewModel: CheckOPQOLViewModel
    private lateinit var opqolCheckBinding2 : FragmentOPQOLCheck2Binding
    private var allPatientSelections =
        arrayListOf<Int?>( 1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1)
    private var selectionsFromPreviousFragment = ArrayList<Int>(17)
    private var selectionsFromThisFragment = ArrayList<Int>(18)
    private var selectionsFromPreviousFragmentExists : Boolean = false
    private var selectionsFromThisFragmentExists : Boolean = false
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginPatientFragment: LoginPatientFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment
    private lateinit var formLinearlayout : LinearLayout

    private var patientTest = Test()
    private var openHistory : Boolean = false
    private lateinit var historyTest : Test

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

        opqolCheckBinding2 = FragmentOPQOLCheck2Binding.inflate(inflater, container, false)
        opqolPatientViewModel = CheckOPQOLViewModel()
        opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
            CheckOPQOLViewModel::class.java)
        opqolCheckBinding2.checkOQPOLPatientViewModel = opqolPatientViewModel
        opqolCheckBinding2.root

        //fetch the previous patient selections from the fragment bundle
        arguments?.let {
            if (it.containsKey("Answers"))
            {
                selectionsFromPreviousFragment = it.getSerializable("Answers") as ArrayList<Int>
                selectionsFromPreviousFragmentExists = true
            }
            if (it.containsKey("Answers2"))
            {
                selectionsFromThisFragment = it.getSerializable("Answers2") as ArrayList<Int>
                selectionsFromThisFragmentExists = true
            }
        }
        return opqolCheckBinding2.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        opqolPatientViewModel.passActivity(mainActivity)
        opqolPatientViewModel.passFragment2(this)
        opqolPatientViewModel.initialiseRealm()

        opqolCheckBinding2.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        if (selectionsFromThisFragmentExists)
        {
            setPatientData2(selectionsFromThisFragment)
        }

//        var patientId = this.requireArguments().getString("patientId")
//        var testDate = this.requireArguments().getString("testDate" , "")
//        var openType = this.requireArguments().getString("openType")
//
//        if (openType == "open_history")
//        {
//            openHistory = true
//            historyTest = Test()
//            if (patientId != "")
//            {
//                if (testDate != "")
//                {
//                    //var date = convertStringToDate(testDate!!)
//                    //default time zone
////                    val defaultZoneId: ZoneId = ZoneId.systemDefault()
////                    val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
////                    var testDateFormated = convertStringToCalenderDate(testDate)
////                    val localDate = LocalDate.parse(testDateFormated)
////                    val text: String = localDate.format(formatter)
////                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
////                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
////                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
//                    historyTest = opqolPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
//                }
//            }
//            if (historyTest.OPQOLTestReesult != null)
//            {
//                setPatientData2(historyTest)
//            }
//        }
//        else
//        {
//            if (openType == "updateLast")
//            {
//                opqolPatientViewModel.setPatientDataOnForm(userName!!)
//            }
//            if (openType == "addNew")
//            {
//                opqolPatientViewModel.setUserDummyData()
//                //baiPatientViewModel.setPatientDataOnForm(userName!!)
//            }
//            if (openType == "history")
//            {
//                opqolPatientViewModel.history()
//            }
//        }


        //set the observer for the patient mutable live data
//        opqolPatientViewModel.patientData.observe(viewLifecycleOwner) {
//        }
//
//        opqolPatientViewModel.testDATA.observe(viewLifecycleOwner) {
//            if (it != null)
//            {
//                setPatientData1(it)
//            }
//        }


        opqolCheckBinding2.clearBtn.setOnClickListener {
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

        opqolCheckBinding2.leftArrow.setOnClickListener {

//            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)

            allPatientSelections[0] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)

            if (opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections))
            {
                val args = Bundle()
                args.putSerializable("Answers2" , allPatientSelections)
                args.putSerializable("Answers" , selectionsFromPreviousFragment)
                opqolCheckFragment = OPQOLCheckFragment.newInstance(args)
                mainActivity.fragmentTransaction(opqolCheckFragment)
            }
        }

        opqolCheckBinding2.submitBtn.setOnClickListener {
            allPatientSelections[0] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)

            var thePatientSelections = ArrayList<Int>(36)

            for (i in 0..selectionsFromPreviousFragment.size)
            {
                thePatientSelections.set(i , selectionsFromPreviousFragment.get(i))
            }

            var counter = 17
            for (i in 0..thePatientSelections.size)
            {
                thePatientSelections.set(counter , selectionsFromPreviousFragment.get(i))
                counter ++
            }
            opqolPatientViewModel.passAllPatientSelections(thePatientSelections)
            if (opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections))
                opqolPatientViewModel.openResultFragment()

        }


        opqolCheckBinding2.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginPatientFragment = LoginPatientFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(opqolCheckBinding2.includePopUpMenu.termsRelLayout , mainActivity, this,   registerFragment , null , leaderBoardFragment)

        opqolCheckBinding2.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        opqolCheckBinding2.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBinding2.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBinding2.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }


    private fun setPatientData2(answers : ArrayList<Int>)
    {
        Handler(Looper.getMainLooper()).postDelayed({
//            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5aRG , answers.get(0))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5bRG , answers.get(1))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5cRG , answers.get(2))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5dRG , answers.get(3))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6aRG , answers.get(4))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6bRG , answers.get(5))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6cRG , answers.get(6))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6dRG , answers.get(7))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7aRG , answers.get(8))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7bRG , answers.get(9))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7cRG, answers.get(10))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7dRG , answers.get(11))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8aRG , answers.get(12))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8bRG , answers.get(13))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8cRG , answers.get(14))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8dRG , answers.get(15))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8eRG , answers.get(16))
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8fRG , answers.get(17))

        } , 1000)
    }


    fun showSelectionError(error : String, qNo : Int) {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when (qNo) {
            19 -> opqolCheckBinding2.OPQOLQ2C5aRG.requestFocus()
            20 -> opqolCheckBinding2.OPQOLQ2C5bRG.requestFocus()
            21 -> opqolCheckBinding2.OPQOLQ2C5cRG.requestFocus()
            22 -> opqolCheckBinding2.OPQOLQ2C5dRG.requestFocus()
            23 -> opqolCheckBinding2.OPQOLQ2C6aRG.requestFocus()
            24 -> opqolCheckBinding2.OPQOLQ2C6bRG.requestFocus()
            25 -> opqolCheckBinding2.OPQOLQ2C6cRG.requestFocus()
            26 -> opqolCheckBinding2.OPQOLQ2C6dRG.requestFocus()
            27 -> opqolCheckBinding2.OPQOLQ2C7aRG.requestFocus()
            28 -> opqolCheckBinding2.OPQOLQ2C7bRG.requestFocus()
            29 -> opqolCheckBinding2.OPQOLQ2C7cRG.requestFocus()
            30 -> opqolCheckBinding2.OPQOLQ2C7dRG.requestFocus()
            31 -> opqolCheckBinding2.OPQOLQ2C8aRG.requestFocus()
            32 -> opqolCheckBinding2.OPQOLQ2C8bRG.requestFocus()
            33 -> opqolCheckBinding2.OPQOLQ2C8cRG.requestFocus()
            34 -> opqolCheckBinding2.OPQOLQ2C8dRG.requestFocus()
            35 -> opqolCheckBinding2.OPQOLQ2C8eRG.requestFocus()
            36 -> opqolCheckBinding2.OPQOLQ2C8fRG.requestFocus()
        }
    }

    private fun setQuestionRadioGroup(rg: RadioGroup, patientAnswer: Int) {

        if ((rg != opqolCheckBinding2.OPQOLQ2C7dRG) && (rg != opqolCheckBinding2.OPQOLQ2C8dRG))
        {
            when (patientAnswer)
            {
                1 -> (rg.getChildAt(0) as RadioButton).isChecked = true
                2 -> (rg.getChildAt(1) as RadioButton).isChecked = true
                3 -> (rg.getChildAt(2) as RadioButton).isChecked = true
                4 -> (rg.getChildAt(3) as RadioButton).isChecked = true
                5 -> (rg.getChildAt(5) as RadioButton).isChecked = true
            }
        }
        else
        {
            when (patientAnswer)
            {
                5 -> (rg.getChildAt(0) as RadioButton).isChecked = true
                4 -> (rg.getChildAt(1) as RadioButton).isChecked = true
                3 -> (rg.getChildAt(2) as RadioButton).isChecked = true
                2 -> (rg.getChildAt(3) as RadioButton).isChecked = true
                1 -> (rg.getChildAt(5) as RadioButton).isChecked = true
            }
        }
    }


    private fun getAsnwerFromRadioGroup2(opqolQ1RG: RadioGroup): Int? {

        var result : Int? = null
        val radioButtonId = opqolQ1RG.checkedRadioButtonId
        if ((opqolQ1RG != opqolCheckBinding2.OPQOLQ2C7dRG) && (opqolQ1RG != opqolCheckBinding2.OPQOLQ2C8dRG))
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
        else
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
        return result
    }


    fun hidePopPutermsLayout()
    {
        opqolCheckBinding2.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun initialisePatientData()
    {
        opqolCheckBinding2.OPQOLQ2C5aRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C5bRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C5cRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C5dRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C6aRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C6bRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C6cRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C6dRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C7aRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C7bRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C7cRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C7dRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8aRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8bRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8cRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8dRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8eRG.clearCheck()
        opqolCheckBinding2.OPQOLQ2C8fRG.clearCheck()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(args : Bundle) =
            OPQOLCheckFragment2().apply {
                val fragment = OPQOLCheckFragment2()
                fragment.arguments = args
                return fragment            }
    }
}
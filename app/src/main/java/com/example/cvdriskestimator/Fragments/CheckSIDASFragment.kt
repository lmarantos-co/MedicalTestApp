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
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentCheckSIDASBinding
import com.example.cvdriskestimator.databinding.FragmentMDICheckBinding
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckMDIPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckSIDASPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckSIDASPatientViewModelFactory
import java.sql.Date
import java.util.*


class SIDASCheckFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var sidasCheckBinding: FragmentCheckSIDASBinding
    private lateinit var sidasPatientViewModel: CheckSIDASPatientViewModel
    private lateinit var sidasPatientViewModelFactory : CheckSIDASPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1)

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
        sidasCheckBinding = FragmentCheckSIDASBinding.inflate(inflater , container , false)
        sidasPatientViewModel = CheckSIDASPatientViewModel()
        sidasPatientViewModelFactory = CheckSIDASPatientViewModelFactory()
        sidasPatientViewModel =  ViewModelProvider(this, sidasPatientViewModelFactory).get(CheckSIDASPatientViewModel::class.java)
        sidasCheckBinding.checkSIDASViewModel = sidasPatientViewModel
        sidasCheckBinding.lifecycleOwner = this
        return sidasCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sidasPatientViewModel.passActivity(mainActivity)
        sidasPatientViewModel.passFragment(this)
        sidasPatientViewModel.initialiseRealm()

        sidasCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate")
        var openType = this.arguments!!.getString("openType")


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
//                    var testDateFormated = convertStringToCalenderDate(testDate!!)
//                    val localDate = LocalDate.parse(testDateFormated)
//                    val text: String = localDate.format(formatter)
//                    val parsedDate: LocalDate = LocalDate.parse(text, formatter)
//                    val covertedDate = java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant())
//                    val d = SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString())
                    historyTest = sidasPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.patientMDITestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                sidasPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                sidasPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                sidasPatientViewModel.history()
            }
        }

        //set the observer for the patient mutable live data
        sidasPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        sidasPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        sidasCheckBinding.clearBtn.setOnClickListener {

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

        sidasCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(sidasCheckBinding.SIDAS1QRG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(sidasCheckBinding.SIDAS2QRG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(sidasCheckBinding.SIDAS3QRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(sidasCheckBinding.SIDAS4QRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(sidasCheckBinding.SIDAS5QRG)



            sidasPatientViewModel.checkMDITestPatient(allPatientSelections)
        }

        sidasCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(sidasCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this, registerFragment , null ,leaderBoardFragment)

        sidasCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        sidasCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        sidasCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        sidasCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(sidasCheckBinding.SIDAS1QRG , test.patientMDIQ1)
            setQuestionRadioGroup(sidasCheckBinding.SIDAS2QRG , test.patientMDIQ2)
            setQuestionRadioGroup(sidasCheckBinding.SIDAS3QRG , test.patientMDIQ3)
            setQuestionRadioGroup(sidasCheckBinding.SIDAS4QRG , test.patientMDIQ4)
            setQuestionRadioGroup(sidasCheckBinding.SIDAS5QRG , test.patientMDIQ5)

        } , 1000)
    }

    fun initialisePatientData()
    {
        sidasCheckBinding.SIDAS1QRG.clearCheck()
        sidasCheckBinding.SIDAS2QRG.clearCheck()
        sidasCheckBinding.SIDAS3QRG.clearCheck()
        sidasCheckBinding.SIDAS4QRG.clearCheck()
        sidasCheckBinding.SIDAS5QRG.clearCheck()
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

            6 ->
            {
                (rg.getChildAt(6) as RadioButton).isChecked = true
            }

            7 ->
            {
                (rg.getChildAt(7) as RadioButton).isChecked = true
            }

            8 ->
            {
                (rg.getChildAt(8) as RadioButton).isChecked = true
            }

            9->
            {
                (rg.getChildAt(9) as RadioButton).isChecked = true
            }

            10 ->
            {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if (rg.get(5).id == radioButtonId)
            result = 6
        if (rg.get(6).id == radioButtonId)
            result = 7
        if (rg.get(7).id == radioButtonId)
            result = 8
        if (rg.get(8).id == radioButtonId)
            result = 9
        if (rg.get(9).id == radioButtonId)
            result = 10

        if (rg.get(0).id == radioButtonId)
            result = 1
        if (rg.get(1).id == radioButtonId)
            result = 2
        if (rg.get(2).id == radioButtonId)
            result = 3
        if (rg.get(3).id == radioButtonId)
            result = 4
        if (rg.get(4).id == radioButtonId)
            result = 5

        return result
    }

    fun hidePopPutermsLayout()
    {
        sidasCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                sidasCheckBinding.SIDAS1QRG.requestFocus()
            }

            2 ->
            {
                sidasCheckBinding.SIDAS2QRG.requestFocus()
            }

            3 ->
            {
                sidasCheckBinding.SIDAS3QRG.requestFocus()
            }

            4 ->
            {
                sidasCheckBinding.SIDAS4QRG.requestFocus()
            }

            5 ->
            {
                sidasCheckBinding.SIDAS5QRG.requestFocus()
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
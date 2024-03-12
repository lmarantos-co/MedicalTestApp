package com.example.cvdriskestimator.Fragments

import android.annotation.SuppressLint
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
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentCheckGasBinding
import com.example.cvdriskestimator.databinding.FragmentGDSCheckBinding
import com.example.cvdriskestimator.viewModels.CheckGASPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGASViewModel
import com.example.cvdriskestimator.viewModels.CheckGDSPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGDSViewModel
import java.sql.Date
import java.util.*

class GASCheckFragment : Fragment() {

    private lateinit var gasCheckBinding: FragmentCheckGasBinding
    private lateinit var gasPatientViewModel: CheckGASViewModel
    private lateinit var gasPatientViewModelFactory : CheckGASPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    //    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1)

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
        gasCheckBinding = FragmentCheckGasBinding.inflate(inflater , container , false)
        gasPatientViewModel = CheckGASViewModel()
        gasPatientViewModelFactory = CheckGASPatientViewModelFactory()
        gasPatientViewModel =  ViewModelProvider(this, gasPatientViewModelFactory).get(
            CheckGASViewModel::class.java)
        gasCheckBinding.checkGASViewModel = gasPatientViewModel
        gasCheckBinding.lifecycleOwner = this
        return gasCheckBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gasPatientViewModel.passActivity(mainActivity)
        gasPatientViewModel.passFragment(this)
        gasPatientViewModel.initialiseRealm()

        gasCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

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
                    historyTest = gasPatientViewModel.fetchHistoryTest(patientId!! , testDate!!)
                }
            }
            if (historyTest.patientGASTestResult != null)
            {
                setPatientData(historyTest)
            }
        }
        else
        {
            if (openType == "updateLast")
            {
                gasPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                gasPatientViewModel.initialiseUserDummy()
//                bpiPatientViewModel.setPatientDataOnForm()
            }
            if (openType == "history")
            {
                gasPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        gasPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        gasPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        gasCheckBinding.clearBtn.setOnClickListener {

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

        gasCheckBinding.submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(gasCheckBinding.GAS1QRG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(gasCheckBinding.GAS2QRG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(gasCheckBinding.GAS3QRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(gasCheckBinding.GAS4QRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(gasCheckBinding.GAS5QRG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(gasCheckBinding.GAS6QRG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(gasCheckBinding.GAS7QRG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(gasCheckBinding.GAS8QRG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(gasCheckBinding.GAS9QRG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(gasCheckBinding.GAS10QRG)


            gasPatientViewModel.checkGASTestPatient(allPatientSelections)
        }

        gasCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(gasCheckBinding.includePopUpMenu.termsRelLayout , mainActivity, this,registerFragment , null ,leaderBoardFragment)

        gasCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        gasCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        gasCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        gasCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gasCheckBinding.GAS1QRG , test.patientGASQ1)
            setQuestionRadioGroup(gasCheckBinding.GAS2QRG , test.patientGASQ2)
            setQuestionRadioGroup(gasCheckBinding.GAS3QRG , test.patientGASQ3)
            setQuestionRadioGroup(gasCheckBinding.GAS4QRG , test.patientGASQ4)
            setQuestionRadioGroup(gasCheckBinding.GAS5QRG , test.patientGASQ5)
            setQuestionRadioGroup(gasCheckBinding.GAS6QRG , test.patientGASQ6)
            setQuestionRadioGroup(gasCheckBinding.GAS7QRG , test.patientGASQ7)
            setQuestionRadioGroup(gasCheckBinding.GAS8QRG , test.patientGASQ8)
            setQuestionRadioGroup(gasCheckBinding.GAS9QRG , test.patientGASQ9)
            setQuestionRadioGroup(gasCheckBinding.GAS10QRG , test.patientGASQ10)

        } , 1000)
    }

    fun initialisePatientData()
    {
        gasCheckBinding.GAS1QRG.clearCheck()
        gasCheckBinding.GAS2QRG.clearCheck()
        gasCheckBinding.GAS3QRG.clearCheck()
        gasCheckBinding.GAS4QRG.clearCheck()
        gasCheckBinding.GAS5QRG.clearCheck()
        gasCheckBinding.GAS6QRG.clearCheck()
        gasCheckBinding.GAS7QRG.clearCheck()
        gasCheckBinding.GAS8QRG.clearCheck()
        gasCheckBinding.GAS9QRG.clearCheck()
        gasCheckBinding.GAS10QRG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        when (answer) {
            null ->
            {

            }
            3 ->
            {
                (rg.getChildAt(3) as RadioButton).isChecked = true
            }
            2 ->
            {
                (rg.getChildAt(2
                ) as RadioButton).isChecked = true
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

    @SuppressLint("SuspiciousIndentation")
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
        gasCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 ->
            {
                gasCheckBinding.GAS1QRG.requestFocus()
            }

            2 ->
            {
                gasCheckBinding.GAS2QRG.requestFocus()
            }

            3 ->
            {
                gasCheckBinding.GAS3QRG.requestFocus()
            }

            4 ->
            {
                gasCheckBinding.GAS4QRG.requestFocus()
            }

            5 ->
            {
                gasCheckBinding.GAS5QRG.requestFocus()
            }

            6 ->
            {
                gasCheckBinding.GAS6QRG.requestFocus()
            }

            7 ->
            {
                gasCheckBinding.GAS7QRG.requestFocus()
            }

            8 ->
            {
                gasCheckBinding.GAS8QRG.requestFocus()
            }

            9 ->
            {
                gasCheckBinding.GAS9QRG.requestFocus()
            }

            10 ->
            {
                gasCheckBinding.GAS10QRG.requestFocus()
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
            GASCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}
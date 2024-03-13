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
import com.example.cvdriskestimator.databinding.FragmentCheckGas1Binding
import com.example.cvdriskestimator.databinding.FragmentCheckGas2Binding
import com.example.cvdriskestimator.viewModels.CheckGASPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGASViewModel
import java.util.*
import kotlin.collections.ArrayList

class GASCheckFragment2 : Fragment() {

    private lateinit var gasCheckBinding2: FragmentCheckGas2Binding
    private lateinit var gasCheckFragment1: GASCheckFragment1
    private lateinit var gasPatientViewModel: CheckGASViewModel
    private lateinit var gasPatientViewModelFactory : CheckGASPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    //    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1 ,1 ,1 ,1 ,1 ,1)
    private var allPatientSelectionsFr2 = arrayListOf<Int?>(1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1 ,1 ,1 ,1 ,1 ,1)
    private var answersFromSecondFragment : Boolean = false
    private var answersFromFirstFragment : Boolean = false
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
        gasCheckBinding2 = FragmentCheckGas2Binding.inflate(inflater , container , false)
        gasPatientViewModel = CheckGASViewModel()
        gasPatientViewModelFactory = CheckGASPatientViewModelFactory()
        gasPatientViewModel =  ViewModelProvider(this, gasPatientViewModelFactory).get(
            CheckGASViewModel::class.java)
        gasCheckBinding2.checkGASViewModel = gasPatientViewModel
        gasCheckBinding2.lifecycleOwner = this

        if (arguments!!.containsKey("fragment2Answers"))
        {
            answersFromSecondFragment = true
            allPatientSelectionsFr2 = arguments!!.getSerializable("fragment2Answers") as ArrayList<Int?>
        }

        if (arguments!!.containsKey("fragment1Answers"))
        {
            allPatientSelections = arguments!!.get("fragment1Answers") as ArrayList<Int?>
            answersFromFirstFragment = true
        }
        return gasCheckBinding2.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gasPatientViewModel.passActivity(mainActivity)
        gasPatientViewModel.passFragmen2(this)
        gasPatientViewModel.initialiseRealm()

        gasCheckBinding2.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")


        var patientId = this.requireArguments().getString("patientId")
        var testDate = this.requireArguments().getString("testDate" , "")
        var openType = this.requireArguments().getString("openType")

        if (answersFromSecondFragment == true)
        {
            setPatientSelections(allPatientSelectionsFr2)
        }
        else
        {
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
        }



        //set the observer for the patient mutable live data
        gasPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        gasPatientViewModel.testData.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        gasCheckBinding2.clearBtn.setOnClickListener {

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


        gasCheckBinding2.leftArrow.setOnClickListener {

            allPatientSelectionsFr2[0] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS16QRG)
            allPatientSelectionsFr2[1] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS17QRG)
            allPatientSelectionsFr2[2] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS18QRG)
            allPatientSelectionsFr2[3] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS19QRG)
            allPatientSelectionsFr2[4] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS20QRG)
            allPatientSelectionsFr2[5] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS21QRG)
            allPatientSelectionsFr2[6] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS22QRG)
            allPatientSelectionsFr2[7] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS23QRG)
            allPatientSelectionsFr2[8] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS24QRG)
            allPatientSelectionsFr2[9] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS25QRG)
            allPatientSelectionsFr2[10] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS26QRG)
            allPatientSelectionsFr2[11] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS27QRG)
            allPatientSelectionsFr2[12] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS28QRG)
            allPatientSelectionsFr2[13] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS29QRG)
            allPatientSelectionsFr2[14] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS30QRG)

            var bundle = Bundle()

            if (gasPatientViewModel.checkGASTestPatient1(allPatientSelections))
            {
                bundle.putSerializable("fragment1Answers" , allPatientSelections)
                bundle.putSerializable("fragment2Answers" , allPatientSelectionsFr2)
            }

            gasCheckFragment1 = GASCheckFragment1.newInstance(bundle)
            mainActivity.fragmentTransaction(gasCheckFragment1)
        }

        gasCheckBinding2.submitBtn.setOnClickListener {

            allPatientSelectionsFr2[0] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS16QRG)
            allPatientSelectionsFr2[1] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS17QRG)
            allPatientSelectionsFr2[2] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS18QRG)
            allPatientSelectionsFr2[3] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS19QRG)
            allPatientSelectionsFr2[4] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS20QRG)
            allPatientSelectionsFr2[5] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS21QRG)
            allPatientSelectionsFr2[6] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS22QRG)
            allPatientSelectionsFr2[7] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS23QRG)
            allPatientSelectionsFr2[8] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS24QRG)
            allPatientSelectionsFr2[9] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS25QRG)
            allPatientSelectionsFr2[10] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS26QRG)
            allPatientSelectionsFr2[11] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS27QRG)
            allPatientSelectionsFr2[12] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS28QRG)
            allPatientSelectionsFr2[13] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS29QRG)
            allPatientSelectionsFr2[14] = getAsnwerFromRadioGroup(gasCheckBinding2.GAS30QRG)

            var totalSelections = ArrayList<Int?>()
            totalSelections.addAll(allPatientSelections)
            totalSelections.addAll(allPatientSelectionsFr2)
            gasPatientViewModel.checkGASTestPatient2(totalSelections)
        }

        gasCheckBinding2.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        //set the PopUpMenu
        popupMenu = PopUpMenu(gasCheckBinding2.includePopUpMenu.termsRelLayout , mainActivity, this,registerFragment , null ,leaderBoardFragment)

        gasCheckBinding2.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        gasCheckBinding2.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        gasCheckBinding2.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        gasCheckBinding2.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun setPatientSelections(test : ArrayList<Int?>)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gasCheckBinding2.GAS16QRG , test[0])
            setQuestionRadioGroup(gasCheckBinding2.GAS17QRG , test[1])
            setQuestionRadioGroup(gasCheckBinding2.GAS18QRG , test[2])
            setQuestionRadioGroup(gasCheckBinding2.GAS19QRG , test[3])
            setQuestionRadioGroup(gasCheckBinding2.GAS20QRG , test[4])
            setQuestionRadioGroup(gasCheckBinding2.GAS21QRG , test[5])
            setQuestionRadioGroup(gasCheckBinding2.GAS22QRG , test[6])
            setQuestionRadioGroup(gasCheckBinding2.GAS23QRG , test[7])
            setQuestionRadioGroup(gasCheckBinding2.GAS24QRG , test[8])
            setQuestionRadioGroup(gasCheckBinding2.GAS25QRG , test[9])
            setQuestionRadioGroup(gasCheckBinding2.GAS26QRG , test[10])
            setQuestionRadioGroup(gasCheckBinding2.GAS27QRG , test[11])
            setQuestionRadioGroup(gasCheckBinding2.GAS28QRG , test[12])
            setQuestionRadioGroup(gasCheckBinding2.GAS29QRG , test[13])
            setQuestionRadioGroup(gasCheckBinding2.GAS30QRG , test[14])
        } , 1000)
    }
    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gasCheckBinding2.GAS16QRG , test.patientGASQ16)
            setQuestionRadioGroup(gasCheckBinding2.GAS17QRG , test.patientGASQ17)
            setQuestionRadioGroup(gasCheckBinding2.GAS18QRG , test.patientGASQ18)
            setQuestionRadioGroup(gasCheckBinding2.GAS19QRG , test.patientGASQ19)
            setQuestionRadioGroup(gasCheckBinding2.GAS20QRG , test.patientGASQ20)
            setQuestionRadioGroup(gasCheckBinding2.GAS21QRG , test.patientGASQ21)
            setQuestionRadioGroup(gasCheckBinding2.GAS22QRG , test.patientGASQ22)
            setQuestionRadioGroup(gasCheckBinding2.GAS23QRG , test.patientGASQ23)
            setQuestionRadioGroup(gasCheckBinding2.GAS24QRG , test.patientGASQ24)
            setQuestionRadioGroup(gasCheckBinding2.GAS25QRG , test.patientGASQ25)
            setQuestionRadioGroup(gasCheckBinding2.GAS26QRG , test.patientGASQ26)
            setQuestionRadioGroup(gasCheckBinding2.GAS27QRG , test.patientGASQ27)
            setQuestionRadioGroup(gasCheckBinding2.GAS28QRG , test.patientGASQ28)
            setQuestionRadioGroup(gasCheckBinding2.GAS29QRG , test.patientGASQ29)
            setQuestionRadioGroup(gasCheckBinding2.GAS30QRG , test.patientGASQ30)
        } , 1000)
    }

    fun initialisePatientData()
    {
        gasCheckBinding2.GAS16QRG.clearCheck()
        gasCheckBinding2.GAS17QRG.clearCheck()
        gasCheckBinding2.GAS18QRG.clearCheck()
        gasCheckBinding2.GAS19QRG.clearCheck()
        gasCheckBinding2.GAS20QRG.clearCheck()
        gasCheckBinding2.GAS21QRG.clearCheck()
        gasCheckBinding2.GAS22QRG.clearCheck()
        gasCheckBinding2.GAS23QRG.clearCheck()
        gasCheckBinding2.GAS24QRG.clearCheck()
        gasCheckBinding2.GAS25QRG.clearCheck()
        gasCheckBinding2.GAS26QRG.clearCheck()
        gasCheckBinding2.GAS27QRG.clearCheck()
        gasCheckBinding2.GAS28QRG.clearCheck()
        gasCheckBinding2.GAS29QRG.clearCheck()
        gasCheckBinding2.GAS30QRG.clearCheck()
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
        gasCheckBinding2.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String, questionNo :  Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            16 ->
            {
                gasCheckBinding2.GAS16QRG.requestFocus()
            }

            17 ->
            {
                gasCheckBinding2.GAS17QRG.requestFocus()
            }

            18 ->
            {
                gasCheckBinding2.GAS18QRG.requestFocus()
            }

            19 ->
            {
                gasCheckBinding2.GAS19QRG.requestFocus()
            }

            20 ->
            {
                gasCheckBinding2.GAS20QRG.requestFocus()
            }

            21 ->
            {
                gasCheckBinding2.GAS21QRG.requestFocus()
            }

            22 ->
            {
                gasCheckBinding2.GAS22QRG.requestFocus()
            }

            23 ->
            {
                gasCheckBinding2.GAS23QRG.requestFocus()
            }

            24 ->
            {
                gasCheckBinding2.GAS24QRG.requestFocus()
            }

            25 ->
            {
                gasCheckBinding2.GAS25QRG.requestFocus()
            }

            26 ->
            {
                gasCheckBinding2.GAS26QRG.requestFocus()
            }

            27 ->
            {
                gasCheckBinding2.GAS27QRG.requestFocus()
            }

            28->
            {
                gasCheckBinding2.GAS28QRG.requestFocus()
            }

            29 ->
            {
                gasCheckBinding2.GAS29QRG.requestFocus()
            }

            30 ->
            {
                gasCheckBinding2.GAS30QRG.requestFocus()
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
        fun newInstance(args : Bundle) =
            GASCheckFragment2().apply {
                arguments = Bundle().apply {
                }
            }
    }

}
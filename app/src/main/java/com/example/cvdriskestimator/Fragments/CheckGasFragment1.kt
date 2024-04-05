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
import com.example.cvdriskestimator.viewModels.CheckGASPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckGASViewModel
import java.util.*
import kotlin.collections.ArrayList

class GASCheckFragment1 : Fragment() {

    private lateinit var gasCheckBinding: FragmentCheckGas1Binding
    private lateinit var gasPatientViewModel: CheckGASViewModel
    private lateinit var gasPatientViewModelFactory : CheckGASPatientViewModelFactory
    private lateinit var mainActivity : MainActivity
    private var registerFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    //    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenu : PopUpMenu
    private var allPatientSelections = arrayListOf<Int?>(null, null ,null ,null ,null ,null ,null ,null ,null, null ,null ,null ,null ,null ,null)
    private var allPatientSelectionsFr2 = arrayListOf<Int?>(null, null ,null ,null ,null ,null ,null ,null ,null, null ,null ,null ,null ,null ,null)
    private var answersFromSecondFragment : Boolean = false
    private var answersFromFirstFragment : Boolean = false
    private lateinit var gasCheckFragment2: GASCheckFragment2

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
        gasCheckBinding = FragmentCheckGas1Binding.inflate(inflater , container , false)
        gasPatientViewModel = CheckGASViewModel()
        gasPatientViewModelFactory = CheckGASPatientViewModelFactory()
        gasPatientViewModel =  ViewModelProvider(this, gasPatientViewModelFactory).get(
            CheckGASViewModel::class.java)
        gasCheckBinding.checkGASViewModel = gasPatientViewModel
        gasCheckBinding.lifecycleOwner = this

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

        if (answersFromFirstFragment == true)
        {
            setPatientSelections(allPatientSelections)
        }
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

        gasCheckBinding.rightArrow.setOnClickListener {

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
            allPatientSelections[10] = getAsnwerFromRadioGroup(gasCheckBinding.GAS11QRG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(gasCheckBinding.GAS12QRG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(gasCheckBinding.GAS13QRG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(gasCheckBinding.GAS14QRG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(gasCheckBinding.GAS15QRG)

            var bundle = Bundle()
            if (gasPatientViewModel.checkGASTestPatient1(allPatientSelections))
           {
               bundle.putSerializable("fragment1Answers" , allPatientSelections)
               if (allPatientSelectionsFr2.get(0) != null)
                   bundle.putSerializable("fragment2Answers" , allPatientSelectionsFr2)
               gasCheckFragment2 = GASCheckFragment2.newInstance(bundle)
               mainActivity.fragmentTransaction(gasCheckFragment2)
           }


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

    private fun setPatientSelections(test : ArrayList<Int?>)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(gasCheckBinding.GAS1QRG , test[0])
            setQuestionRadioGroup(gasCheckBinding.GAS2QRG , test[1])
            setQuestionRadioGroup(gasCheckBinding.GAS3QRG , test[2])
            setQuestionRadioGroup(gasCheckBinding.GAS4QRG , test[3])
            setQuestionRadioGroup(gasCheckBinding.GAS5QRG , test[4])
            setQuestionRadioGroup(gasCheckBinding.GAS6QRG , test[5])
            setQuestionRadioGroup(gasCheckBinding.GAS7QRG , test[6])
            setQuestionRadioGroup(gasCheckBinding.GAS8QRG , test[7])
            setQuestionRadioGroup(gasCheckBinding.GAS9QRG , test[8])
            setQuestionRadioGroup(gasCheckBinding.GAS10QRG , test[9])
            setQuestionRadioGroup(gasCheckBinding.GAS11QRG , test[10])
            setQuestionRadioGroup(gasCheckBinding.GAS12QRG , test[11])
            setQuestionRadioGroup(gasCheckBinding.GAS13QRG , test[12])
            setQuestionRadioGroup(gasCheckBinding.GAS14QRG , test[13])
            setQuestionRadioGroup(gasCheckBinding.GAS15QRG , test[14])
        } , 1000)
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
            setQuestionRadioGroup(gasCheckBinding.GAS11QRG , test.patientGASQ11)
            setQuestionRadioGroup(gasCheckBinding.GAS12QRG , test.patientGASQ12)
            setQuestionRadioGroup(gasCheckBinding.GAS13QRG , test.patientGASQ13)
            setQuestionRadioGroup(gasCheckBinding.GAS14QRG , test.patientGASQ14)
            setQuestionRadioGroup(gasCheckBinding.GAS15QRG , test.patientGASQ15)
            allPatientSelectionsFr2.add(0 , test.patientGASQ16)
            allPatientSelectionsFr2.add(1 , test.patientGASQ17)
            allPatientSelectionsFr2.add(2 , test.patientGASQ18)
            allPatientSelectionsFr2.add(3 , test.patientGASQ19)
            allPatientSelectionsFr2.add(4 , test.patientGASQ20)
            allPatientSelectionsFr2.add(5 , test.patientGASQ21)
            allPatientSelectionsFr2.add(6 , test.patientGASQ22)
            allPatientSelectionsFr2.add(7 , test.patientGASQ23)
            allPatientSelectionsFr2.add(8 , test.patientGASQ24)
            allPatientSelectionsFr2.add(9 , test.patientGASQ25)
            allPatientSelectionsFr2.add(10 , test.patientGASQ26)
            allPatientSelectionsFr2.add(11 , test.patientGASQ27)
            allPatientSelectionsFr2.add(12 , test.patientGASQ28)
            allPatientSelectionsFr2.add(13 , test.patientGASQ29)
            allPatientSelectionsFr2.add(14 , test.patientGASQ30)


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
        gasCheckBinding.GAS11QRG.clearCheck()
        gasCheckBinding.GAS12QRG.clearCheck()
        gasCheckBinding.GAS13QRG.clearCheck()
        gasCheckBinding.GAS14QRG.clearCheck()
        gasCheckBinding.GAS15QRG.clearCheck()
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

            11 ->
            {
                gasCheckBinding.GAS11QRG.requestFocus()
            }

            12 ->
            {
                gasCheckBinding.GAS12QRG.requestFocus()
            }

            13 ->
            {
                gasCheckBinding.GAS13QRG.requestFocus()
            }

           14 ->
            {
                gasCheckBinding.GAS14QRG.requestFocus()
            }

            15 ->
            {
                gasCheckBinding.GAS15QRG.requestFocus()
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
            GASCheckFragment1().apply {
                arguments = Bundle().apply {
                    val fragment = GASCheckFragment1()
                    fragment.arguments = args
                    return fragment
                }
            }
    }

}
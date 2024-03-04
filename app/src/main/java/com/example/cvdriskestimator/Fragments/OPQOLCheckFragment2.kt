package com.example.cvdriskestimator.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.customClasses.XMLUtils
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2Binding
import com.example.cvdriskestimator.viewModels.CheckOPQOLPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class OPQOLCheckFragment2 : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var opqolCheckFragment: OPQOLCheckFragment
    private lateinit var opqolPatientViewModel: CheckOPQOLViewModel
    private lateinit var opqolCheckBinding2 : FragmentOPQOLCheck2Binding
    private var allPatientSelections =
        arrayListOf<Int?>( 1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1)
    private var allGeneratedRadioGroups = ArrayList<RadioGroup>(35)
    private var selectionsFromPreviousFragment = ArrayList<Int>(17)
    private var selectionsFromThisFragment = ArrayList<Int>(18)
    private var selectionsFromPreviousFragmentExists : Boolean = false
    private var selectionsFromThisFragmentExists : Boolean = false
    private lateinit var popupMenu: PopUpMenu

    //    // test components id
    private var allGeneratedRelativeLayoutIds2Fr = ArrayList<Int>(18)
    //    private var allGeneratedCatTxtViewsIds = ArrayList<Int>(36)
    private var allGeneratedTxtViewsIds2Fr = ArrayList<Int>(18)
    private var allGeneratedRadioGroupIds2Fr = ArrayList<Int>(18)
    private var allGeneratedRadioButtonIds2Fr = ArrayList<Int>(90)
    private var allGeneratedRadioGroups2Fr = ArrayList<RadioGroup>(18)
    var questionNoList = ArrayList<Int>(35)

    private var allGeneratedRelativeLayoutIds1Fr = ArrayList<Int>(17)
    //    private var allGeneratedCatTxtViewsIds = ArrayList<Int>(36)
    private var allGeneratedTxtViewsIds1Fr = ArrayList<Int>(17)
    private var allGeneratedRadioGroupIds1Fr = ArrayList<Int>(17)
    private var allGeneratedRadioButtonIds1Fr = ArrayList<Int>(85)
    private var allGeneratedRadioGroups1Fr = ArrayList<RadioGroup>(17)
    private lateinit var Fragment1Document : String
    private lateinit var Fragment2Document : String
    private lateinit var fragment1Doc : Document
    private lateinit var fragment2Doc : Document

    private var fragment2Set : Boolean = false
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

    @SuppressLint("ResourceType")
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
            if (it.containsKey("allGeneratedRelativeLayoutIds2Fr"))
            {
                allGeneratedRelativeLayoutIds2Fr = it.getSerializable("allGeneratedRelativeLayoutIds2Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedTxtViewsIds2Fr"))
            {
                allGeneratedTxtViewsIds2Fr = it.getSerializable("allGeneratedTxtViewsIds2Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroupIds2Fr"))
            {
                allGeneratedRadioGroupIds2Fr = it.getSerializable("allGeneratedRadioGroupIds2Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioButtonIds2Fr"))
            {
                allGeneratedRadioButtonIds2Fr = it.getSerializable("allGeneratedRadioButtonIds2Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroups2Fr"))
            {
                allGeneratedRadioGroups2Fr = it.getSerializable("allGeneratedRadioGroups2Fr") as ArrayList<RadioGroup>
            }
            if (it.containsKey("Questions"))
            {
                questionNoList = it.getSerializable("Questions") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRelativeLayoutIds1Fr"))
            {
                allGeneratedRelativeLayoutIds1Fr = it.getSerializable("allGeneratedRelativeLayoutIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedTxtViewsIds1Fr"))
            {
                allGeneratedTxtViewsIds1Fr = it.getSerializable("allGeneratedTxtViewsIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroupIds1Fr"))
            {
                allGeneratedRadioGroupIds1Fr = it.getSerializable("allGeneratedRadioGroupIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioButtonIds1Fr"))
            {
                allGeneratedRadioButtonIds1Fr = it.getSerializable("allGeneratedRadioButtonIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroups1Fr"))
            {
                allGeneratedRadioGroups1Fr = it.getSerializable("allGeneratedRadioGroups1Fr") as ArrayList<RadioGroup>
            }
            if (it.containsKey("Fragment1Document"))
            {
                Fragment1Document = it.getString("Fragment1Document")!!
            }
            if (it.containsKey("Fragment2Document"))
            {
                Fragment2Document = it.getString("Fragment2Document")!!
            }
        }

        if (!fragment2Set)
        {
            //randomise the elements of the xml layout
            val xmlResourceId = R.layout.fragment_o_p_q_o_l_check_2
            val inputStream: InputStream = resources.openRawResource(xmlResourceId)
            val components = parseComponents(inputStream).toMutableList()

            for (i in 17..35)
            {
                var temp = components.get(i)
                components[i] = components.get(questionNoList.get(i))
                components[questionNoList.get(i)] = temp
            }

            fragment2Doc = generateXmlDocument(components , questionNoList)

            for (i in 17..35)
            {
                var newRadioGroup = view!!.findViewById<RadioGroup>(allGeneratedRadioGroupIds2Fr.get(i))
                allGeneratedRadioGroups2Fr.add(newRadioGroup)
            }
            fragment2Set = true
        }
        if (fragment2Set)
            fragment2Doc = XMLUtils.parseXMLFromString(Fragment2Document)!!

        addXmlToView(view!! , Fragment2Document)

        return opqolCheckBinding2.root
    }

    @SuppressLint("ResourceType")
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
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(17))
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(18))
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(19))
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(20))
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(21))
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(22))
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(23))
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(24))
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(25))
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(26))
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(27))
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(28))
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(29))
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(30))
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(31))
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(32))
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(33))
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(34))

            if (opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections))
            {
                val args = Bundle()
                args.putSerializable("Answers2" , allPatientSelections)
                args.putSerializable("Answers" , selectionsFromPreviousFragment)
                args.putString("Fragment1Document" , convertDocumentToString(fragment1Doc!!))
                args.putString("Fragemnt2Document" , convertDocumentToString(fragment2Doc!!))
                args.putSerializable("allGeneratedRelativeLayoutIds2Fr" , allGeneratedRelativeLayoutIds2Fr)
                args.putSerializable("allGeneratedTxtViewsIds2Fr" , allGeneratedTxtViewsIds2Fr)
                args.putSerializable("allGeneratedRadioGroupIds2Fr" , allGeneratedRadioGroupIds2Fr)
                args.putSerializable("allGeneratedRadioButtonIds2Fr" , allGeneratedRadioButtonIds2Fr)
                args.putSerializable("allGeneratedRadioGroups2Fr" , allGeneratedRadioGroups2Fr)
                args.putBoolean("Fragment2Set" , true)
                args.putSerializable("Questions" , questionNoList)
                args.putSerializable("allGeneratedRelativeLayoutIds1Fr" , allGeneratedRelativeLayoutIds1Fr)
                args.putSerializable("allGeneratedTxtViewsIds1Fr" , allGeneratedTxtViewsIds1Fr)
                args.putSerializable("allGeneratedRadioGroupIds1Fr" , allGeneratedRadioGroupIds1Fr)
                args.putSerializable("allGeneratedRadioButtonIds1Fr" , allGeneratedRadioButtonIds1Fr)
                args.putSerializable("allGeneratedRadioGroups1Fr" , allGeneratedRadioGroups1Fr)
                args.putBoolean("Fragment1Set" , true)
                opqolCheckFragment = OPQOLCheckFragment.newInstance(args)
                mainActivity.fragmentTransaction(opqolCheckFragment)
            }
        }

        opqolCheckBinding2.submitBtn.setOnClickListener {
            allPatientSelections[0] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(17))
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(18))
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(19))
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(20))
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(21))
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(22))
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(23))
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(24))
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(25))
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(26))
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(27))
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(28))
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(29))
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(30))
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(31))
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(32))
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(33))
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(34))

            var thePatientSelections = ArrayList<Int>(36)

            for (i in 0..selectionsFromPreviousFragment.size -1)
            {
                thePatientSelections.add(i , selectionsFromPreviousFragment.get(i))
            }

            var counter = 17
            for (i in 0..allPatientSelections.size -1)
            {
                thePatientSelections.add(counter , allPatientSelections.get(i)!!)
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
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(0) , answers.get(0))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(1) , answers.get(1))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(2) , answers.get(2))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(3) , answers.get(3))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(4) , answers.get(4))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(5) , answers.get(5))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(6) , answers.get(6))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(7) , answers.get(7))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(8) , answers.get(8))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(9) , answers.get(9))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(10), answers.get(10))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(11) , answers.get(11))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(12) , answers.get(12))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(13) , answers.get(13))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(14) , answers.get(14))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(15) , answers.get(15))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(16) , answers.get(16))
            setQuestionRadioGroup(allGeneratedRadioGroups2Fr.get(17) , answers.get(17))

        } , 1000)
    }


    fun showSelectionError(error : String, qNo : Int) {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when (qNo) {
            19 -> allGeneratedRadioGroups2Fr.get(0).requestFocus()
            20 -> allGeneratedRadioGroups2Fr.get(1).requestFocus()
            21 -> allGeneratedRadioGroups2Fr.get(2).requestFocus()
            22 -> allGeneratedRadioGroups2Fr.get(3).requestFocus()
            23 -> allGeneratedRadioGroups2Fr.get(4).requestFocus()
            24 -> allGeneratedRadioGroups2Fr.get(5).requestFocus()
            25 -> allGeneratedRadioGroups2Fr.get(6).requestFocus()
            26 -> allGeneratedRadioGroups2Fr.get(7).requestFocus()
            27 -> allGeneratedRadioGroups2Fr.get(8).requestFocus()
            28 -> allGeneratedRadioGroups2Fr.get(9).requestFocus()
            29 -> allGeneratedRadioGroups2Fr.get(10).requestFocus()
            30 -> allGeneratedRadioGroups2Fr.get(11).requestFocus()
            31 -> allGeneratedRadioGroups2Fr.get(12).requestFocus()
            32 -> allGeneratedRadioGroups2Fr.get(13).requestFocus()
            33 -> allGeneratedRadioGroups2Fr.get(14).requestFocus()
            34 -> allGeneratedRadioGroups2Fr.get(15).requestFocus()
            35 -> allGeneratedRadioGroups2Fr.get(16).requestFocus()
            36 -> allGeneratedRadioGroups2Fr.get(17).requestFocus()
        }
    }

    private fun setQuestionRadioGroup(rg: RadioGroup, patientAnswer: Int) {

        if ((rg != allGeneratedRadioGroups2Fr.get(questionNoList.get(28)))
            && (rg != allGeneratedRadioGroups2Fr.get(questionNoList.get(32))))
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
        if ((opqolQ1RG != allGeneratedRadioGroups2Fr.get(questionNoList.get(28)))
            && (opqolQ1RG != allGeneratedRadioGroups2Fr.get(questionNoList.get(32))))
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

    //randomization and formation of xml document
    fun parseComponents(inputStream: InputStream): List<OPQOLCheckFragment.Component> {
        val components = mutableListOf<OPQOLCheckFragment.Component>()
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(inputStream)
        val nodeList = doc.getElementsByTagName("RelativeLayout")

        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)
            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = node as Element
                val relativeLayout = element.getElementsByTagName("RelativeLayout").item(0) as RelativeLayout
                val textView = element.getElementsByTagName("TextView").item(0) as TextView
                val radioGroup = element.getElementsByTagName("RadioGroup").item(0) as RadioGroup
                val radioButtons = mutableListOf<RadioButton>()
                val radioButtonList = element.getElementsByTagName("RadioButton")

                for (j in 0 until radioButtonList.length) {
                    val radioButtonNode = radioButtonList.item(j)
                    if (radioButtonNode.nodeType == Node.ELEMENT_NODE) {
                        val radioButton = radioButtonNode as RadioButton
                        radioButtons.add(radioButton)
                    }
                }

                components.add(
                    OPQOLCheckFragment.Component(
                        relativeLayout,
                        textView,
                        radioGroup,
                        radioButtons
                    )
                )
            }
        }

        return components
    }

    fun generateXmlDocument(components: List<OPQOLCheckFragment.Component>, qustionsNo : ArrayList<Int>): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.newDocument()

        val rootElement = doc.createElement("Components")
        doc.appendChild(rootElement)

        // Iterate over the shuffled list of components
        for (i in 0..components.size - 1) {

            val componentElement = doc.createElement("RelativeLayout")
            //set the attributes for the relative layout
//          Set layout width and height
            componentElement.setAttribute("android:layout_width", "match_parent")
            componentElement.setAttribute("android:layout_height", "wrap_content")

//          Set margin attributes
            componentElement.setAttribute("android:layout_marginLeft", "16dp")
            componentElement.setAttribute("android:layout_marginRight", "16dp")
            val id = View.generateViewId().toString()
            componentElement.setAttribute("android:id" , id)
            allGeneratedRelativeLayoutIds2Fr.add(id.toInt())
            componentElement.setAttribute("android:background" ,"@drawable/relativelayout_style" )

            if (i == 0)
            {
                componentElement.setAttribute("android:layout_below" , "@+id/opqoQ2cTextV")
            }
            else
            {
                componentElement.setAttribute("android:layout_below" , allGeneratedRelativeLayoutIds2Fr.get(-1).toString())
            }
            val textViewElement = doc.createElement("TextView")
            val textViewId = View.generateViewId()
            allGeneratedTxtViewsIds2Fr.add(textViewId)
            textViewElement.setAttribute("android:id" , "@id/${textViewId.toString()}")
            textViewElement.setAttribute("android:layout_width", "match_parent")
            textViewElement.setAttribute("android:layout_height", "wrap_content")
            textViewElement.setAttribute("android:background" , "@color/light_blue")
            textViewElement.setAttribute("android:focusable" , "true")
            textViewElement.setAttribute("android:focusableInTouchMode" , "true")
            textViewElement.setAttribute("android:gravity" , "center")
            textViewElement.setAttribute("android:minHeight" , "64dp")
            var textViewTextStringId : String =""
            when (qustionsNo.get(i))
            {
                0 -> textViewTextStringId = "@string/OPQOL35Q2Category1Q1"
                1 -> textViewTextStringId = "@string/OPQOL35Q2Category1Q2"
                2 -> textViewTextStringId = "@string/OPQOL35Q2Category1Q3"
                3 -> textViewTextStringId = "@string/OPQOL35Q2Category1Q4"
                4 -> textViewTextStringId = "@string/OPQOL35Q2Category2Q5"
                5 -> textViewTextStringId = "@string/OPQOL35Q2Category2Q6"
                6 -> textViewTextStringId = "@string/OPQOL35Q2Category2Q7"
                7 -> textViewTextStringId = "@string/OPQOL35Q2Category2Q8"
                8 -> textViewTextStringId = "@string/OPQOL35Q2Category3Q9"
                9 -> textViewTextStringId = "@string/OPQOL35Q2Category3Q10"
                10 -> textViewTextStringId = "@string/OPQOL35Q2Category3Q11"
                11 -> textViewTextStringId = "@string/OPQOL35Q2Category3Q12"
                12 -> textViewTextStringId = "@string/OPQOL35Q2Category3Q13"
                13 -> textViewTextStringId = "@string/OPQOL35Q2Category4Q14"
                14 -> textViewTextStringId = "@string/OPQOL35Q2Category4Q15"
                15 -> textViewTextStringId = "@string/OPQOL35Q2Category4Q16"
                16 -> textViewTextStringId = "@string/OPQOL35Q2Category4Q17"
                17 -> textViewTextStringId = "@string/OPQOL35Q2Category5Q18"
                18 -> textViewTextStringId = "@string/OPQOL35Q2Category5Q19"
                19 -> textViewTextStringId = "@string/OPQOL35Q2Category5Q20"
                20 -> textViewTextStringId = "@string/OPQOL35Q2Category5Q21"
                21 -> textViewTextStringId = "@string/OPQOL35Q2Category6Q22"
                22 -> textViewTextStringId = "@string/OPQOL35Q2Category6Q23"
                23 -> textViewTextStringId = "@string/OPQOL35Q2Category6Q24"
                24 -> textViewTextStringId = "@string/OPQOL35Q2Category6Q25"
                25 -> textViewTextStringId = "@string/OPQOL35Q2Category7Q26"
                26 -> textViewTextStringId = "@string/OPQOL35Q2Category7Q27"
                27 -> textViewTextStringId = "@string/OPQOL35Q2Category7Q28"
                28 -> textViewTextStringId = "@string/OPQOL35Q2Category7Q29"
                29 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q30"
                30 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q31"
                31 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q32"
                32 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q33"
                33 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q34"
                35 -> textViewTextStringId = "@string/OPQOL35Q2Category8Q35"
            }
            textViewElement.setAttribute("android:text" , textViewTextStringId)
            textViewElement.setAttribute("android:textColor" , "@color/black")
            textViewElement.setAttribute("android:textSize" , "20sp")
            textViewElement.textContent = components.get(i).textView.text.toString()
            componentElement.appendChild(textViewElement)

            val radioGroupElement = doc.createElement("RadioGroup")
            //set the attributes for the radioGroup
            val radioGroupId = View.generateViewId()
            allGeneratedRadioGroupIds2Fr.add(radioGroupId)
            radioGroupElement.setAttribute("android:id" , "@id/${radioGroupId.toString()}")
            radioGroupElement.setAttribute("android:layout_width", "match_parent")
            radioGroupElement.setAttribute("android:layout_height", "wrap_content")
            radioGroupElement.setAttribute("android:layout_below" , "@id/${allGeneratedTxtViewsIds2Fr.get(i)}")
            componentElement.appendChild(radioGroupElement)

            var radioBUttonCounter : Int = 0
            for (radioButton in components.get(i).radioButtons) {
                val radioButtonElement = doc.createElement("RadioButton")
                radioButtonElement.textContent = radioButton.text.toString()
                val radioButtonId = View.generateViewId()
                allGeneratedRadioButtonIds2Fr.add(radioButtonId)
                radioButtonElement.setAttribute("android:id" , "@id/${radioButtonId}")
                radioButtonElement.setAttribute("android:layout_width", "match_parent")
                radioButtonElement.setAttribute("android:layout_height", "wrap_content")
                radioButtonElement.setAttribute("android:layout_gravity" , "center|left")
                radioGroupElement.setAttribute("android:paddingLeft" , "8dp")
                radioGroupElement.setAttribute("android:paddingTop" , "8dp")
                radioGroupElement.setAttribute("android:textColor" , "@color/black")
                radioGroupElement.setAttribute("android:textSize" , "20sp")
                when (radioBUttonCounter)
                {
                    0 -> radioGroupElement.setAttribute("android:text" , "@string/OPQOL35Q2A1")
                    1 -> radioGroupElement.setAttribute("android:text" , "@string/OPQOL35Q2A2")
                    2 -> radioGroupElement.setAttribute("android:text" , "@string/OPQOL35Q2A3")
                    3 -> radioGroupElement.setAttribute("android:text" , "@string/OPQOL35Q2A4")
                }
                radioBUttonCounter ++
                radioGroupElement.appendChild(radioButtonElement)
            }

            rootElement.appendChild(componentElement)
        }

        return doc
    }

    private fun convertDocumentToString(document: Document): String? {
        return try {
            val transformerFactory = TransformerFactory.newInstance()
            val transformer: Transformer = transformerFactory.newTransformer()
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            val source = DOMSource(document)
            val writer = StringWriter()
            val result = StreamResult(writer)
            transformer.transform(source, result)
            writer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun addXmlToView(rootView: View, xmlString: String?) {
        if (xmlString == null) {
            Log.e("addXmlToView", "XML string is null")
            return
        }
        val parser = Xml.newPullParser()
        try {
            parser.setInput(StringReader(xmlString))
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    val tagName = parser.name
                    if ("TextView" == tagName) {
                        val text = parser.getAttributeValue(null, "text")
                        val textView = TextView(rootView.context)
                        textView.text = text
                        (rootView as ViewGroup).addView(textView)
                    }
                    // You can add more conditions for other types of views (e.g., RadioButton, etc.)
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
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
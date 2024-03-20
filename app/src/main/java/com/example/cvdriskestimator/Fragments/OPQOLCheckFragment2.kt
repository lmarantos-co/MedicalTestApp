package com.example.cvdriskestimator.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.util.Xml
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.customClasses.XMLUtils
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck1TemplateBinding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2Binding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2TemplateBinding
import com.example.cvdriskestimator.viewModels.CheckOPQOLPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
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
    private lateinit var opqolCheckBindingTemplateBinding: FragmentOPQOLCheck2TemplateBinding
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
    private var fragment2Randomized : Boolean = false
    private lateinit var Fragment1Document : String
    private lateinit var Fragment2Document : String
    private lateinit var fragment1Doc : Document
    private lateinit var fragment2Doc : Document
    private lateinit var components : ArrayList<OPQOLCheckFragment.Component>
    private var newComponents = ArrayList<OPQOLCheckFragment.Component>(35)
    private var negativeRadioGroups = ArrayList<RadioGroup>(3)


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

        opqolCheckBindingTemplateBinding =  FragmentOPQOLCheck2TemplateBinding.inflate(inflater , container , false)
        opqolPatientViewModel = CheckOPQOLViewModel()
        opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
            CheckOPQOLViewModel::class.java)
        opqolCheckBindingTemplateBinding.checkOQPOLPatientViewModel = opqolPatientViewModel
        opqolCheckBindingTemplateBinding.root

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
                fragment2Randomized = true
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

        return opqolCheckBindingTemplateBinding.root.rootView
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        opqolPatientViewModel.passActivity(mainActivity)
        opqolPatientViewModel.passFragment2(this)
        opqolPatientViewModel.initialiseRealm()

        opqolCheckBindingTemplateBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        if (!fragment2Randomized)
        {
            //randomise the elements of the xml layout
            val xmlResourceId = R.layout.fragment_o_p_q_o_l_check_2
//                var inputStream: InputStream = resources.openRawResource(xmlResourceId)
            val xmlParser = resources.getXml(xmlResourceId)
            //inputStream = inputStream.toString().replace("[^\\x20-\\x7e]", "").toInputStream()
//                components = parseComponents(inputStream).toMutableList()
            components = parseParserComponents(xmlParser)
            components.removeFirst()
            for (i in 0..16)
            {
                newComponents.add(i , components.get(i))
            }
            for (i in 0..17)
            {
                newComponents.add(i + 17 , components.get(i))
            }
            var counter : Int = 17
            var questionsCounter : Int = 17
            while (counter <= 34)
            {
                if ((questionNoList.get(questionsCounter) > 16) && (questionNoList.get(questionsCounter) < 35))
                {
                    var temp = newComponents.get(counter)
                    newComponents[counter] = newComponents.get(questionNoList.get(questionsCounter))
                    newComponents[questionNoList.get(questionsCounter)] = temp
                    counter++
                }
                else
                {
                    questionsCounter ++
                }
            }

            fragment2Doc = generateXmlDocument(newComponents , questionNoList)
            Fragment2Document = convertDocumentToString(fragment2Doc!!)!!


            fragment2Set = true
        }
        val parentViewGroup = view.findViewById<ViewGroup>(R.id.opqolCheckLinLayout)
        var ViewToAppendGeneratedLayout = parentViewGroup.findViewById<View>(R.id.opqolPillow_5)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

//        addXmlToView(parentViewGroup!! , fragment1Document)
        val parentView = ViewToAppendGeneratedLayout.parent as ViewGroup // Assuming the parent is a ViewGroup
        val index = parentView.indexOfChild(ViewToAppendGeneratedLayout)
        var viewToBeAdded = createViewFromXml(fragment2Doc, mainActivity.applicationContext)
        for (i in 0..viewToBeAdded.size -1)
        {
            parentView.addView(viewToBeAdded.get(i), index)
        }
        for (i in 0..17)
        {
            var newRadioGroup = view.findViewById<RelativeLayout>(allGeneratedRelativeLayoutIds2Fr.get(i)).findViewById<RadioGroup>(allGeneratedRadioGroupIds2Fr.get(i))
            allGeneratedRadioGroups.add(newRadioGroup)
        }
        //get the radiogroups associated with negative questions
        negativeRadioGroups = ArrayList<RadioGroup>(6)
        for (i in 0..16)
        {
            if (questionNoList.get(i) == 3)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
            if (questionNoList.get(i) == 5)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
            if (questionNoList.get(i) == 6)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
            if (questionNoList.get(i) == 15)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
            if (questionNoList.get(i) == 28)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
            if (questionNoList.get(i) == 32)
                negativeRadioGroups.add(allGeneratedRadioGroups.get(i))
        }

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


        opqolCheckBindingTemplateBinding.clearBtn.setOnClickListener {
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

        opqolCheckBindingTemplateBinding.leftArrow.setOnClickListener {

//            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)

            allPatientSelections[0] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(0))
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(1))
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(2))
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(3))
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(4))
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(5))
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(6))
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(7))
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(8))
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(9))
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(10))
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(11))
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(12))
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(13))
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(14))
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(15))
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(16))
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(17))

            if (opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections))
            {
                val args = Bundle()
                args.putSerializable("Answers2" , allPatientSelections)
                args.putSerializable("Answers" , selectionsFromPreviousFragment)
                args.putString("Fragment1Document" , Fragment1Document)
                args.putString("Fragemnt2Document" , Fragment2Document)
                args.putSerializable("allGeneratedRelativeLayoutIds2Fr" , allGeneratedRelativeLayoutIds2Fr)
                args.putSerializable("allGeneratedTxtViewsIds2Fr" , allGeneratedTxtViewsIds2Fr)
                args.putSerializable("allGeneratedRadioGroupIds2Fr" , allGeneratedRadioGroupIds2Fr)
                args.putSerializable("allGeneratedRadioButtonIds2Fr" , allGeneratedRadioButtonIds2Fr)
                args.putSerializable("allGeneratedRadioGroups2Fr" , allGeneratedRadioGroups)
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

        opqolCheckBindingTemplateBinding.submitBtn.setOnClickListener {
            allPatientSelections[0] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(0))
            allPatientSelections[1] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(1))
            allPatientSelections[2] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(2))
            allPatientSelections[3] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(3))
            allPatientSelections[4] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(4))
            allPatientSelections[5] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(5))
            allPatientSelections[6] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(6))
            allPatientSelections[7] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(7))
            allPatientSelections[8] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(8))
            allPatientSelections[9] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(9))
            allPatientSelections[10] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(10))
            allPatientSelections[11] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(11))
            allPatientSelections[12] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(12))
            allPatientSelections[13] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(13))
            allPatientSelections[14] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(14))
            allPatientSelections[15] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(15))
            allPatientSelections[16] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(16))
            allPatientSelections[17] =
                getAsnwerFromRadioGroup2(allGeneratedRadioGroups.get(17))

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


        opqolCheckBindingTemplateBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        loginPatientFragment = LoginPatientFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
        popupMenu = PopUpMenu(opqolCheckBindingTemplateBinding.includePopUpMenu.termsRelLayout , mainActivity, this,   registerFragment , null , leaderBoardFragment)

        opqolCheckBindingTemplateBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenu.showPopUp(it)
        }


        //listeners on form
        opqolCheckBindingTemplateBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBindingTemplateBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        opqolCheckBindingTemplateBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
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

        var positiveAnswer : Boolean = true
        for (radioGroup in negativeRadioGroups)
        {
            positiveAnswer = positiveAnswer && (rg != radioGroup)
        }
        if (positiveAnswer)
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
        var positiveAnswer : Boolean = true
        for (radioGroup in negativeRadioGroups)
        {
            positiveAnswer = positiveAnswer && (opqolQ1RG != radioGroup)
        }
        if (positiveAnswer)
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


    fun parseParserComponents(parser: XmlPullParser): ArrayList<OPQOLCheckFragment.Component> {
        val components = ArrayList<OPQOLCheckFragment.Component>()

        var eventType = parser.eventType
        var relativeLayout: RelativeLayout? = null
        var textView: TextView? = null
        var radioGroup: RadioGroup? = null
        val radioButtons = mutableListOf<RadioButton>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "RelativeLayout" -> relativeLayout = RelativeLayout(context)
                        "TextView" -> textView = TextView(context)
                        "RadioGroup" -> radioGroup = RadioGroup(context)
                        "RadioButton" -> {
                            val radioButton = RadioButton(context)
                            radioButtons.add(radioButton)
                            // If there's a RadioGroup, add the RadioButton to it
                            radioGroup?.addView(radioButton)
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "RelativeLayout" -> {
                            if (radioGroup != null) {
                                components.add(
                                    OPQOLCheckFragment.Component(
                                        relativeLayout!!,
                                        textView!!,
                                        radioGroup!!,
                                        radioButtons
                                    )
                                )
                                relativeLayout = null
                                textView = null
                                radioGroup = null
                                radioButtons.clear()
                            }
                        }
                    }
                }
            }
            eventType = parser.next()
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
        for (i in 17..components.size - 1) {

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
                componentElement.setAttribute("android:layout_below" , R.id.opqoQ2cTextV.toString())
            }
            else
            {
                componentElement.setAttribute("android:layout_below" , allGeneratedRelativeLayoutIds2Fr.get(i - 16 -1).toString())
            }
            val textViewElement = doc.createElement("TextView")
            val textViewId = View.generateViewId()
            allGeneratedTxtViewsIds2Fr.add(textViewId)
            textViewElement.setAttribute("android:id" , "${textViewId.toString()}")
            textViewElement.setAttribute("android:layout_width", "match_parent")
            textViewElement.setAttribute("android:layout_height", "wrap_content")
            textViewElement.setAttribute("android:background" , "@drawable/light_blue_textview")
            textViewElement.setAttribute("android:focusable" , "true")
            textViewElement.setAttribute("android:focusableInTouchMode" , "true")
            textViewElement.setAttribute("android:gravity" , "center")
            textViewElement.setAttribute("android:minHeight" , "64dp")
            var textViewTextStringId : Int = 0
            when (qustionsNo.get(i))
            {
                0 -> textViewTextStringId = R.string.OPQOL35Q2Category1Q1
                1 -> textViewTextStringId = R.string.OPQOL35Q2Category1Q2
                2 -> textViewTextStringId = R.string.OPQOL35Q2Category1Q3
                3 -> textViewTextStringId = R.string.OPQOL35Q2Category1Q4
                4 -> textViewTextStringId = R.string.OPQOL35Q2Category2Q5
                5 -> textViewTextStringId = R.string.OPQOL35Q2Category2Q6
                6 -> textViewTextStringId = R.string.OPQOL35Q2Category2Q7
                7 -> textViewTextStringId = R.string.OPQOL35Q2Category2Q8
                8 -> textViewTextStringId = R.string.OPQOL35Q2Category3Q9
                9 -> textViewTextStringId = R.string.OPQOL35Q2Category3Q10
                10 -> textViewTextStringId = R.string.OPQOL35Q2Category3Q11
                11 -> textViewTextStringId = R.string.OPQOL35Q2Category3Q12
                12 -> textViewTextStringId = R.string.OPQOL35Q2Category3Q13
                13 -> textViewTextStringId = R.string.OPQOL35Q2Category4Q14
                14 -> textViewTextStringId = R.string.OPQOL35Q2Category4Q15
                15 -> textViewTextStringId = R.string.OPQOL35Q2Category4Q16
                16 -> textViewTextStringId = R.string.OPQOL35Q2Category4Q17
                17 -> textViewTextStringId = R.string.OPQOL35Q2Category5Q18
                18 -> textViewTextStringId = R.string.OPQOL35Q2Category5Q19
                19 -> textViewTextStringId = R.string.OPQOL35Q2Category5Q20
                20 -> textViewTextStringId = R.string.OPQOL35Q2Category5Q21
                21 -> textViewTextStringId = R.string.OPQOL35Q2Category6Q22
                22 -> textViewTextStringId = R.string.OPQOL35Q2Category6Q23
                23 -> textViewTextStringId = R.string.OPQOL35Q2Category6Q24
                24 -> textViewTextStringId = R.string.OPQOL35Q2Category6Q25
                25 -> textViewTextStringId = R.string.OPQOL35Q2Category7Q26
                26 -> textViewTextStringId = R.string.OPQOL35Q2Category7Q27
                27 -> textViewTextStringId = R.string.OPQOL35Q2Category7Q28
                28 -> textViewTextStringId = R.string.OPQOL35Q2Category7Q29
                29 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q30
                30 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q31
                31 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q32
                32 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q33
                33 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q34
                34 -> textViewTextStringId = R.string.OPQOL35Q2Category8Q35
            }
            textViewElement.setAttribute("android:text" , getString(textViewTextStringId))
            textViewElement.setAttribute("android:textColor" , "@color/black")
            textViewElement.setAttribute("android:textSize" , "20sp")
            textViewElement.textContent = components.get(i - 17).textView.text.toString()
            componentElement.appendChild(textViewElement)

            val radioGroupElement = doc.createElement("RadioGroup")
            //set the attributes for the radioGroup
            val radioGroupId = View.generateViewId()
            allGeneratedRadioGroupIds2Fr.add(radioGroupId)
            radioGroupElement.setAttribute("android:id" , "${radioGroupId.toString()}")
            radioGroupElement.setAttribute("android:layout_width", "match_parent")
            radioGroupElement.setAttribute("android:layout_height", "wrap_content")
            radioGroupElement.setAttribute("android:layout_below" , "${allGeneratedTxtViewsIds2Fr.get(i -17)}")
            componentElement.appendChild(radioGroupElement)

            var allRadioButtons = ArrayList<RadioButton>(4)
            if (components.get(i).radioGroup.childCount >0)
            {
                for (k in 0..components.get(i).radioGroup.childCount -1)
                {
                    allRadioButtons.add(components.get(i).radioGroup.get(k) as RadioButton)
                }

                for (l in 0 until allRadioButtons.size) {
                    val radioButtonElement = doc.createElement("RadioButton")
                    radioButtonElement.textContent = allRadioButtons.get(l).text.toString()
                    val radioButtonId = View.generateViewId()
                    allGeneratedRadioButtonIds2Fr.add(radioButtonId)
                    radioButtonElement.setAttribute("android:id" , "${radioButtonId}")
                    radioButtonElement.setAttribute("android:layout_width", "match_parent")
                    radioButtonElement.setAttribute("android:layout_height", "wrap_content")
                    radioButtonElement.setAttribute("android:layout_gravity" , "center|left")
                    radioButtonElement.setAttribute("android:paddingLeft" , "8dp")
                    radioButtonElement.setAttribute("android:paddingTop" , "8dp")
                    radioButtonElement.setAttribute("android:textColor" , "@color/black")
                    radioButtonElement.setAttribute("android:textSize" , "20sp")
                    when (l)
                    {
                        0 -> radioButtonElement.setAttribute("android:text" , getString(R.string.OPQOL35Q2A1))
                        1 -> radioButtonElement.setAttribute("android:text" , getString(R.string.OPQOL35Q2A2))
                        2 -> radioButtonElement.setAttribute("android:text" , getString(R.string.OPQOL35Q2A3))
                        3 -> radioButtonElement.setAttribute("android:text" , getString(R.string.OPQOL35Q2A4))
                        4 -> radioButtonElement.setAttribute("android:text" , getString(R.string.OPQOL35Q2A5))
                    }
                    radioGroupElement.appendChild(radioButtonElement)
                }
                allRadioButtons.clear()
            }
            rootElement.appendChild(componentElement)
        }

        return doc
    }

//    private fun convertDocumentToString(document: Document): String? {
//        return try {
//            val transformerFactory = TransformerFactory.newInstance()
//            val transformer: Transformer = transformerFactory.newTransformer()
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
//            val source = DOMSource(document)
//            val writer = StringWriter()
//            val result = StreamResult(writer)
//            transformer.transform(source, result)
//            writer.toString()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }

    fun createViewFromXml(document: Document, context: Context): List<View> {
        val rootElement = document.documentElement ?: return emptyList()
        return createViewsFromXml(rootElement, context)
    }

    private fun createViewsFromXml(element: Element, context: Context): List<View> {
        val views = mutableListOf<View>()

        // Process the current element
        var view = createViewFromXmlElement(element, context)
        if (view != null) {
            view = applyProgrammaticallySetAttributes(element, view)!!
            views.add(view)
        }

        // Recursively process child elements
        val childNodes = element.childNodes
        for (i in 0 until childNodes.length) {
            val childNode = childNodes.item(i)
            if (childNode.nodeType == Node.ELEMENT_NODE) {
                val childElement = childNode as Element
                views.addAll(createViewsFromXml(childElement, context))
            }
        }

        return views
    }

    private fun applyProgrammaticallySetAttributes(element: Element, view: View) : View {
        val layoutParams = view.layoutParams ?: RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        if (layoutParams is RelativeLayout.LayoutParams) {
            layoutParams.width = parseDimension(element.getAttribute("android:layout_width"))
            layoutParams.height = parseDimension(element.getAttribute("android:layout_height"))
            layoutParams.leftMargin = parseDimension(element.getAttribute("android:layout_marginLeft"))
            layoutParams.rightMargin = parseDimension(element.getAttribute("android:layout_marginRight"))
            layoutParams.topMargin = parseDimension(element.getAttribute("android:layout_marginTop"))
            layoutParams.bottomMargin = parseDimension(element.getAttribute("android:layout_marginBottom"))
            layoutParams.marginStart = parseDimension(element.getAttribute("android:layout_marginStart"))
            layoutParams.marginEnd = parseDimension(element.getAttribute("android:layout_marginEnd"))
            layoutParams.setMargins(parseDimension(element.getAttribute("android:layout_marginLeft")) ,
                parseDimension(element.getAttribute("android:layout_marginTop")),
                parseDimension(element.getAttribute("android:layout_marginRight")) ,
                parseDimension(element.getAttribute("android:layout_marginBottom")))
        }
        val relativeLayoutParams = layoutParams as RelativeLayout.LayoutParams

        if (view is RelativeLayout)
        {
            view.background = parseDrawable(element.getAttribute("android:background") , context!!)
            view.id = element.getAttribute("android:id").substringAfter("/").toInt()
            val belowId = parseResource(element.getAttribute("android:layout_below"))
            if (belowId != 0) {
                relativeLayoutParams.addRule(RelativeLayout.BELOW, belowId)
            }
            view.layoutParams = relativeLayoutParams
            view.requestLayout()
        }

        if (view is TextView) {
            view.text = element.getAttribute("android:text")
            view.layoutParams = layoutParams
            view.background = parseDrawable(element.getAttribute("android:background") , context!!)
            view.setTextColor(parseColor(element.getAttribute("android:textColor")))
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, parseDimension(element.getAttribute("android:textSize")).toFloat())
            view.gravity = parseGravity(element.getAttribute("android:gravity"))
            view.isFocusable = element.getAttribute("android:focusable").toBoolean()
            view.isFocusableInTouchMode = element.getAttribute("android:focusableInTouchMode").toBoolean()
            view.minimumHeight = parseDimension(element.getAttribute("android:minHeight")).toInt()
            view.id = element.getAttribute("android:id").substringAfter("/").toInt()
        }

        if (view is RadioGroup) {
            // Set RadioGroup attributes here
//            val layoutParams = view.layoutParams as? RelativeLayout.LayoutParams
//            layoutParams?.let {
//                it.width = parseDimension(element.getAttribute("android:layout_width"))
//                it.height = parseDimension(element.getAttribute("android:layout_height"))
//                it.leftMargin = parseDimension(element.getAttribute("android:layout_marginLeft"))
//                it.rightMargin = parseDimension(element.getAttribute("android:layout_marginRight"))
//                it.topMargin = parseDimension(element.getAttribute("android:layout_marginTop"))
//                it.bottomMargin = parseDimension(element.getAttribute("android:layout_marginBottom"))
//                it.marginStart = parseDimension(element.getAttribute("android:layout_marginStart"))
//                it.marginEnd = parseDimension(element.getAttribute("android:layout_marginEnd"))
//            }
            val belowId = parseResource(element.getAttribute("android:layout_below"))
            if (belowId != 0) {
                relativeLayoutParams.addRule(RelativeLayout.BELOW, belowId)
            }
            view.id = element.getAttribute("android:id").substringAfter("/").toInt()
            view.layoutParams = relativeLayoutParams
            view.requestLayout()
        }

        if (view is RadioButton) {
            // Set radiobutton attributes here
//            val layoutParams = view.layoutParams as? RelativeLayout.LayoutParams
//            layoutParams?.let {
//                it.width = parseDimension(element.getAttribute("android:layout_width"))
//                it.height = parseDimension(element.getAttribute("android:layout_height"))
//                it.leftMargin = parseDimension(element.getAttribute("android:layout_marginLeft"))
//                it.rightMargin = parseDimension(element.getAttribute("android:layout_marginRight"))
//                it.topMargin = parseDimension(element.getAttribute("android:layout_marginTop"))
//                it.bottomMargin = parseDimension(element.getAttribute("android:layout_marginBottom"))
//                it.marginStart = parseDimension(element.getAttribute("android:layout_marginStart"))
//                it.marginEnd = parseDimension(element.getAttribute("android:layout_marginEnd"))
//            }
            view.text = element.getAttribute("android:text")
            view.id = element.getAttribute("android:id").substringAfter("/").toInt()
            view.gravity = parseGravity(element.getAttribute("android:gravity"))
            view.setPadding(parseDimension(element.getAttribute("paddingLeft")) , parseDimension(element.getAttribute("paddingTop")) , 0 , 0)
            // Add any specific attributes for RadioButton here
            view.layoutParams = relativeLayoutParams
            view.requestLayout()
        }

        if (view is RelativeLayout)
        {
            view.requestLayout()
        }
        else
        {
            view.requestLayout()
        }
        return view

    }

    private fun fix5RadioButtonOfQuestion1(radioButton: RadioButton) : RadioButton
    {
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        if (layoutParams is RelativeLayout.LayoutParams) {
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        }

        radioButton.layoutParams = layoutParams
        var id = View.generateViewId()
        radioButton.setId(id)
        radioButton.gravity = Gravity.LEFT or Gravity.CENTER
        radioButton.setText(R.string.OPQOL35Q2A5)
        radioButton.setTextColor(Color.BLACK)
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        radioButton.setPadding(8 , 8 , 0 , 0)

        return radioButton
    }

    private fun parseDimension(dimension: String): Int {
        return when (dimension) {
            "match_parent" -> ViewGroup.LayoutParams.MATCH_PARENT
            "wrap_content" -> ViewGroup.LayoutParams.WRAP_CONTENT
            else -> {
                // Assuming dimension is specified in dp
                if (dimension.endsWith("dp")) {
                    dimension.replace("dp", "").toInt()

                } else {
                    // Default to 0 if the dimension is not recognized
                    0
                }
                // Assuming dimension is specified in dp
                if (dimension.endsWith("sp")) {
                    dimension.replace("sp", "").toInt()

                } else {
                    // Default to 0 if the dimension is not recognized
                    0
                }
            }
        }
    }

    private fun parseDrawable(drawableString: String, context: Context): Drawable? {
        return when {
            drawableString.startsWith("@drawable/") -> {
                // If the drawableString starts with '@drawable/', it's a resource drawable
                val resId = context.resources.getIdentifier(drawableString.substring(10), "drawable", context.packageName)
                ContextCompat.getDrawable(context, resId)
            }
            else -> {
                // If it's not a resource drawable, try parsing it using other methods
                try {
                    // Parse the drawable using Drawable.createFromXml()
                    val xmlParser = XmlPullParserFactory.newInstance().newPullParser()
                    xmlParser.setInput(StringReader(drawableString))
                    Drawable.createFromXml(context.resources, xmlParser)
                } catch (e: Exception) {
                    // Log any exceptions and return null
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    private fun parseResource(resource: String): Int {
        // Check if the resource string starts with '@drawable/' indicating it's a drawable resource
        if (resource.startsWith("@drawable/")) {
            // Extract the drawable resource name
            val drawableName = resource.substring(10)
            // Get the resource ID of the drawable
            return context!!.resources.getIdentifier(drawableName, "drawable", context!!.packageName)
        }
        else
        {
            return resource.toInt()
        }
        // If the resource string doesn't start with '@drawable/', it might be another type of resource
        // Handle other types of resources as needed
        return 0 // Return a default value or handle the case based on your application's logic
    }

    private fun parseColor(color: String): Int {
        // Parse color string into color value
        // You may need to handle different types of color strings (e.g., #RRGGBB, @color/colorName)
        return if (color.startsWith("@color/")) {
            // It's a color resource, retrieve the color value
            val resId = context!!.resources.getIdentifier(color.substring(1), "color", context!!.packageName)
            ContextCompat.getColor(context!!, resId)
        } else {
            // Parse color string into color value
            Color.parseColor(color)
        }
    }

    private fun parseGravity(gravity: String): Int {
        // Parse gravity string into gravity value
        // You may need to handle different types of gravity strings
        // Here, we assume a single gravity value without '|' separator
        return when (gravity) {
            "center" -> Gravity.CENTER
            "center_horizontal" -> Gravity.CENTER_HORIZONTAL
            "center_vertical" -> Gravity.CENTER_VERTICAL
            // Add more cases as needed
            else -> Gravity.NO_GRAVITY
        }
    }
    private fun createViewFromXmlElement(element: Element, context: Context): View? {
        val tagName = element.tagName
        return when (tagName) {
            "RelativeLayout" -> createRelativeLayout(element, context)
            else -> null
        }
    }

    private fun createRelativeLayout(element: Element, context: Context): View? {
        val relativeLayout = RelativeLayout(context)

        val childNodes = element.childNodes
        for (i in 0 until childNodes.length) {
            val childNode = childNodes.item(i)
            if (childNode.nodeType == Node.ELEMENT_NODE) {
                val childElement = childNode as Element
                val childView = createViewFromRelativeLayoutChild(childElement, context)
                applyProgrammaticallySetAttributes(childElement , childView!!)
                if (childView != null) {
                    relativeLayout.addView(childView)
                }
            }
        }

        return relativeLayout
    }

    private fun createViewFromRelativeLayoutChild(element: Element, context: Context): View? {
        return when (element.tagName) {
            "TextView" -> createTextView(element, context)
            "RadioGroup" -> createRadioGroup(element, context)
            else -> null
        }
    }

    private fun createTextView(element: Element, context: Context): TextView {
        val textView = TextView(context)
        textView.text = element.textContent
        // Set other attributes if needed
        return textView
    }

    private fun createRadioGroup(element: Element, context: Context): RadioGroup {
        val radioGroup = RadioGroup(context)

        val childNodes = element.childNodes
        for (i in 0 until childNodes.length) {
            val childNode = childNodes.item(i)
            if (childNode.nodeType == Node.ELEMENT_NODE && childNode.nodeName == "RadioButton") {
                val radioButton = createRadioButton(childNode as Element, context)
                if (radioButton != null) {
                    applyProgrammaticallySetAttributes(childNode as Element, radioButton)
                    radioGroup.addView(radioButton)
                }
            }
        }

        // Set other attributes if needed
        return radioGroup
    }

    private fun createRadioButton(element: Element, context: Context): RadioButton? {
        val radioButton = RadioButton(context)
        radioButton.text = element.textContent
        // Set other attributes if needed
        return radioButton



    }

    fun convertDocumentToString(document: Document): String {
        val transformerFactory = TransformerFactory.newInstance()
        val transformer = transformerFactory.newTransformer()
        val writer = StringWriter()
        transformer.transform(DOMSource(document), StreamResult(writer))
        return writer.toString()
    }

    fun convertStringToDocument(documentString: String?): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        return builder.parse(InputSource(StringReader(documentString)))
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
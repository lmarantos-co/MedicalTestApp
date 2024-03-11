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
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.customClasses.XMLUtils
import com.example.cvdriskestimator.databinding.FragmentBAICheckBinding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck1Binding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck1TemplateBinding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2Binding
import com.example.cvdriskestimator.viewModels.CheckOPQOLPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
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


class OPQOLCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var opqolPatientViewModel: CheckOPQOLViewModel
    private lateinit var opqolCheckBinding : FragmentOPQOLCheck1Binding
    private lateinit var opqolCheckBindingTemplateBinding: FragmentOPQOLCheck1TemplateBinding
    private lateinit var opqolCheckBinding2 : FragmentOPQOLCheck2Binding
    private lateinit var thisFragmentPatientSelections : ArrayList<Int?>
    private var thisFragmentSelectionsExists : Boolean = false
    private lateinit var otherFragmentPatientSelections : ArrayList<Int>
    private var otherFragmentSelectionsExists : Boolean = false
    private var allPatientSelections =
    arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1)
    private var fragment2AnswersFromPreviousTest = ArrayList<Int>(18)
    private var answersFromPreviousTest : Boolean = false
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginPatientFragment: LoginPatientFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment
    private lateinit var opqolCheckFragment2: OPQOLCheckFragment2
    private lateinit var formLinearlayout : LinearLayout
//    private var allGeneratedRelativeViews = ArrayList<RelativeLayout>(36)
//    private var allGeneratedCategoryTxtViews = ArrayList<TextView>(7)
//    private var allGeneratedQuestionTxtViews = ArrayList<TextView>(36)
//    private var allGeneratedTxtViewsstringResources = ArrayList<Int>(36)
//    private var allGeneratedQuestionRadioGroups = ArrayList<RadioGroup>(36)
//    private var allGeneratedRadioButtons = ArrayList<RadioButton>(180)
//
//    // test components id
    private var allGeneratedRelativeLayoutIds = ArrayList<Int>(17)
//    private var allGeneratedCatTxtViewsIds = ArrayList<Int>(36)
    private var fragment1Randomized : Boolean = false
    private var fragment2Randomized : Boolean = false
    private lateinit var components : ArrayList<Component>
    private var allGeneratedTxtViewsIds = ArrayList<Int>(17)
    private var allGeneratedRadioGroupIds = ArrayList<Int>(17)
    private var allGeneratedRadioButtonIds = ArrayList<Int>(85)
    private var allGeneratedRadioGroups = ArrayList<RadioGroup>(17)
    var questionNoList = ArrayList<Int>(35)
    private var allGeneratedRelativeLayoutIds2Fr = ArrayList<Int>(18)
    //    private var allGeneratedCatTxtViewsIds = ArrayList<Int>(36)
    private var allGeneratedTxtViewsIds2Fr = ArrayList<Int>(18)
    private var allGeneratedRadioGroupIds2Fr = ArrayList<Int>(18)
    private var allGeneratedRadioButtonIds2Fr = ArrayList<Int>(90)
    private var allGeneratedRadioGroups2Fr = ArrayList<RadioGroup>(18)
    private var negativeRadioGroups = ArrayList<RadioGroup>(3)
    private var Fragment1Set : Boolean = false
    private var Fragment2Set : Boolean = false
    private lateinit var fragment1Document : String
    private lateinit var fragment2Document : String
    private lateinit var fragment1Doc : Document
    private lateinit var fragment2Doc : Document
    private var useLayout1 : Boolean = true
    private var firstLayoutCompleted : Boolean = false
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
        opqolCheckBindingTemplateBinding = FragmentOPQOLCheck1TemplateBinding.inflate(inflater , container , false)
        opqolPatientViewModel = CheckOPQOLViewModel()
        opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
            CheckOPQOLViewModel::class.java)
        opqolCheckBindingTemplateBinding.checkOQPOLPatientViewModel = opqolPatientViewModel
        opqolCheckBindingTemplateBinding.lifecycleOwner = this
        arguments?.let {
            if (it.containsKey("Answers")) {
                thisFragmentPatientSelections = it.getSerializable("Answers") as ArrayList<Int?>
                thisFragmentSelectionsExists = true
                if (it.containsKey("Answers2"))
                {
                    otherFragmentPatientSelections =
                        it.getSerializable("Answers2") as ArrayList<Int>
                    otherFragmentSelectionsExists = true
                }
            }
            if (it.containsKey("Fragment1Document"))
            {
                fragment1Document = it.getString("Fragment1Document")!!
                fragment1Randomized = true
            }
            if (it.containsKey("Fragemnt2Document"))
            {
                fragment2Document = it.getString("Fragemnt2Document")!!
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
                allGeneratedRelativeLayoutIds = it.getSerializable("allGeneratedRelativeLayoutIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedTxtViewsIds1Fr"))
            {
                allGeneratedTxtViewsIds = it.getSerializable("allGeneratedTxtViewsIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroupIds1Fr"))
            {
                allGeneratedRadioGroupIds = it.getSerializable("allGeneratedRadioGroupIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioButtonIds1Fr"))
            {
                allGeneratedRadioButtonIds = it.getSerializable("allGeneratedRadioButtonIds1Fr") as ArrayList<Int>
            }
            if (it.containsKey("allGeneratedRadioGroups1Fr"))
            {
                allGeneratedRadioGroups = it.getSerializable("allGeneratedRadioGroups1Fr") as ArrayList<RadioGroup>
            }

        }
        return opqolCheckBindingTemplateBinding.root.rootView
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!fragment1Randomized)
        {
            //randomise the elements of the xml layout
            val xmlResourceId = R.layout.fragment_o_p_q_o_l_check_1
//                var inputStream: InputStream = resources.openRawResource(xmlResourceId)
            val xmlParser = resources.getXml(xmlResourceId)
            //inputStream = inputStream.toString().replace("[^\\x20-\\x7e]", "").toInputStream()
//                components = parseComponents(inputStream).toMutableList()
            components = parseParserComponents(xmlParser)
            var extraRadioButton = RadioButton(mainActivity.applicationContext)
            extraRadioButton = fix5RadioButtonOfQuestion1(extraRadioButton)
            components.get(0).radioGroup.addView(extraRadioButton)
            components.removeFirst()
            // Shuffle the list of components
//        val shuffledComponents = components.shuffled()
            questionNoList = ArrayList<Int>(35)
            for (i in 0..34)
            {
                questionNoList.add(i)
            }
            questionNoList.shuffle()
            var counter : Int = 0
            var questionsCounter : Int = 0
            while ((counter <= 16) && (questionsCounter < 35))
            {

                if (questionNoList.get(questionsCounter) <= 16)
                {
                    var temp = components.get(counter)
                    components[counter] = components.get(questionNoList.get(questionsCounter))
                    components[questionNoList.get(questionsCounter)] = temp
                    counter ++
                }
                else
                {
                    questionsCounter ++
                }

            }
            fragment1Doc = generateXmlDocument(components , questionNoList)
            fragment1Document = convertDocumentToString(fragment1Doc!!)!!

            fragment1Randomized = true
        }
//        if (fragment1Randomized)
//            fragment1Doc = XMLUtils.parseXMLFromString(fragment1Document)!!
        val parentViewGroup = view.findViewById<ViewGroup>(R.id.opqolCheckLinLayout)
        var ViewToAppendGeneratedLayout = parentViewGroup.findViewById<View>(R.id.opqolPillow_5)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

//        addXmlToView(parentViewGroup!! , fragment1Document)
        val parentView = ViewToAppendGeneratedLayout.parent as ViewGroup // Assuming the parent is a ViewGroup
        val index = parentView.indexOfChild(ViewToAppendGeneratedLayout)
        var viewToBeAdded = createViewFromXml(convertStringToDocument(fragment1Document), mainActivity.applicationContext)
        for (i in 0..viewToBeAdded.size -1)
        {
            parentView.addView(viewToBeAdded.get(i), index)
        }
        for (i in 0..16)
        {
            var newRadioGroup = view.findViewById<RelativeLayout>(allGeneratedRelativeLayoutIds.get(i)).findViewById<RadioGroup>(allGeneratedRadioGroupIds.get(i))
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
        // Step 5: Inflate the modified layout in the fragment
//        parentViewGroup.addView(inflateXmlLayout(mainActivity.applicationContext , fragment1Doc))
        opqolPatientViewModel.passActivity(mainActivity)
        opqolPatientViewModel.passFragment(this)
        opqolPatientViewModel.initialiseRealm()


        opqolCheckBindingTemplateBinding.includeCvdTitleForm.userIcon.alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.requireArguments().getString("patientId")
        var testDate = this.requireArguments().getString("testDate" , "")
        var openType = this.requireArguments().getString("openType")

        if (thisFragmentSelectionsExists == true)
        {
            setPatientSelections(thisFragmentPatientSelections)
        }
        else
        {
            if (openType == "open_history")
            {
                openHistory = true
//                historyTest = Test()
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
                if (historyTest.OPQOLTestReesult != null)
                {
                    setPatientSelectionsForFragment2(historyTest)
                    setPatientData1(historyTest)
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
        }
//        if (firstLayoutCompleted)
//        {
//            var newTest = Test()
//            newTest.patientOPQOLQ1 = allPatientSelections.get(0)
//            newTest.patientOPQOLQ2 = allPatientSelections.get(1)
//            newTest.patientOPQOLQ3 = allPatientSelections.get(2)
//            newTest.patientOPQOLQ4 = allPatientSelections.get(3)
//            newTest.patientOPQOLQ5 = allPatientSelections.get(4)
//            newTest.patientOPQOLQ6 = allPatientSelections.get(5)
//            newTest.patientOPQOLQ7 = allPatientSelections.get(6)
//            newTest.patientOPQOLQ8 = allPatientSelections.get(7)
//            newTest.patientOPQOLQ9 = allPatientSelections.get(8)
//            newTest.patientOPQOLQ10 = allPatientSelections.get(9)
//            newTest.patientOPQOLQ11= allPatientSelections.get(10)
//            newTest.patientOPQOLQ12 = allPatientSelections.get(11)
//            newTest.patientOPQOLQ13 = allPatientSelections.get(12)
//            newTest.patientOPQOLQ14 = allPatientSelections.get(13)
//            newTest.patientOPQOLQ15 = allPatientSelections.get(14)
//            newTest.patientOPQOLQ16 = allPatientSelections.get(15)
//            newTest.patientOPQOLQ17 = allPatientSelections.get(16)
//            setPatientData1(newTest)
//        }


        //set the observer for the patient mutable live data
        opqolPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        opqolPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
            {
                if (useLayout1)
                    patientTest = it
                setPatientSelectionsForFragment2(it)
                setPatientData1(it)
            }
        }

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

        //  generate the questionaire components for the for the ui
//        for (layout in allGeneratedRelativeViews)
//        {
//            layout.id = View.generateViewId()
//        }
//
//        for (catTxtView in allGeneratedCategoryTxtViews)
//        {
//            catTxtView.id = View.generateViewId()
//        }
//
//        for (textView in allGeneratedQuestionTxtViews)
//        {
//            textView.id = View.generateViewId()
//        }
//
//        for (rb in allGeneratedRadioButtons)
//        {
//            rb.id = View.generateViewId()
//        }
//
//        for (layout in allGeneratedRelativeViews)
//        {
//            allGeneratedRelativeLayoutIds.add(layout.id)
//        }
//
//        for (catTextView in allGeneratedCategoryTxtViews)
//        {
//            allGeneratedCatTxtViewsIds.add(catTextView.id)
//        }
//
//        for (textView in allGeneratedQuestionTxtViews)
//        {
//            allGeneratedTxtViewsIds.add(textView.id)
//        }
//
//        for (rb in allGeneratedRadioButtons)
//        {
//            allGeneratedRadioButtonIds.add(rb.id)
//        }
//
//        formLinearlayout = view.findViewById(R.id.opqolCheckLinLayout)
//        // Set orientation of the LinearLayout to vertical
//        formLinearlayout.setOrientation(LinearLayout.VERTICAL);
//
//        populateAllTextViewsStringResources()
//
//        //generate the component ui programmatically
//        for (i in 0..allGeneratedRelativeViews.size - 1)
//        {
//
//            if (i == 0)
//            {
//                // Create layout parameters for the TextView indicating it should be placed below the previous view
//                // Create layout parameters for the TextView indicating it should be placed below the previous view
//                val layoutParams1 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category1 , allGeneratedCategoryTxtViews.get(0)), layoutParams1)
//            }
//            if (i == 4)
//            {
//                val layoutParams2 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category2 , allGeneratedCategoryTxtViews.get(1)) , layoutParams2)
//            }
//            if (i == 8)
//            {
//                val layoutParams3 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category3 , allGeneratedCategoryTxtViews.get(2)) , layoutParams3)
//            }
//            if (i == 13)
//            {
//                val layoutParams4 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category4 , allGeneratedCategoryTxtViews.get(3)), layoutParams4)
//            }
//            if (i == 17)
//            {
//                val layoutParams5 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category5 , allGeneratedCategoryTxtViews.get(4)) , layoutParams5)
//            }
//            if (i == 21)
//            {
//                val layoutParams6 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category6 , allGeneratedCategoryTxtViews.get(5)) , layoutParams6)
//            }
//            if (i == 25)
//            {
//                val layoutParams7 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category7 , allGeneratedCategoryTxtViews.get(6)) , layoutParams7)
//            }
//            if (i == 29)
//            {
//                val layoutParams8 = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
//                )
//                formLinearlayout.addView(generateHeaderTextView(R.string.OPQOL35Q2Category8 , allGeneratedCategoryTxtViews.get(7)) , layoutParams8)
//            }
//            val layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,  // Width
//                LinearLayout.LayoutParams.WRAP_CONTENT // Height
//            )
//            formLinearlayout.addView(generateRelativeLayout(
//                layout = allGeneratedRelativeViews.get(i),
//                txtView = allGeneratedQuestionTxtViews.get(i),
//                radioGroup = allGeneratedQuestionRadioGroups.get(i),
//                rb1 = allGeneratedRadioButtons.get(0 *(i + 1)),
//                rb2 = allGeneratedRadioButtons.get(1 * (i + 1)),
//                rb3 = allGeneratedRadioButtons.get(2 * (i + 1)),
//                rb4 = allGeneratedRadioButtons.get(3 * (i + 1)),
//                rb5 = allGeneratedRadioButtons.get(4 * (i + 1)),
//                txtViewStringResource = allGeneratedTxtViewsstringResources.get(i),
//                radioButton1StringResource = R.string.OPQOL35Q2A1,
//                radioButton2StringResource = R.string.OPQOL35Q2A2,
//                radioButton3StringResource = R.string.OPQOL35Q2A3,
//                radioButton4StringResource = R.string.OPQOL35Q2A4,
//                radiobutton5StringResource = R.string.OPQOL35Q2A5
//                ) , layoutParams)
//
//        }

        opqolCheckBindingTemplateBinding.rightArrow.setOnClickListener {

//            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)
            allPatientSelections[0] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(0))
            allPatientSelections[1] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(1))
            allPatientSelections[2] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(2))
            allPatientSelections[3] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(3))
            allPatientSelections[4] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(4))
            allPatientSelections[5] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(5))
            allPatientSelections[6] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(6))
            allPatientSelections[7] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(7))
            allPatientSelections[8] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(8))
            allPatientSelections[9] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(9))
            allPatientSelections[10] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(10))
            allPatientSelections[11] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(11))
            allPatientSelections[12] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(12))
            allPatientSelections[13] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(13))
            allPatientSelections[14] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(14))
            allPatientSelections[15] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(15))
            allPatientSelections[16] = getAsnwerFromRadioGroup1(allGeneratedRadioGroups.get(16))
            thisFragmentPatientSelections = allPatientSelections

            if (opqolPatientViewModel.checkOPQOLTestPatient1(allPatientSelections))
            {
                var args = Bundle()
                if (answersFromPreviousTest)
                {
                    args.putSerializable("Answers2" , fragment2AnswersFromPreviousTest)
                }
                if (otherFragmentSelectionsExists)
                {
                    args.putSerializable("Answers2" , otherFragmentPatientSelections)
                    args.putBoolean("Fragment2Set" , true)
                    args.putSerializable("allGeneratedRelativeLayoutIds2Fr" , allGeneratedRelativeLayoutIds2Fr)
                    args.putSerializable("allGeneratedTxtViewsIds2Fr" , allGeneratedTxtViewsIds2Fr)
                    args.putSerializable("allGeneratedRadioGroupIds2Fr" , allGeneratedRadioGroupIds2Fr)
                    args.putSerializable("allGeneratedRadioButtonIds2Fr" , allGeneratedRadioButtonIds2Fr)
                    args.putSerializable("allGeneratedRadioGroups2Fr" , allGeneratedRadioGroups2Fr)
                    args.putSerializable("Fragemnt2Document" , convertDocumentToString(fragment2Doc))
                }

                args.putSerializable("Answers" , thisFragmentPatientSelections)
                args.putBoolean("Fragment1Set" , true)
                args.putSerializable("Questions" , questionNoList)
                args.putSerializable("allGeneratedRelativeLayoutIds1Fr" , allGeneratedRelativeLayoutIds)
                args.putSerializable("allGeneratedTxtViewsIds1Fr" , allGeneratedTxtViewsIds)
                args.putSerializable("allGeneratedRadioGroups1Fr" , allGeneratedRadioGroups)
                args.putSerializable("allGeneratedRadioGroupIds1Fr" , allGeneratedRadioGroupIds)
                args.putSerializable("allGeneratedRadioButtonIds1Fr" , allGeneratedRadioButtonIds)
                args.putSerializable("Fragment1Document" , convertDocumentToString(fragment1Doc))
                opqolCheckFragment2 = OPQOLCheckFragment2.newInstance(args)
                mainActivity.fragmentTransaction(opqolCheckFragment2)
            }

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

    private fun setPatientSelectionsForFragment2(it : Test) {
        fragment2AnswersFromPreviousTest[0] = it.patientOPQOLQ18!!
        fragment2AnswersFromPreviousTest[1] = it.patientOPQOLQ19!!
        fragment2AnswersFromPreviousTest[2] = it.patientOPQOLQ20!!
        fragment2AnswersFromPreviousTest[3] = it.patientOPQOLQ21!!
        fragment2AnswersFromPreviousTest[4] = it.patientOPQOLQ22!!
        fragment2AnswersFromPreviousTest[5] = it.patientOPQOLQ23!!
        fragment2AnswersFromPreviousTest[6] = it.patientOPQOLQ24!!
        fragment2AnswersFromPreviousTest[7] = it.patientOPQOLQ25!!
        fragment2AnswersFromPreviousTest[8] = it.patientOPQOLQ26!!
        fragment2AnswersFromPreviousTest[9] = it.patientOPQOLQ27!!
        fragment2AnswersFromPreviousTest[10] = it.patientOPQOLQ28!!
        fragment2AnswersFromPreviousTest[11] = it.patientOPQOLQ29!!
        fragment2AnswersFromPreviousTest[12] = it.patientOPQOLQ30!!
        fragment2AnswersFromPreviousTest[13] = it.patientOPQOLQ31!!
        fragment2AnswersFromPreviousTest[14] = it.patientOPQOLQ32!!
        fragment2AnswersFromPreviousTest[15] = it.patientOPQOLQ33!!
        fragment2AnswersFromPreviousTest[16] = it.patientOPQOLQ34!!
        fragment2AnswersFromPreviousTest[17] = it.patientOPQOLQ35!!
        answersFromPreviousTest = true
    }

//    private fun populateAllTextViewsStringResources()
//    {
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category1Q1)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category1Q2)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category1Q3)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category1Q4)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category2Q5)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category2Q6)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category2Q7)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category2Q8)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category3Q10)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category3Q11)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category3Q12)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category3Q13)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category4Q14)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category4Q15)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category4Q16)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category4Q17)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category5Q18)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category5Q19)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category5Q20)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category5Q21)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category6Q22)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category6Q23)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category6Q24)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category6Q25)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category7Q26)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category7Q27)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category7Q28)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category7Q29)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q30)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q31)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q32)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q33)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q34)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q35)
//        allGeneratedTxtViewsstringResources.add(R.string.OPQOL35Q2Category8Q36)
//    }

//    private fun generateHeaderTextView(txtString : Int, catTxtView : TextView): TextView
//    {
//        // Create layout parameters for the TextView
//        val layoutParams = catTxtView.layoutParams as (ViewGroup.MarginLayoutParams)
//        layoutParams.setMargins(16 , 0 , 16 , 0)
//        catTxtView.background = resources.getDrawable(R.color.light_blue)
//        catTxtView.isFocusable = true
//        catTxtView.isFocusableInTouchMode = true
//        catTxtView.gravity = Gravity.CENTER
//        catTxtView.minHeight = 64
//        catTxtView.text = resources.getString(txtString)
//        catTxtView.setTextColor(resources.getColor(R.color.black))
//        catTxtView.setTextSize(20f)
//        return catTxtView
//    }
//
//
//    private fun generateRelativeLayout(
//        layout: RelativeLayout,
//        txtView : TextView,
//        radioGroup : RadioGroup,
//        rb1 : RadioButton,
//        rb2 : RadioButton,
//        rb3 : RadioButton,
//        rb4 : RadioButton,
//        rb5 : RadioButton,
//        txtViewStringResource : Int,
//        radioButton1StringResource : Int,
//        radioButton2StringResource : Int,
//        radioButton3StringResource : Int,
//        radioButton4StringResource : Int,
//        radiobutton5StringResource : Int
//    ) : RelativeLayout
//    {
//        var relLayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT , RelativeLayout.LayoutParams.WRAP_CONTENT)
//        relLayoutParams.setMargins(16 , 0 , 16, 0)
//        layout.background = resources.getDrawable(R.drawable.relativelayout_style)
//
//        var txtViewsLayouParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)
//        txtView.layoutParams = txtViewsLayouParams
//        txtView.background = resources.getDrawable(R.color.light_blue)
//        txtView.isFocusable = true
//        txtView.isFocusableInTouchMode = true
//        txtView.gravity = Gravity.CENTER
//        txtView.minHeight = 64
//        txtView.text = resources.getString(txtViewStringResource)
//        txtView.setTextColor(resources.getColor(R.color.black))
//        txtView.setTextSize(20f)
//
//        radioGroup.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)
//
//
//        val rgVlayoutParams = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        // Add a rule to position the TextView below the specified component
//        rgVlayoutParams.addRule(RelativeLayout.BELOW, txtView.getId())
//
//        // Apply the layout parameters to the TextView
//        radioGroup.layoutParams = rgVlayoutParams
//
//        val rblayoutParams = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        // Add a rule to position the TextView below the specified component
//        rblayoutParams.addRule(RelativeLayout.BELOW, radioGroup.getId())
//
//        val rblayoutParams2 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        rblayoutParams2.addRule(RelativeLayout.BELOW, radioGroup.getId())
//        rblayoutParams2.addRule(RelativeLayout.BELOW, rb1.getId())
//
//        val rblayoutParams3 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        rblayoutParams3.addRule(RelativeLayout.BELOW, radioGroup.getId())
//        rblayoutParams3.addRule(RelativeLayout.BELOW, rb2.getId())
//
//        val rblayoutParams4 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        rblayoutParams4.addRule(RelativeLayout.BELOW, radioGroup.getId())
//        rblayoutParams4.addRule(RelativeLayout.BELOW, rb3.getId())
//
//        val rblayoutParams5 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,  // Width
//            RelativeLayout.LayoutParams.WRAP_CONTENT // Height
//        )
//
//        rblayoutParams5.addRule(RelativeLayout.BELOW, radioGroup.getId())
//        rblayoutParams5.addRule(RelativeLayout.BELOW, rb4.getId())
//
//        // Apply the layout parameters to the TextView
//        radioGroup.layoutParams = rblayoutParams
//        rb1.layoutParams = rblayoutParams
//        rb2.layoutParams = rblayoutParams2
//        rb3.layoutParams = rblayoutParams3
//        rb4.layoutParams = rblayoutParams4
//        rb5.layoutParams = rblayoutParams5
//        rb1.setPadding(8 , 0 , 8 , 0)
//        rb2.setPadding(8 , 0 , 8 , 0)
//        rb3.setPadding(8 , 0 , 8 , 0)
//        rb4.setPadding(8 , 0 , 8 , 0)
//        rb5.setPadding(8 , 0 , 8 , 0)
//        rb1.setText(radioButton1StringResource)
//        rb1.gravity = Gravity.CENTER or Gravity.LEFT
//        rb1.setTextColor(mainActivity.resources.getColor(R.color.black))
//        rb1.setTextSize(20f)
//        rb2.setText(radioButton2StringResource)
//        rb2.gravity = Gravity.CENTER or Gravity.LEFT
//
//        rb2.setTextColor(mainActivity.resources.getColor(R.color.black))
//        rb2.setTextSize(20f)
//        rb3.setText(radioButton3StringResource)
//        rb3.setTextColor(mainActivity.resources.getColor(R.color.black))
//        rb3.setTextSize(20f)
//        rb3.gravity = Gravity.CENTER or Gravity.LEFT
//        rb4.setText(radioButton4StringResource)
//        rb4.setTextColor(mainActivity.resources.getColor(R.color.black))
//        rb4.setTextSize(20f)
//        rb4.gravity = Gravity.CENTER or Gravity.LEFT
//        rb5.setText(radiobutton5StringResource)
//        rb5.setTextColor(mainActivity.resources.getColor(R.color.black))
//        rb5.setTextSize(20f)
//        rb5.gravity = Gravity.CENTER or Gravity.LEFT
//        layout.layoutParams = relLayoutParams
//
//
//        //add the above components to the relativelayout
//        layout.addView(txtView ,txtViewsLayouParams)
//        layout.addView(radioGroup , rgVlayoutParams)
//        layout.addView(rb1 , rblayoutParams)
//        layout.addView(rb2 , rblayoutParams2)
//        layout.addView(rb3 , rblayoutParams3)
//        layout.addView(rb4 , rblayoutParams4)
//        layout.addView(rb5 , rblayoutParams5)
//
//        return layout
//    }


    // Function to switch layouts
//    private fun switchLayout() {
//        useLayout1 = !useLayout1 // Toggle between layouts
//        // Reinflate the layout
//        view?.let { view ->
//            val newLayout = if (useLayout1) {
//                opqolCheckBinding = FragmentOPQOLCheck1Binding.inflate(layoutInflater)
//                opqolCheckBinding.root
//            } else {
//                opqolCheckBinding2 = FragmentOPQOLCheck2Binding.inflate(layoutInflater)
//                if (openHistory) {
//                    if (historyTest.OPQOLTestReesult != null) {
//                        setPatientData2(historyTest)
//                    }
//                }
//                else
//                {
//                    if (patientTest.OPQOLTestReesult != null)
//                    {
//                        setPatientData2(historyTest)
//                    }
//                }
//
//                opqolCheckBinding2.leftArrow.setOnClickListener {
//
////            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)
//
//                        allPatientSelections[17] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
//                        allPatientSelections[18] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
//                        allPatientSelections[19] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
//                        allPatientSelections[20] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
//                        allPatientSelections[21] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
//                        allPatientSelections[22] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
//                        allPatientSelections[23] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
//                        allPatientSelections[24] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
//                        allPatientSelections[25] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
//                        allPatientSelections[26] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
//                        allPatientSelections[27] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
//                        allPatientSelections[28] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
//                        allPatientSelections[29] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
//                        allPatientSelections[30] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
//                        allPatientSelections[31] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
//                        allPatientSelections[32] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
//                        allPatientSelections[33] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
//                        allPatientSelections[34] =
//                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)
//                        switchLayout()
//                }
//
//                opqolCheckBinding2.submitBtn.setOnClickListener {
//                    allPatientSelections[17] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
//                    allPatientSelections[18] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
//                    allPatientSelections[19] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
//                    allPatientSelections[20] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
//                    allPatientSelections[21] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
//                    allPatientSelections[22] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
//                    allPatientSelections[23] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
//                    allPatientSelections[24] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
//                    allPatientSelections[25] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
//                    allPatientSelections[26] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
//                    allPatientSelections[27] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
//                    allPatientSelections[28] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
//                    allPatientSelections[29] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
//                    allPatientSelections[30] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
//                    allPatientSelections[31] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
//                    allPatientSelections[32] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
//                    allPatientSelections[33] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
//                    allPatientSelections[34] =
//                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)
//                }
//                    opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections)
//                opqolCheckBinding2.root
//            }
//            // Replace the current layout with the new layout
//            (requireView().parent as? ViewGroup)?.apply {
//                removeAllViews()
//                addView(newLayout)
//            }
//        }
//
//    }

    private fun setPatientSelections(answers : ArrayList<Int?>)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI

//            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ1RG , test.patientOPQOLQ1!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(0), answers.get(0)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(1) , answers.get(1)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(2) , answers.get(2)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(3) , answers.get(3)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(4) , answers.get(4)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(5) , answers.get(5)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(6) , answers.get(6)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(7) , answers.get(7)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(8) , answers.get(8)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(9) , answers.get(9)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(10) , answers.get(10)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(11) , answers.get(11)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(12) , answers.get(12)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(13) , answers.get(13)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(14) , answers.get(14)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(15) , answers.get(15)!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(16) , answers.get(16)!!)

        } , 1000)
    }

    private fun setPatientData1(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI

//            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ1RG , test.patientOPQOLQ1!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(0), test.patientOPQOLQ1!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(1) , test.patientOPQOLQ2!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(2) , test.patientOPQOLQ3!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(3) , test.patientOPQOLQ4!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(4) , test.patientOPQOLQ5!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(5) , test.patientOPQOLQ6!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(6) , test.patientOPQOLQ7!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(7) , test.patientOPQOLQ8!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(8) , test.patientOPQOLQ9!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(9) , test.patientOPQOLQ10!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(10) , test.patientOPQOLQ11!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(11) , test.patientOPQOLQ12!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(12) , test.patientOPQOLQ13!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(13) , test.patientOPQOLQ14!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(14) , test.patientOPQOLQ15!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(15) , test.patientOPQOLQ16!!)
            setQuestionRadioGroup(allGeneratedRadioGroups.get(16) , test.patientOPQOLQ17!!)

        } , 1000)
    }

//    private fun setPatientData2(test : Test)
//    {
//        Handler(Looper.getMainLooper()).postDelayed({
////            initialisePatientData()
//            //show all the data on the UI
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5aRG , test.patientOPQOLQ18!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5bRG , test.patientOPQOLQ19!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5cRG , test.patientOPQOLQ20!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5dRG , test.patientOPQOLQ21!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6aRG , test.patientOPQOLQ22!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6bRG , test.patientOPQOLQ23!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6cRG , test.patientOPQOLQ24!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6dRG , test.patientOPQOLQ25!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7aRG , test.patientOPQOLQ26!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7bRG , test.patientOPQOLQ27!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7cRG, test.patientOPQOLQ28!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7dRG , test.patientOPQOLQ29!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8aRG , test.patientOPQOLQ30!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8bRG , test.patientOPQOLQ31!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8cRG , test.patientOPQOLQ32!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8dRG , test.patientOPQOLQ33!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8eRG , test.patientOPQOLQ34!!)
//            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8fRG , test.patientOPQOLQ35!!)
//
//        } , 1000)
//    }


    fun showSelectionError(error : String, qNo : Int) {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
            when (qNo) {
                1 -> allGeneratedRadioGroups.get(0).requestFocus()
                2 -> allGeneratedRadioGroups.get(1).requestFocus()
                3 -> allGeneratedRadioGroups.get(2).requestFocus()
                4 -> allGeneratedRadioGroups.get(3).requestFocus()
                5 -> allGeneratedRadioGroups.get(4).requestFocus()
                6 -> allGeneratedRadioGroups.get(5).requestFocus()
                7 -> allGeneratedRadioGroups.get(6).requestFocus()
                8 -> allGeneratedRadioGroups.get(7).requestFocus()
                9 -> allGeneratedRadioGroups.get(8).requestFocus()
                10 -> allGeneratedRadioGroups.get(9).requestFocus()
                11 -> allGeneratedRadioGroups.get(10).requestFocus()
                12 -> allGeneratedRadioGroups.get(11).requestFocus()
                13 -> allGeneratedRadioGroups.get(12).requestFocus()
                14 -> allGeneratedRadioGroups.get(13).requestFocus()
                15 -> allGeneratedRadioGroups.get(14).requestFocus()
                16 -> allGeneratedRadioGroups.get(15).requestFocus()
                17 -> allGeneratedRadioGroups.get(16).requestFocus()
                18 -> allGeneratedRadioGroups.get(17).requestFocus()
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


    private fun getAsnwerFromRadioGroup1(opqolQ1RG: RadioGroup): Int? {

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
        opqolCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    fun initialisePatientData()
    {
        allGeneratedRadioGroups.get(0).clearCheck()
        allGeneratedRadioGroups.get(1).clearCheck()
        allGeneratedRadioGroups.get(2).clearCheck()
        allGeneratedRadioGroups.get(3).clearCheck()
        allGeneratedRadioGroups.get(4).clearCheck()
        allGeneratedRadioGroups.get(5).clearCheck()
        allGeneratedRadioGroups.get(6).clearCheck()
        allGeneratedRadioGroups.get(7).clearCheck()
        allGeneratedRadioGroups.get(8).clearCheck()
        allGeneratedRadioGroups.get(9).clearCheck()
        allGeneratedRadioGroups.get(10).clearCheck()
        allGeneratedRadioGroups.get(11).clearCheck()
        allGeneratedRadioGroups.get(12).clearCheck()
        allGeneratedRadioGroups.get(13).clearCheck()
        allGeneratedRadioGroups.get(14).clearCheck()
        allGeneratedRadioGroups.get(15).clearCheck()
        allGeneratedRadioGroups.get(16).clearCheck()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    //xml randomization of questions methods
    data class Component(
        val relativeLayout: RelativeLayout,
        val textView: TextView,
        val radioGroup: RadioGroup,
        val radioButtons: List<RadioButton>
    )

    fun parseComponents(inputStream: InputStream): List<Component> {
        val components = mutableListOf<Component>()
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

                components.add(Component(relativeLayout , textView, radioGroup, radioButtons))
            }
        }

        return components
    }

    fun parseParserComponents(parser: XmlPullParser): ArrayList<Component> {
        val components = ArrayList<Component>()

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
                                components.add(Component(relativeLayout!!, textView!!, radioGroup!!, radioButtons))
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

    fun generateXmlDocument(components: List<Component> , qustionsNo : ArrayList<Int>): Document {
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
            allGeneratedRelativeLayoutIds.add(id.toInt())
            componentElement.setAttribute("android:background" ,"@drawable/relativelayout_style" )

            if (i == 0)
            {
                componentElement.setAttribute("android:layout_below" , R.id.opqoQ2cTextV.toString())
            }
            else
            {
                componentElement.setAttribute("android:layout_below" , allGeneratedRelativeLayoutIds.get(i-1).toString())
            }
            val textViewElement = doc.createElement("TextView")
            val textViewId = View.generateViewId()
            allGeneratedTxtViewsIds.add(textViewId)
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
            textViewElement.textContent = components.get(i).textView.text.toString()
            componentElement.appendChild(textViewElement)

            val radioGroupElement = doc.createElement("RadioGroup")
            //set the attributes for the radioGroup
            val radioGroupId = View.generateViewId()
            allGeneratedRadioGroupIds.add(radioGroupId)
            radioGroupElement.setAttribute("android:id" , "${radioGroupId.toString()}")
            radioGroupElement.setAttribute("android:layout_width", "match_parent")
            radioGroupElement.setAttribute("android:layout_height", "wrap_content")
            radioGroupElement.setAttribute("android:layout_below" , "${allGeneratedTxtViewsIds.get(i)}")
            componentElement.appendChild(radioGroupElement)

            var allRadioButtons = ArrayList<RadioButton>(4)
            if (components.get(i).radioGroup.childCount >0)
            {
                for (i in 0..components.get(i).radioGroup.childCount -1)
                {
                    allRadioButtons.add(components.get(i).radioGroup.get(i) as RadioButton)
                }

                for (k in 0 until allRadioButtons.size) {
                    val radioButtonElement = doc.createElement("RadioButton")
                    radioButtonElement.textContent = allRadioButtons.get(k).text.toString()
                    val radioButtonId = View.generateViewId()
                    allGeneratedRadioButtonIds.add(radioButtonId)
                    radioButtonElement.setAttribute("android:id" , "${radioButtonId}")
                    radioButtonElement.setAttribute("android:layout_width", "match_parent")
                    radioButtonElement.setAttribute("android:layout_height", "wrap_content")
                    radioButtonElement.setAttribute("android:layout_gravity" , "center|left")
                    radioButtonElement.setAttribute("android:paddingLeft" , "8dp")
                    radioButtonElement.setAttribute("android:paddingTop" , "8dp")
                    radioButtonElement.setAttribute("android:textColor" , "@color/black")
                    radioButtonElement.setAttribute("android:textSize" , "20sp")
                    when (k)
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

    fun String.toInputStream(): InputStream {
        return ByteArrayInputStream(this.toByteArray())
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(args : Bundle) =
            OPQOLCheckFragment().apply {
                val fragment = OPQOLCheckFragment()
                fragment.arguments = args
                return fragment
            }
    }
}
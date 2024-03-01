package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck1Binding
import com.example.cvdriskestimator.databinding.FragmentOPQOLCheck2Binding
import com.example.cvdriskestimator.viewModels.CheckOPQOLPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.CheckOPQOLViewModel


class OPQOLCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var opqolPatientViewModel: CheckOPQOLViewModel
    private lateinit var opqolCheckBinding : FragmentOPQOLCheck1Binding
    private lateinit var opqolCheckBinding2 : FragmentOPQOLCheck2Binding
    private lateinit var thisFragmentPatientSelections : ArrayList<Int?>
    private var thisFragmentSelectionsExists : Boolean = false
    private lateinit var otherFragmentPatientSelections : ArrayList<Int>
    private var otherFragmentSelectionsExists : Boolean = false
    private var allPatientSelections =
    arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1)
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
//    private var allGeneratedRelativeLayoutIds = ArrayList<Int>(36)
//    private var allGeneratedCatTxtViewsIds = ArrayList<Int>(36)
//    private var allGeneratedTxtViewsIds = ArrayList<Int>(36)
//    private var allGeneratedRadioGroupIds = ArrayList<Int>(36)
//    private var allGeneratedRadioButtonIds = ArrayList<Int>(180)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //assign the binding to the xml layout
        val rootView = if (useLayout1) {
            opqolCheckBinding = FragmentOPQOLCheck1Binding.inflate(inflater, container, false)
            opqolPatientViewModel = CheckOPQOLViewModel()
            opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
                CheckOPQOLViewModel::class.java)
            opqolCheckBinding.checkOQPOLPatientViewModel = opqolPatientViewModel
            opqolCheckBinding.root

        } else {
            opqolCheckBinding2 = FragmentOPQOLCheck2Binding.inflate(inflater, container, false)
            opqolPatientViewModel = CheckOPQOLViewModel()
            opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
                CheckOPQOLViewModel::class.java)
            opqolCheckBinding2.checkOQPOLPatientViewModel = opqolPatientViewModel
            opqolCheckBinding2.root
        }
        opqolPatientViewModel = CheckOPQOLViewModel()
        opqolPatientViewModel = ViewModelProvider(this , CheckOPQOLPatientViewModelFactory()).get(
            CheckOPQOLViewModel::class.java)
        opqolCheckBinding.checkOQPOLPatientViewModel = opqolPatientViewModel
        opqolCheckBinding.lifecycleOwner = this
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
        }
        return rootView
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

        if (thisFragmentSelectionsExists == true)
        {
            setPatientSelections(thisFragmentPatientSelections)
        }
        else
        {
            if (openType == "open_history")
            {
                openHistory = true
                historyTest = Test()
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
                setPatientData1(it)
            }
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

        opqolCheckBinding.rightArrow.setOnClickListener {

//            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)
            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C1bRG)
            allPatientSelections[2] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C1cRG)
            allPatientSelections[3] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C1dRG)
            allPatientSelections[4] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C2aRG)
            allPatientSelections[5] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C2bRG)
            allPatientSelections[6] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C2cRG)
            allPatientSelections[7] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C2dRG)
            allPatientSelections[8] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C3aRG)
            allPatientSelections[9] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C3bRG)
            allPatientSelections[10] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C3cRG)
            allPatientSelections[11] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C3dRG)
            allPatientSelections[12] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C3eRG)
            allPatientSelections[13] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C4aRG)
            allPatientSelections[14] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C4bRG)
            allPatientSelections[15] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C4cRG)
            allPatientSelections[16] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ2C4dRG)
            thisFragmentPatientSelections = allPatientSelections

            if (opqolPatientViewModel.checkOPQOLTestPatient1(allPatientSelections))
            {
                var args = Bundle()
                if (otherFragmentSelectionsExists)
                {
                    args.putSerializable("Answers2" , otherFragmentPatientSelections)
                }

                    args.putSerializable("Answers" , thisFragmentPatientSelections)
                opqolCheckFragment2 = OPQOLCheckFragment2.newInstance(args)
                mainActivity.fragmentTransaction(opqolCheckFragment2)
            }

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
    private fun switchLayout() {
        useLayout1 = !useLayout1 // Toggle between layouts
        // Reinflate the layout
        view?.let { view ->
            val newLayout = if (useLayout1) {
                opqolCheckBinding = FragmentOPQOLCheck1Binding.inflate(layoutInflater)
                opqolCheckBinding.root
            } else {
                opqolCheckBinding2 = FragmentOPQOLCheck2Binding.inflate(layoutInflater)
                if (openHistory) {
                    if (historyTest.OPQOLTestReesult != null) {
                        setPatientData2(historyTest)
                    }
                }
                else
                {
                    if (patientTest.OPQOLTestReesult != null)
                    {
                        setPatientData2(historyTest)
                    }
                }

                opqolCheckBinding2.leftArrow.setOnClickListener {

//            allPatientSelections[0] = getAsnwerFromRadioGroup1(opqolCheckBinding.OPQOLQ1RG)

                        allPatientSelections[17] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
                        allPatientSelections[18] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
                        allPatientSelections[19] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
                        allPatientSelections[20] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
                        allPatientSelections[21] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
                        allPatientSelections[22] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
                        allPatientSelections[23] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
                        allPatientSelections[24] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
                        allPatientSelections[25] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
                        allPatientSelections[26] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
                        allPatientSelections[27] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
                        allPatientSelections[28] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
                        allPatientSelections[29] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
                        allPatientSelections[30] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
                        allPatientSelections[31] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
                        allPatientSelections[32] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
                        allPatientSelections[33] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
                        allPatientSelections[34] =
                            getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)
                        switchLayout()
                }

                opqolCheckBinding2.submitBtn.setOnClickListener {
                    allPatientSelections[17] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5aRG)
                    allPatientSelections[18] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5bRG)
                    allPatientSelections[19] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5cRG)
                    allPatientSelections[20] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C5dRG)
                    allPatientSelections[21] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6aRG)
                    allPatientSelections[22] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6bRG)
                    allPatientSelections[23] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6cRG)
                    allPatientSelections[24] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C6dRG)
                    allPatientSelections[25] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7aRG)
                    allPatientSelections[26] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7bRG)
                    allPatientSelections[27] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7cRG)
                    allPatientSelections[28] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C7dRG)
                    allPatientSelections[29] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8aRG)
                    allPatientSelections[30] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8bRG)
                    allPatientSelections[31] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8cRG)
                    allPatientSelections[32] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8dRG)
                    allPatientSelections[33] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8eRG)
                    allPatientSelections[34] =
                        getAsnwerFromRadioGroup2(opqolCheckBinding2.OPQOLQ2C8fRG)
                }
                    opqolPatientViewModel.checkOPQOLTestPatient2(allPatientSelections)
                opqolCheckBinding2.root
            }
            // Replace the current layout with the new layout
            (requireView().parent as? ViewGroup)?.apply {
                removeAllViews()
                addView(newLayout)
            }
        }

    }

    private fun setPatientSelections(answers : ArrayList<Int?>)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI

//            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ1RG , test.patientOPQOLQ1!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1RG, answers.get(0)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1bRG , answers.get(1)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1cRG , answers.get(2)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1dRG , answers.get(3)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2aRG , answers.get(4)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2bRG , answers.get(5)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2cRG , answers.get(6)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2dRG , answers.get(7)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3aRG , answers.get(8)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3bRG , answers.get(9)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3cRG , answers.get(10)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3dRG , answers.get(11)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3eRG , answers.get(12)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4aRG , answers.get(13)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4bRG , answers.get(14)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4cRG , answers.get(15)!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4dRG , answers.get(16)!!)

        } , 1000)
    }

    private fun setPatientData1(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI

//            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ1RG , test.patientOPQOLQ1!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1RG, test.patientOPQOLQ1!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1bRG , test.patientOPQOLQ2!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1cRG , test.patientOPQOLQ3!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C1dRG , test.patientOPQOLQ4!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2aRG , test.patientOPQOLQ5!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2bRG , test.patientOPQOLQ6!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2cRG , test.patientOPQOLQ7!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C2dRG , test.patientOPQOLQ8!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3aRG , test.patientOPQOLQ9!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3bRG , test.patientOPQOLQ10!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3cRG , test.patientOPQOLQ11!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3dRG , test.patientOPQOLQ12!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C3eRG , test.patientOPQOLQ13!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4aRG , test.patientOPQOLQ14!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4bRG , test.patientOPQOLQ15!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4cRG , test.patientOPQOLQ16!!)
            setQuestionRadioGroup(opqolCheckBinding.OPQOLQ2C4dRG , test.patientOPQOLQ17!!)

        } , 1000)
    }

    private fun setPatientData2(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
//            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5aRG , test.patientOPQOLQ18!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5bRG , test.patientOPQOLQ19!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5cRG , test.patientOPQOLQ20!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C5dRG , test.patientOPQOLQ21!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6aRG , test.patientOPQOLQ22!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6bRG , test.patientOPQOLQ23!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6cRG , test.patientOPQOLQ24!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C6dRG , test.patientOPQOLQ25!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7aRG , test.patientOPQOLQ26!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7bRG , test.patientOPQOLQ27!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7cRG, test.patientOPQOLQ28!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C7dRG , test.patientOPQOLQ29!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8aRG , test.patientOPQOLQ30!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8bRG , test.patientOPQOLQ31!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8cRG , test.patientOPQOLQ32!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8dRG , test.patientOPQOLQ33!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8eRG , test.patientOPQOLQ34!!)
            setQuestionRadioGroup(opqolCheckBinding2.OPQOLQ2C8fRG , test.patientOPQOLQ35!!)

        } , 1000)
    }


    fun showSelectionError(error : String, qNo : Int) {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
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

        if ((rg != opqolCheckBinding.OPQOLQ2C1dRG) && (rg != opqolCheckBinding.OPQOLQ2C2bRG)
            && (rg != opqolCheckBinding.OPQOLQ2C2cRG) && (rg != opqolCheckBinding.OPQOLQ2C4cRG))
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
        if ((opqolQ1RG != opqolCheckBinding.OPQOLQ2C1dRG) && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C2bRG)
            && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C2cRG) && (opqolQ1RG != opqolCheckBinding.OPQOLQ2C4cRG))
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
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
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
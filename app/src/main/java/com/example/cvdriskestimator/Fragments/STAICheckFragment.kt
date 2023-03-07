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
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.viewModels.CheckSTAIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckSTAIPatientViewModelFactory
import java.sql.Date
import java.util.*
import java.util.zip.Inflater


/**
 * A simple [Fragment] subclass.
 * Use the [BAICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class STAICheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var staiPatientViewModel: CheckSTAIPatientViewModel
    private var allPatientSelections = arrayListOf<Int?>(1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1,
                                                         1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 , 1 ,1 , 1 ,1 ,1 , 1, 1, 1, 1, 1, 1)
    private lateinit var popupMenu: PopUpMenu

    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment

    //all variables related with the fragment
    private lateinit var SQ1RG : RadioGroup

    private lateinit var sq1radiobutton1 : RadioButton
    private lateinit var sq1radiobutton2 : RadioButton
    private lateinit var sq1radiobutton3 : RadioButton
    private lateinit var sq1radiobutton4 : RadioButton

    private lateinit var SQ2RG : RadioGroup

    private lateinit var sq2radiobutton1 : RadioButton
    private lateinit var sq2radiobutton2 : RadioButton
    private lateinit var sq2radiobutton3 : RadioButton
    private lateinit var sq2radiobutton4 : RadioButton

    private lateinit var SQ3RG : RadioGroup

    private lateinit var sq3radiobutton1 : RadioButton
    private lateinit var sq3radiobutton2 : RadioButton
    private lateinit var sq3radiobutton3 : RadioButton
    private lateinit var sq3radiobutton4 : RadioButton

    private lateinit var SQ4RG : RadioGroup

    private lateinit var sq4radiobutton1 : RadioButton
    private lateinit var sq4radiobutton2 : RadioButton
    private lateinit var sq4radiobutton3 : RadioButton
    private lateinit var sq4radiobutton4 : RadioButton

    private lateinit var SQ5RG : RadioGroup

    private lateinit var sq5radiobutton1 : RadioButton
    private lateinit var sq5radiobutton2 : RadioButton
    private lateinit var sq5radiobutton3 : RadioButton
    private lateinit var sq5radiobutton4 : RadioButton

    private lateinit var SQ6RG : RadioGroup

    private lateinit var sq6radiobutton1 : RadioButton
    private lateinit var sq6radiobutton2 : RadioButton
    private lateinit var sq6radiobutton3 : RadioButton
    private lateinit var sq6radiobutton4 : RadioButton

    private lateinit var SQ7RG : RadioGroup

    private lateinit var sq7radiobutton1 : RadioButton
    private lateinit var sq7radiobutton2 : RadioButton
    private lateinit var sq7radiobutton3 : RadioButton
    private lateinit var sq7radiobutton4 : RadioButton

    private lateinit var SQ8RG : RadioGroup

    private lateinit var sq8radiobutton1 : RadioButton
    private lateinit var sq8radiobutton2 : RadioButton
    private lateinit var sq8radiobutton3 : RadioButton
    private lateinit var sq8radiobutton4 : RadioButton

    private lateinit var SQ9RG : RadioGroup

    private lateinit var sq9radiobutton1 : RadioButton
    private lateinit var sq9radiobutton2 : RadioButton
    private lateinit var sq9radiobutton3 : RadioButton
    private lateinit var sq9radiobutton4 : RadioButton

    private lateinit var SQ10RG : RadioGroup

    private lateinit var sq10radiobutton1 : RadioButton
    private lateinit var sq10radiobutton2 : RadioButton
    private lateinit var sq10radiobutton3 : RadioButton
    private lateinit var sq10radiobutton4 : RadioButton

    private lateinit var SQ11RG : RadioGroup

    private lateinit var sq11radiobutton1 : RadioButton
    private lateinit var sq11radiobutton2 : RadioButton
    private lateinit var sq11radiobutton3 : RadioButton
    private lateinit var sq11radiobutton4 : RadioButton

    private lateinit var SQ12RG : RadioGroup

    private lateinit var sq12radiobutton1 : RadioButton
    private lateinit var sq12radiobutton2 : RadioButton
    private lateinit var sq12radiobutton3 : RadioButton
    private lateinit var sq12radiobutton4 : RadioButton

    private lateinit var SQ13RG : RadioGroup

    private lateinit var sq13radiobutton1 : RadioButton
    private lateinit var sq13radiobutton2 : RadioButton
    private lateinit var sq13radiobutton3 : RadioButton
    private lateinit var sq13radiobutton4 : RadioButton

    private lateinit var SQ14RG : RadioGroup

    private lateinit var sq14radiobutton1 : RadioButton
    private lateinit var sq14radiobutton2 : RadioButton
    private lateinit var sq14radiobutton3 : RadioButton
    private lateinit var sq14radiobutton4 : RadioButton

    private lateinit var SQ15RG : RadioGroup

    private lateinit var sq15radiobutton1 : RadioButton
    private lateinit var sq15radiobutton2 : RadioButton
    private lateinit var sq15radiobutton3 : RadioButton
    private lateinit var sq15radiobutton4 : RadioButton

    private lateinit var SQ16RG : RadioGroup

    private lateinit var sq16radiobutton1 : RadioButton
    private lateinit var sq16radiobutton2 : RadioButton
    private lateinit var sq16radiobutton3 : RadioButton
    private lateinit var sq16radiobutton4 : RadioButton

    private lateinit var SQ17RG : RadioGroup

    private lateinit var sq17radiobutton1 : RadioButton
    private lateinit var sq17radiobutton2 : RadioButton
    private lateinit var sq17radiobutton3 : RadioButton
    private lateinit var sq17radiobutton4 : RadioButton

    private lateinit var SQ18RG : RadioGroup

    private lateinit var sq18radiobutton1 : RadioButton
    private lateinit var sq18radiobutton2 : RadioButton
    private lateinit var sq18radiobutton3 : RadioButton
    private lateinit var sq18radiobutton4 : RadioButton

    private lateinit var SQ19RG : RadioGroup

    private lateinit var sq19radiobutton1 : RadioButton
    private lateinit var sq19radiobutton2 : RadioButton
    private lateinit var sq19radiobutton3 : RadioButton
    private lateinit var sq19radiobutton4 : RadioButton

    private lateinit var SQ20RG : RadioGroup

    private lateinit var sq20radiobutton1 : RadioButton
    private lateinit var sq20radiobutton2 : RadioButton
    private lateinit var sq20radiobutton3 : RadioButton
    private lateinit var sq20radiobutton4 : RadioButton

    private lateinit var TQ21RG : RadioGroup

    private lateinit var sq21radiobutton1 : RadioButton
    private lateinit var sq21radiobutton2 : RadioButton
    private lateinit var sq21radiobutton3 : RadioButton
    private lateinit var sq21radiobutton4 : RadioButton

    private lateinit var TQ22RG : RadioGroup

    private lateinit var sq22radiobutton1 : RadioButton
    private lateinit var sq22radiobutton2 : RadioButton
    private lateinit var sq22radiobutton3 : RadioButton
    private lateinit var sq22radiobutton4 : RadioButton

    private lateinit var TQ23RG : RadioGroup

    private lateinit var sq23radiobutton1 : RadioButton
    private lateinit var sq23radiobutton2 : RadioButton
    private lateinit var sq23radiobutton3 : RadioButton
    private lateinit var sq23radiobutton4 : RadioButton

    private lateinit var TQ24RG : RadioGroup

    private lateinit var sq24radiobutton1 : RadioButton
    private lateinit var sq24radiobutton2 : RadioButton
    private lateinit var sq24radiobutton3 : RadioButton
    private lateinit var sq24radiobutton4 : RadioButton

    private lateinit var TQ25RG : RadioGroup

    private lateinit var sq25radiobutton1 : RadioButton
    private lateinit var sq25radiobutton2 : RadioButton
    private lateinit var sq25radiobutton3 : RadioButton
    private lateinit var sq25radiobutton4 : RadioButton

    private lateinit var TQ26RG : RadioGroup

    private lateinit var sq26radiobutton1 : RadioButton
    private lateinit var sq26radiobutton2 : RadioButton
    private lateinit var sq26radiobutton3 : RadioButton
    private lateinit var sq26radiobutton4 : RadioButton

    private lateinit var TQ27RG : RadioGroup

    private lateinit var sq27radiobutton1 : RadioButton
    private lateinit var sq27radiobutton2 : RadioButton
    private lateinit var sq27radiobutton3 : RadioButton
    private lateinit var sq27radiobutton4 : RadioButton

    private lateinit var TQ28RG : RadioGroup

    private lateinit var sq28radiobutton1 : RadioButton
    private lateinit var sq28radiobutton2 : RadioButton
    private lateinit var sq28radiobutton3 : RadioButton
    private lateinit var sq28radiobutton4 : RadioButton

    private lateinit var TQ29RG : RadioGroup

    private lateinit var sq29radiobutton1 : RadioButton
    private lateinit var sq29radiobutton2 : RadioButton
    private lateinit var sq29radiobutton3 : RadioButton
    private lateinit var sq29radiobutton4 : RadioButton

    private lateinit var TQ30RG : RadioGroup

    private lateinit var sq30radiobutton1 : RadioButton
    private lateinit var sq30radiobutton2 : RadioButton
    private lateinit var sq30radiobutton3 : RadioButton
    private lateinit var sq30radiobutton4 : RadioButton

    private lateinit var TQ31RG : RadioGroup

    private lateinit var sq31radiobutton1 : RadioButton
    private lateinit var sq31radiobutton2 : RadioButton
    private lateinit var sq31radiobutton3 : RadioButton
    private lateinit var sq31radiobutton4 : RadioButton

    private lateinit var TQ32RG : RadioGroup

    private lateinit var sq32radiobutton1 : RadioButton
    private lateinit var sq32radiobutton2 : RadioButton
    private lateinit var sq32radiobutton3 : RadioButton
    private lateinit var sq32radiobutton4 : RadioButton

    private lateinit var TQ33RG : RadioGroup

    private lateinit var sq33radiobutton1 : RadioButton
    private lateinit var sq33radiobutton2 : RadioButton
    private lateinit var sq33radiobutton3 : RadioButton
    private lateinit var sq33radiobutton4 : RadioButton

    private lateinit var TQ34RG : RadioGroup

    private lateinit var sq34radiobutton1 : RadioButton
    private lateinit var sq34radiobutton2 : RadioButton
    private lateinit var sq34radiobutton3 : RadioButton
    private lateinit var sq34radiobutton4 : RadioButton

    private lateinit var TQ35RG : RadioGroup

    private lateinit var sq35radiobutton1 : RadioButton
    private lateinit var sq35radiobutton2 : RadioButton
    private lateinit var sq35radiobutton3 : RadioButton
    private lateinit var sq35radiobutton4 : RadioButton

    private lateinit var TQ36RG : RadioGroup

    private lateinit var sq36radiobutton1 : RadioButton
    private lateinit var sq36radiobutton2 : RadioButton
    private lateinit var sq36radiobutton3 : RadioButton
    private lateinit var sq36radiobutton4 : RadioButton

    private lateinit var TQ37RG : RadioGroup

    private lateinit var sq37radiobutton1 : RadioButton
    private lateinit var sq37radiobutton2 : RadioButton
    private lateinit var sq37radiobutton3 : RadioButton
    private lateinit var sq37radiobutton4 : RadioButton

    private lateinit var TQ38RG : RadioGroup

    private lateinit var sq38radiobutton1 : RadioButton
    private lateinit var sq38radiobutton2 : RadioButton
    private lateinit var sq38radiobutton3 : RadioButton
    private lateinit var sq38radiobutton4 : RadioButton

    private lateinit var TQ39RG : RadioGroup

    private lateinit var sq39radiobutton1 : RadioButton
    private lateinit var sq39radiobutton2 : RadioButton
    private lateinit var sq39radiobutton3 : RadioButton
    private lateinit var sq39radiobutton4 : RadioButton

    private lateinit var TQ40RG : RadioGroup

    private lateinit var sq40radiobutton1 : RadioButton
    private lateinit var sq40radiobutton2 : RadioButton
    private lateinit var sq40radiobutton3 : RadioButton
    private lateinit var sq40radiobutton4 : RadioButton

    private lateinit var includeConLayout : ConstraintLayout
    private lateinit var includePopUpMenu : ConstraintLayout
    private lateinit var popUpCloseBtn : Button
    private lateinit var termsLayout : RelativeLayout
    private lateinit var clearBtn : Button
    private lateinit var submitBtn : Button

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
        staiPatientViewModel = CheckSTAIPatientViewModel()
        val checkSTAIPatientViewModelFactory = CheckSTAIPatientViewModelFactory()
        staiPatientViewModel = ViewModelProvider(this , checkSTAIPatientViewModelFactory).get(CheckSTAIPatientViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_stai_check , container, false)
        initUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        staiPatientViewModel.passActivity(mainActivity)
        staiPatientViewModel.passFragment(this)
        staiPatientViewModel.initialiseRealm()

        includeConLayout.findViewById<ImageView>(R.id.userIcon).alpha = 1f

        val userName = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName" , "tempUser")

        var patientId = this.arguments!!.getString("patientId")
        var testDate = this.arguments!!.getString("testDate" , "")
        var openType = this.arguments!!.getString("openType")


        var historyTest = Test()
        if (patientId != "")
        {
            if (testDate != "")
            {
                var date = convertStringToDate(testDate!!)
                historyTest = staiPatientViewModel.fetchHistoryTest(patientId!! , date!!)
            }
        }
        if (historyTest.cvdTestResult != null)
        {
            setPatientData(historyTest)
        }
        else
        {
            if (openType == "updateLast")
            {
                staiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "addNew")
            {
                staiPatientViewModel.setUserDummyData()
                //baiPatientViewModel.setPatientDataOnForm(userName!!)
            }
            if (openType == "history")
            {
                staiPatientViewModel.history()
            }
        }


        //set the observer for the patient mutable live data
        staiPatientViewModel.patientData.observe(viewLifecycleOwner) {
        }

        staiPatientViewModel.testDATA.observe(viewLifecycleOwner) {
            if (it != null)
                setPatientData(it)
        }

        clearBtn.setOnClickListener {
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

        submitBtn.setOnClickListener {

            allPatientSelections[0] = getAsnwerFromRadioGroup(SQ1RG)
            allPatientSelections[1] = getAsnwerFromRadioGroup(SQ2RG)
            allPatientSelections[2] = getAsnwerFromRadioGroup(SQ3RG)
            allPatientSelections[3] = getAsnwerFromRadioGroup(SQ4RG)
            allPatientSelections[4] = getAsnwerFromRadioGroup(SQ5RG)
            allPatientSelections[5] = getAsnwerFromRadioGroup(SQ6RG)
            allPatientSelections[6] = getAsnwerFromRadioGroup(SQ7RG)
            allPatientSelections[7] = getAsnwerFromRadioGroup(SQ8RG)
            allPatientSelections[8] = getAsnwerFromRadioGroup(SQ9RG)
            allPatientSelections[9] = getAsnwerFromRadioGroup(SQ10RG)
            allPatientSelections[10] = getAsnwerFromRadioGroup(SQ11RG)
            allPatientSelections[11] = getAsnwerFromRadioGroup(SQ12RG)
            allPatientSelections[12] = getAsnwerFromRadioGroup(SQ13RG)
            allPatientSelections[13] = getAsnwerFromRadioGroup(SQ14RG)
            allPatientSelections[14] = getAsnwerFromRadioGroup(SQ15RG)
            allPatientSelections[15] = getAsnwerFromRadioGroup(SQ16RG)
            allPatientSelections[16] = getAsnwerFromRadioGroup(SQ17RG)
            allPatientSelections[17] = getAsnwerFromRadioGroup(SQ18RG)
            allPatientSelections[18] = getAsnwerFromRadioGroup(SQ19RG)
            allPatientSelections[19] = getAsnwerFromRadioGroup(SQ20RG)
            allPatientSelections[20] = getAsnwerFromRadioGroup(TQ21RG)
            allPatientSelections[21] = getAsnwerFromRadioGroup(TQ22RG)
            allPatientSelections[22] = getAsnwerFromRadioGroup(TQ23RG)
            allPatientSelections[23] = getAsnwerFromRadioGroup(TQ24RG)
            allPatientSelections[24] = getAsnwerFromRadioGroup(TQ25RG)
            allPatientSelections[25] = getAsnwerFromRadioGroup(TQ26RG)
            allPatientSelections[26] = getAsnwerFromRadioGroup(TQ27RG)
            allPatientSelections[27] = getAsnwerFromRadioGroup(TQ28RG)
            allPatientSelections[28] = getAsnwerFromRadioGroup(TQ29RG)
            allPatientSelections[29] = getAsnwerFromRadioGroup(TQ30RG)
            allPatientSelections[30] = getAsnwerFromRadioGroup(TQ31RG)
            allPatientSelections[31] = getAsnwerFromRadioGroup(TQ32RG)
            allPatientSelections[32] = getAsnwerFromRadioGroup(TQ33RG)
            allPatientSelections[33] = getAsnwerFromRadioGroup(TQ34RG)
            allPatientSelections[34] = getAsnwerFromRadioGroup(TQ35RG)
            allPatientSelections[35] = getAsnwerFromRadioGroup(TQ36RG)
            allPatientSelections[36] = getAsnwerFromRadioGroup(TQ37RG)
            allPatientSelections[37] = getAsnwerFromRadioGroup(TQ38RG)
            allPatientSelections[38] = getAsnwerFromRadioGroup(TQ39RG)
            allPatientSelections[39] = getAsnwerFromRadioGroup(TQ40RG)


            staiPatientViewModel.checkSTAITestPatient(allPatientSelections)
        }

//        termsLayout.visibility = View.INVISIBLE

        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()

        //set the PopUpMenu
//        popupMenu = PopUpMenu(termsLayout , mainActivity, this,  loginFragment, registerFragment , null , leaderBoardFragment)

//        includeConLayout.findViewById<ImageView>(R.id.userIcon).setOnClickListener {
//            popupMenu.showPopUp(it)
//        }


        //listeners on form
//        termsLayout.setOnClickListener {
//            hidePopPutermsLayout()
//        }
//
//        popUpCloseBtn.setOnClickListener {
//            hidePopPutermsLayout()
//        }

        includeConLayout.findViewById<RelativeLayout>(R.id.cvdTitleForm).setOnClickListener {
            mainActivity.backToActivity()

        }

    }

    private fun initUI(view : View)
    {

        includeConLayout = view.findViewById(R.id.include_cvd_title_form)
//        includePopUpMenu = view.findViewById(R.id.include_popup_menu)
        clearBtn = view.findViewById(R.id.clearBtn)
        submitBtn = view.findViewById(R.id.submitBtn)
//        termsLayout = includePopUpMenu.findViewById(R.id.termsRelLayout)
//        popUpCloseBtn = includePopUpMenu.findViewById(R.id.closeBtn)

        SQ1RG = view.findViewById(R.id.STAIQ1RG)

        sq1radiobutton1 = view.findViewById(R.id.STAIQ1RB1)
        sq1radiobutton2 = view.findViewById(R.id.STAIQ1RB2)
        sq1radiobutton3 = view.findViewById(R.id.STAIQ1RB3)
        sq1radiobutton4 = view.findViewById(R.id.STAIQ1RB4)


        SQ2RG = view.findViewById(R.id.STAIQ2RG)

        sq2radiobutton1 = view.findViewById(R.id.STAIQ2RB1)
        sq2radiobutton2 = view.findViewById(R.id.STAIQ2RB2)
        sq2radiobutton3 = view.findViewById(R.id.STAIQ2RB3)
        sq2radiobutton4 = view.findViewById(R.id.STAIQ2RB4)

        SQ3RG = view.findViewById(R.id.STAIQ3RG)

        sq3radiobutton1 = view.findViewById(R.id.STAIQ3RB1)
        sq3radiobutton2 = view.findViewById(R.id.STAIQ3RB2)
        sq3radiobutton3 = view.findViewById(R.id.STAIQ3RB3)
        sq3radiobutton4 = view.findViewById(R.id.STAIQ3RB4)

        SQ4RG = view.findViewById(R.id.STAIQ4RG)

        sq4radiobutton1 = view.findViewById(R.id.STAIQ4RB1)
        sq4radiobutton2 = view.findViewById(R.id.STAIQ4RB2)
        sq4radiobutton3 = view.findViewById(R.id.STAIQ4RB3)
        sq4radiobutton4 = view.findViewById(R.id.STAIQ4RB4)

        SQ5RG = view.findViewById(R.id.STAIQ5RG)

        sq5radiobutton1 = view.findViewById(R.id.STAIQ5RB1)
        sq5radiobutton2 = view.findViewById(R.id.STAIQ5RB2)
        sq5radiobutton3 = view.findViewById(R.id.STAIQ5RB3)
        sq5radiobutton4 = view.findViewById(R.id.STAIQ5RB4)

        SQ6RG = view.findViewById(R.id.STAIQ6RG)

        sq6radiobutton1 = view.findViewById(R.id.STAIQ6RB1)
        sq6radiobutton2 = view.findViewById(R.id.STAIQ6RB2)
        sq6radiobutton3 = view.findViewById(R.id.STAIQ6RB3)
        sq6radiobutton4 = view.findViewById(R.id.STAIQ6RB4)

        SQ7RG = view.findViewById(R.id.STAIQ7RG)

        sq7radiobutton1 = view.findViewById(R.id.STAIQ7RB1)
        sq7radiobutton2 = view.findViewById(R.id.STAIQ7RB2)
        sq7radiobutton3 = view.findViewById(R.id.STAIQ7RB3)
        sq7radiobutton4 = view.findViewById(R.id.STAIQ7RB4)

        SQ8RG = view.findViewById(R.id.STAIQ8RG)

        sq8radiobutton1 = view.findViewById(R.id.STAIQ8RB1)
        sq8radiobutton2 = view.findViewById(R.id.STAIQ8RB2)
        sq8radiobutton3 = view.findViewById(R.id.STAIQ8RB3)
        sq8radiobutton4 = view.findViewById(R.id.STAIQ8RB4)

        SQ9RG = view.findViewById(R.id.STAIQ9RG)

        sq9radiobutton1 = view.findViewById(R.id.STAIQ9RB1)
        sq9radiobutton2 = view.findViewById(R.id.STAIQ9RB2)
        sq9radiobutton3 = view.findViewById(R.id.STAIQ9RB3)
        sq9radiobutton4 = view.findViewById(R.id.STAIQ9RB4)

        SQ10RG = view.findViewById(R.id.STAIQ10RG)

        sq10radiobutton1 = view.findViewById(R.id.STAIQ10RB1)
        sq10radiobutton2 = view.findViewById(R.id.STAIQ10RB2)
        sq10radiobutton3 = view.findViewById(R.id.STAIQ10RB3)
        sq10radiobutton4 = view.findViewById(R.id.STAIQ10RB4)

        SQ11RG = view.findViewById(R.id.STAIQ11RG)

        sq11radiobutton1 = view.findViewById(R.id.STAIQ11RB1)
        sq11radiobutton2 = view.findViewById(R.id.STAIQ11RB2)
        sq11radiobutton3 = view.findViewById(R.id.STAIQ11RB3)
        sq11radiobutton4 = view.findViewById(R.id.STAIQ11RB4)

        SQ12RG = view.findViewById(R.id.STAIQ12RG)

        sq12radiobutton1 = view.findViewById(R.id.STAIQ12RB1)
        sq12radiobutton2 = view.findViewById(R.id.STAIQ12RB2)
        sq12radiobutton3 = view.findViewById(R.id.STAIQ12RB3)
        sq12radiobutton4 = view.findViewById(R.id.STAIQ12RB4)

        SQ13RG = view.findViewById(R.id.STAIQ13RG)

        sq13radiobutton1 = view.findViewById(R.id.STAIQ13RB1)
        sq13radiobutton2 = view.findViewById(R.id.STAIQ13RB2)
        sq13radiobutton3 = view.findViewById(R.id.STAIQ13RB3)
        sq13radiobutton4 = view.findViewById(R.id.STAIQ13RB4)

        SQ14RG = view.findViewById(R.id.STAIQ14RG)

        sq14radiobutton1 = view.findViewById(R.id.STAIQ14RB1)
        sq14radiobutton2 = view.findViewById(R.id.STAIQ14RB2)
        sq14radiobutton3 = view.findViewById(R.id.STAIQ14RB3)
        sq14radiobutton4 = view.findViewById(R.id.STAIQ14RB4)

        SQ15RG = view.findViewById(R.id.STAIQ15RG)

        sq15radiobutton1 = view.findViewById(R.id.STAIQ15RB1)
        sq15radiobutton2 = view.findViewById(R.id.STAIQ15RB2)
        sq15radiobutton3 = view.findViewById(R.id.STAIQ15RB3)
        sq15radiobutton4 = view.findViewById(R.id.STAIQ15RB4)

        SQ16RG = view.findViewById(R.id.STAIQ16RG)

        sq16radiobutton1 = view.findViewById(R.id.STAIQ16RB1)
        sq16radiobutton2 = view.findViewById(R.id.STAIQ16RB2)
        sq16radiobutton3 = view.findViewById(R.id.STAIQ16RB3)
        sq16radiobutton4 = view.findViewById(R.id.STAIQ16RB4)

        SQ17RG = view.findViewById(R.id.STAIQ17RG)

        sq17radiobutton1 = view.findViewById(R.id.STAIQ17RB1)
        sq17radiobutton2 = view.findViewById(R.id.STAIQ17RB2)
        sq17radiobutton3 = view.findViewById(R.id.STAIQ17RB3)
        sq17radiobutton4 = view.findViewById(R.id.STAIQ17RB4)

        SQ18RG = view.findViewById(R.id.STAIQ18RG)

        sq18radiobutton1 = view.findViewById(R.id.STAIQ18RB1)
        sq18radiobutton2 = view.findViewById(R.id.STAIQ18RB2)
        sq18radiobutton3 = view.findViewById(R.id.STAIQ18RB3)
        sq18radiobutton4 = view.findViewById(R.id.STAIQ18RB4)

        SQ19RG = view.findViewById(R.id.STAIQ19RG)

        sq19radiobutton1 = view.findViewById(R.id.STAIQ19RB1)
        sq19radiobutton2 = view.findViewById(R.id.STAIQ19RB2)
        sq19radiobutton3 = view.findViewById(R.id.STAIQ19RB3)
        sq19radiobutton4 = view.findViewById(R.id.STAIQ19RB4)

        SQ20RG = view.findViewById(R.id.STAIQ20RG)

        sq20radiobutton1 = view.findViewById(R.id.STAIQ20RB1)
        sq20radiobutton2 = view.findViewById(R.id.STAIQ20RB2)
        sq20radiobutton3 = view.findViewById(R.id.STAIQ20RB3)
        sq20radiobutton4 = view.findViewById(R.id.STAIQ20RB4)

        TQ21RG = view.findViewById(R.id.STAIQ21RG)

        sq21radiobutton1 = view.findViewById(R.id.STAIQ21RB1)
        sq21radiobutton2 = view.findViewById(R.id.STAIQ21RB2)
        sq21radiobutton3 = view.findViewById(R.id.STAIQ21RB3)
        sq21radiobutton4 = view.findViewById(R.id.STAIQ21RB4)

        TQ22RG = view.findViewById(R.id.STAIQ22RG)

        sq22radiobutton1 = view.findViewById(R.id.STAIQ22RB1)
        sq22radiobutton2 = view.findViewById(R.id.STAIQ22RB2)
        sq22radiobutton3 = view.findViewById(R.id.STAIQ22RB3)
        sq22radiobutton4 = view.findViewById(R.id.STAIQ22RB4)

        TQ23RG = view.findViewById(R.id.STAIQ23RG)

        sq23radiobutton1 = view.findViewById(R.id.STAIQ23RB1)
        sq23radiobutton2 = view.findViewById(R.id.STAIQ23RB2)
        sq23radiobutton3 = view.findViewById(R.id.STAIQ23RB3)
        sq23radiobutton4 = view.findViewById(R.id.STAIQ23RB4)

        TQ24RG = view.findViewById(R.id.STAIQ24RG)

        sq24radiobutton1 = view.findViewById(R.id.STAIQ24RB1)
        sq24radiobutton2 = view.findViewById(R.id.STAIQ24RB2)
        sq24radiobutton3 = view.findViewById(R.id.STAIQ24RB3)
        sq24radiobutton4 = view.findViewById(R.id.STAIQ24RB4)

        TQ25RG = view.findViewById(R.id.STAIQ25RG)

        sq25radiobutton1 = view.findViewById(R.id.STAIQ25RB1)
        sq25radiobutton2 = view.findViewById(R.id.STAIQ25RB2)
        sq25radiobutton3 = view.findViewById(R.id.STAIQ25RB3)
        sq25radiobutton4 = view.findViewById(R.id.STAIQ25RB4)

        TQ26RG = view.findViewById(R.id.STAIQ26RG)

        sq26radiobutton1 = view.findViewById(R.id.STAIQ26RB1)
        sq26radiobutton2 = view.findViewById(R.id.STAIQ26RB2)
        sq26radiobutton3 = view.findViewById(R.id.STAIQ26RB3)
        sq26radiobutton4 = view.findViewById(R.id.STAIQ26RB4)

        TQ27RG = view.findViewById(R.id.STAIQ27RG)

        sq27radiobutton1 = view.findViewById(R.id.STAIQ27RB1)
        sq27radiobutton2 = view.findViewById(R.id.STAIQ27RB2)
        sq27radiobutton3 = view.findViewById(R.id.STAIQ27RB3)
        sq27radiobutton4 = view.findViewById(R.id.STAIQ27RB4)

        TQ28RG = view.findViewById(R.id.STAIQ28RG)

        sq28radiobutton1 = view.findViewById(R.id.STAIQ28RB1)
        sq28radiobutton2 = view.findViewById(R.id.STAIQ28RB2)
        sq28radiobutton3 = view.findViewById(R.id.STAIQ28RB3)
        sq28radiobutton4 = view.findViewById(R.id.STAIQ28RB4)

        TQ29RG = view.findViewById(R.id.STAIQ29RG)

        sq29radiobutton1 = view.findViewById(R.id.STAIQ29RB1)
        sq29radiobutton2 = view.findViewById(R.id.STAIQ29RB2)
        sq29radiobutton3 = view.findViewById(R.id.STAIQ29RB3)
        sq29radiobutton4 = view.findViewById(R.id.STAIQ29RB4)

        TQ30RG = view.findViewById(R.id.STAIQ30RG)

        sq30radiobutton1 = view.findViewById(R.id.STAIQ30RB1)
        sq30radiobutton2 = view.findViewById(R.id.STAIQ30RB2)
        sq30radiobutton3 = view.findViewById(R.id.STAIQ30RB3)
        sq30radiobutton4 = view.findViewById(R.id.STAIQ30RB4)

        TQ31RG = view.findViewById(R.id.STAIQ31RG)

        sq31radiobutton1 = view.findViewById(R.id.STAIQ31RB1)
        sq31radiobutton2 = view.findViewById(R.id.STAIQ31RB2)
        sq31radiobutton3 = view.findViewById(R.id.STAIQ31RB3)
        sq31radiobutton4 = view.findViewById(R.id.STAIQ31RB4)

        TQ32RG = view.findViewById(R.id.STAIQ32RG)

        sq32radiobutton1 = view.findViewById(R.id.STAIQ32RB1)
        sq32radiobutton2 = view.findViewById(R.id.STAIQ32RB2)
        sq32radiobutton3 = view.findViewById(R.id.STAIQ32RB3)
        sq32radiobutton4 = view.findViewById(R.id.STAIQ32RB4)

        TQ33RG = view.findViewById(R.id.STAIQ33RG)

        sq33radiobutton1 = view.findViewById(R.id.STAIQ33RB1)
        sq33radiobutton2 = view.findViewById(R.id.STAIQ33RB2)
        sq33radiobutton3 = view.findViewById(R.id.STAIQ33RB3)
        sq33radiobutton4 = view.findViewById(R.id.STAIQ33RB4)

        TQ34RG = view.findViewById(R.id.STAIQ34RG)

        sq34radiobutton1 = view.findViewById(R.id.STAIQ34RB1)
        sq34radiobutton2 = view.findViewById(R.id.STAIQ34RB2)
        sq34radiobutton3 = view.findViewById(R.id.STAIQ34RB3)
        sq34radiobutton4 = view.findViewById(R.id.STAIQ34RB4)

        TQ35RG = view.findViewById(R.id.STAIQ35RG)

        sq35radiobutton1 = view.findViewById(R.id.STAIQ35RB1)
        sq35radiobutton2 = view.findViewById(R.id.STAIQ35RB2)
        sq35radiobutton3 = view.findViewById(R.id.STAIQ35RB3)
        sq35radiobutton4 = view.findViewById(R.id.STAIQ35RB4)

        TQ36RG = view.findViewById(R.id.STAIQ36RG)

        sq36radiobutton1 = view.findViewById(R.id.STAIQ36RB1)
        sq36radiobutton2 = view.findViewById(R.id.STAIQ36RB2)
        sq36radiobutton3 = view.findViewById(R.id.STAIQ36RB3)
        sq36radiobutton4 = view.findViewById(R.id.STAIQ36RB4)

        TQ37RG = view.findViewById(R.id.STAIQ37RG)

        sq37radiobutton1 = view.findViewById(R.id.STAIQ37RB1)
        sq37radiobutton2 = view.findViewById(R.id.STAIQ37RB2)
        sq37radiobutton3 = view.findViewById(R.id.STAIQ37RB3)
        sq37radiobutton4 = view.findViewById(R.id.STAIQ37RB4)

        TQ38RG = view.findViewById(R.id.STAIQ38RG)

        sq38radiobutton1 = view.findViewById(R.id.STAIQ38RB1)
        sq38radiobutton2 = view.findViewById(R.id.STAIQ38RB2)
        sq38radiobutton3 = view.findViewById(R.id.STAIQ38RB3)
        sq38radiobutton4 = view.findViewById(R.id.STAIQ38RB4)

        TQ39RG = view.findViewById(R.id.STAIQ39RG)

        sq39radiobutton1 = view.findViewById(R.id.STAIQ39RB1)
        sq39radiobutton2 = view.findViewById(R.id.STAIQ39RB2)
        sq39radiobutton3 = view.findViewById(R.id.STAIQ39RB3)
        sq39radiobutton4 = view.findViewById(R.id.STAIQ39RB4)

        TQ40RG = view.findViewById(R.id.STAIQ40RG)

        sq40radiobutton1 = view.findViewById(R.id.STAIQ40RB1)
        sq40radiobutton2 = view.findViewById(R.id.STAIQ40RB2)
        sq40radiobutton3 = view.findViewById(R.id.STAIQ40RB3)
        sq40radiobutton4 = view.findViewById(R.id.STAIQ40RB4)

    }

    private fun setPatientData(test : Test)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialisePatientData()
            //show all the data on the UI
            setQuestionRadioGroup(SQ1RG , test.patientSTAISQ1)
            setQuestionRadioGroup(SQ2RG , test.patientSTAISQ2)
            setQuestionRadioGroup(SQ3RG , test.patientSTAISQ3)
            setQuestionRadioGroup(SQ4RG , test.patientSTAISQ4)
            setQuestionRadioGroup(SQ5RG , test.patientSTAISQ5)
            setQuestionRadioGroup(SQ6RG , test.patientSTAISQ6)
            setQuestionRadioGroup(SQ7RG , test.patientSTAISQ7)
            setQuestionRadioGroup(SQ8RG , test.patientSTAISQ8)
            setQuestionRadioGroup(SQ9RG , test.patientSTAISQ9)
            setQuestionRadioGroup(SQ10RG , test.patientSTAISQ10)
            setQuestionRadioGroup(SQ11RG , test.patientSTAISQ11)
            setQuestionRadioGroup(SQ12RG , test.patientSTAISQ12)
            setQuestionRadioGroup(SQ13RG , test.patientSTAISQ13)
            setQuestionRadioGroup(SQ14RG , test.patientSTAISQ14)
            setQuestionRadioGroup(SQ15RG , test.patientSTAISQ15)
            setQuestionRadioGroup(SQ16RG , test.patientSTAISQ16)
            setQuestionRadioGroup(SQ17RG , test.patientSTAISQ17)
            setQuestionRadioGroup(SQ18RG , test.patientSTAISQ18)
            setQuestionRadioGroup(SQ19RG , test.patientSTAISQ19)
            setQuestionRadioGroup(SQ20RG , test.patientSTAISQ20)
            setQuestionRadioGroup(TQ21RG , test.patientSTAITQ21)
            setQuestionRadioGroup(TQ22RG , test.patientSTAITQ22)
            setQuestionRadioGroup(TQ23RG , test.patientSTAITQ23)
            setQuestionRadioGroup(TQ24RG , test.patientSTAITQ24)
            setQuestionRadioGroup(TQ25RG , test.patientSTAITQ25)
            setQuestionRadioGroup(TQ26RG , test.patientSTAITQ26)
            setQuestionRadioGroup(TQ27RG , test.patientSTAITQ27)
            setQuestionRadioGroup(TQ28RG , test.patientSTAITQ28)
            setQuestionRadioGroup(TQ29RG , test.patientSTAITQ29)
            setQuestionRadioGroup(TQ30RG , test.patientSTAITQ30)
            setQuestionRadioGroup(TQ31RG , test.patientSTAITQ31)
            setQuestionRadioGroup(TQ32RG , test.patientSTAITQ32)
            setQuestionRadioGroup(TQ33RG , test.patientSTAITQ33)
            setQuestionRadioGroup(TQ34RG , test.patientSTAITQ34)
            setQuestionRadioGroup(TQ35RG , test.patientSTAITQ35)
            setQuestionRadioGroup(TQ36RG , test.patientSTAITQ36)
            setQuestionRadioGroup(TQ37RG , test.patientSTAITQ37)
            setQuestionRadioGroup(TQ38RG , test.patientSTAITQ38)
            setQuestionRadioGroup(TQ39RG , test.patientSTAITQ39)
            setQuestionRadioGroup(TQ40RG , test.patientSTAITQ40)
        } , 1000)
    }

    fun initialisePatientData()
    {
        SQ1RG.clearCheck()
        SQ2RG.clearCheck()
        SQ3RG.clearCheck()
        SQ4RG.clearCheck()
        SQ5RG.clearCheck()
        SQ6RG.clearCheck()
        SQ7RG.clearCheck()
        SQ8RG.clearCheck()
        SQ9RG.clearCheck()
        SQ10RG.clearCheck()
        SQ11RG.clearCheck()
        SQ12RG.clearCheck()
        SQ13RG.clearCheck()
        SQ14RG.clearCheck()
        SQ15RG.clearCheck()
        SQ16RG.clearCheck()
        SQ17RG.clearCheck()
        SQ18RG.clearCheck()
        SQ19RG.clearCheck()
        SQ20RG.clearCheck()
        TQ21RG.clearCheck()
        TQ22RG.clearCheck()
        TQ23RG.clearCheck()
        TQ24RG.clearCheck()
        TQ25RG.clearCheck()
        TQ26RG.clearCheck()
        TQ27RG.clearCheck()
        TQ28RG.clearCheck()
        TQ29RG.clearCheck()
        TQ30RG.clearCheck()
        TQ31RG.clearCheck()
        TQ32RG.clearCheck()
        TQ33RG.clearCheck()
        TQ34RG.clearCheck()
        TQ35RG.clearCheck()
        TQ36RG.clearCheck()
        TQ37RG.clearCheck()
        TQ38RG.clearCheck()
        TQ39RG.clearCheck()
        TQ40RG.clearCheck()

    }

    private fun setQuestionRadioGroup(rg : RadioGroup, answer : Int?)
    {
        if ((rg.id == SQ1RG.id) || (rg.id == SQ2RG.id)
            || (rg.id == SQ5RG.id)|| (rg.id ==SQ8RG.id)
            || (rg.id == SQ10RG.id)|| (rg.id == SQ11RG.id)
            || (rg.id == SQ13RG.id)|| (rg.id == SQ15RG.id)
            || (rg.id == SQ16RG.id)|| (rg.id == SQ20RG.id)
            ||    (rg.id == TQ21RG.id) || (rg.id == TQ26RG.id)
            || (rg.id == TQ27RG.id)|| (rg.id == TQ33RG.id)
            || (rg.id == TQ36RG.id)|| (rg.id == TQ39RG.id))
        {
            if (answer == 4)
                (rg.getChildAt(0) as RadioButton).isChecked = true
            if (answer == 3)
                (rg.getChildAt(1) as RadioButton).isChecked = true
            if (answer == 2)
                (rg.getChildAt(2) as RadioButton).isChecked = true
            if (answer == 1)
                (rg.getChildAt(3) as RadioButton).isChecked = true
        }
        else
        {
            if (answer == 1)
                (rg.getChildAt(3) as RadioButton).isChecked = true
            if (answer == 2)
                (rg.getChildAt(2) as RadioButton).isChecked = true
            if (answer == 3)
                (rg.getChildAt(1) as RadioButton).isChecked = true
            if (answer == 4)
                (rg.getChildAt(0) as RadioButton).isChecked = true
        }
    }

    private fun getAsnwerFromRadioGroup(rg : RadioGroup) : Int?
    {
        var result : Int? = null
        val radioButtonId = rg.checkedRadioButtonId
        if ((rg.id == SQ1RG.id) || (rg.id == SQ2RG.id)
            || (rg.id == SQ5RG.id)|| (rg.id ==SQ8RG.id)
            || (rg.id == SQ10RG.id)|| (rg.id == SQ11RG.id)
            || (rg.id == SQ13RG.id)|| (rg.id == SQ15RG.id)
            || (rg.id == SQ16RG.id)|| (rg.id == SQ20RG.id)
            ||    (rg.id == TQ21RG.id) || (rg.id == TQ26RG.id)
            || (rg.id == TQ27RG.id)|| (rg.id == TQ33RG.id)
            || (rg.id == TQ36RG.id)|| (rg.id == TQ39RG.id))
        {
            if (rg.get(0).id == radioButtonId)
                result = 4
            if (rg.get(1).id == radioButtonId)
                result = 3
            if (rg.get(2).id == radioButtonId)
                result = 2
            if (rg.get(3).id == radioButtonId)
                result = 1
        }
        else
        {
            if (rg.get(0).id == radioButtonId)
                result = 1
            if (rg.get(1).id == radioButtonId)
                result = 2
            if (rg.get(2).id == radioButtonId)
                result = 3
            if (rg.get(3).id == radioButtonId)
                result = 4
        }
        return result
    }

    fun hidePopPutermsLayout()
    {
       termsLayout.visibility = View.INVISIBLE
    }

    fun showSelectionError(error : String , questNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext, error, Toast.LENGTH_LONG).show()
        when(questNo)
        {
            1 ->
            {
                SQ1RG.requestFocus()
            }

            2 ->
            {
                SQ2RG.requestFocus()
            }

            3 ->
            {
                SQ3RG.requestFocus()
            }

            4 ->
            {
                SQ4RG.requestFocus()
            }

            5 ->
            {
                SQ5RG.requestFocus()
            }

            6 ->
            {
                SQ6RG.requestFocus()
            }

            7 ->
            {
                SQ7RG.requestFocus()
            }

            8 ->
            {
                SQ8RG.requestFocus()
            }

            9 ->
            {
                SQ9RG.requestFocus()
            }

            10 ->
            {
                SQ10RG.requestFocus()
            }

            11 ->
            {
                SQ11RG.requestFocus()
            }

            12 ->
            {
                SQ12RG.requestFocus()
            }

            13 ->
            {
                SQ13RG.requestFocus()
            }

            14 ->
            {
                SQ14RG.requestFocus()
            }

            15 ->
            {
                SQ15RG.requestFocus()
            }

            16 ->
            {
                SQ16RG.requestFocus()
            }

            17 ->
            {
                SQ17RG.requestFocus()
            }

            18 ->
            {
                SQ18RG.requestFocus()
            }

            19 ->
            {
                SQ19RG.requestFocus()
            }

            20 ->
            {
                SQ20RG.requestFocus()
            }

            21 ->
            {
                TQ21RG.requestFocus()
            }

            22 ->
            {
                TQ22RG.requestFocus()
            }

            23 ->
            {
                TQ23RG.requestFocus()
            }

            24 ->
            {
                TQ24RG.requestFocus()
            }

            25 ->
            {
                TQ25RG.requestFocus()
            }

            26 ->
            {
                TQ26RG.requestFocus()
            }

            27 ->
            {
                TQ27RG.requestFocus()
            }

            28 ->
            {
                TQ28RG.requestFocus()
            }

            29 ->
            {
                TQ29RG.requestFocus()
            }

            30 ->
            {
                TQ30RG.requestFocus()
            }

            31 ->
            {
                TQ31RG.requestFocus()
            }

            32 ->
            {
                TQ32RG.requestFocus()
            }

            33 ->
            {
                TQ33RG.requestFocus()
            }

            34 ->
            {
                TQ34RG.requestFocus()
            }

            35 ->
            {
                TQ35RG.requestFocus()
            }

            36 ->
            {
                TQ36RG.requestFocus()
            }

            37 ->
            {
                TQ37RG.requestFocus()
            }

            38 ->
            {
                TQ38RG.requestFocus()
            }

            39 ->
            {
                TQ39RG.requestFocus()
            }

            40 ->
            {
                TQ40RG.requestFocus()
            }

        }
    }

    private fun convertStringToDate(date: String): java.util.Date {
        var allDateParts = date.split(" ")
        var monthName = allDateParts.get(1)
        var monthNo: Int = 0
        when (monthName) {
            "Jan" -> {
                monthNo = 0
            }
            "Feb" -> {
                monthNo = 1
            }
            "Mar" -> {
                monthNo = 2
            }
            "Apr" -> {
                monthNo = 3
            }
            "May" -> {
                monthNo = 4
            }
            "Jun" -> {
                monthNo = 5
            }
            "Jul" -> {
                monthNo = 6
            }
            "Aug" -> {
                monthNo = 7
            }
            "Sep" -> {
                monthNo = 8
            }
            "Oct" -> {
                monthNo = 9
            }
            "Nov" -> {
                monthNo = 10
            }
            "Dec" -> {
                monthNo = 11
            }
        }
        var dateName = allDateParts.get(2)
        var time = allDateParts.get(3).toString().split(":")
        var hour = time.get(0)
        var min = time.get(1)
        var sec = time.get(2)
        var year = date.get(5)
        var date = Date(year.toInt(), monthNo, dateName.toInt())
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
        calendar.set(Calendar.MINUTE, min.toInt())
        calendar.set(Calendar.SECOND, sec.toInt())
        return calendar.time
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            STAICheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
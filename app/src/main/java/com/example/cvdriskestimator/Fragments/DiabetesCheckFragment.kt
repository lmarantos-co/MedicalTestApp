package com.example.cvdriskestimator.Fragments

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentDiabetesCheckBinding
import com.example.cvdriskestimator.viewModels.CheckDiabetesPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckDiabetesPatientViewModelFactory
import java.lang.reflect.Method


/**
 * A simple [Fragment] subclass.
 * Use the [DiabetesCheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiabetesCheckFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var diabetesCheckBinding : FragmentDiabetesCheckBinding
    private var checkDiabetesPatientViewModel =  CheckDiabetesPatientViewModel()

    private  var loginFragment: LoginFragment = LoginFragment.newInstance()
    private  var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private lateinit var popUpMenu: PopUpMenu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        CheckDiabetesPatientViewModelFactory()
        checkDiabetesPatientViewModel = ViewModelProviders.of(this).get(CheckDiabetesPatientViewModel::class.java)

        // Inflate the layout for this fragment
        diabetesCheckBinding = FragmentDiabetesCheckBinding.inflate(inflater, container, false)
        diabetesCheckBinding.lifecycleOwner = this
        diabetesCheckBinding.checkDiabetesPatientViewModel = checkDiabetesPatientViewModel

        checkDiabetesPatientViewModel.setMainActivity(mainActivity)
        checkDiabetesPatientViewModel.setDiabetesCheckFragment(this)
        initPatientData()
        checkDiabetesPatientViewModel.initRealm()
        return diabetesCheckBinding.root
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?)
    {

        val userName = activity!!.getPreferences(Context.MODE_PRIVATE).getString("userName" , "testUser")
        if (userName != "userDummy")
            checkDiabetesPatientViewModel.setPatientDataOnForm()
        else {
            checkDiabetesPatientViewModel.setUserDummyData()
            checkDiabetesPatientViewModel.setPatientDataOnForm()
        }

        //observe live data change
        checkDiabetesPatientViewModel.patientDATA.observe(viewLifecycleOwner , {
            setPatientData(it)
        })

        diabetesCheckBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }

        diabetesCheckBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        diabetesCheckBinding.formConLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

//        setUserIconDimens()

        diabetesCheckBinding.includeCvdTitleForm.userIcon.alpha = 1f

        diabetesCheckBinding.submitBtn.setOnClickListener {
            hideSoftKeyboard()
            var correctRGInput : Boolean = true
            var toastMessageShown : Boolean = false
            //quick form validation for RadioGroup
            if (getPatientSex() == "")
            {
                Toast.makeText(mainActivity.applicationContext, "Please select SEX Status.", Toast.LENGTH_LONG).show()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getPrescribedAntiHypertensionMedication() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, "Please select Prescribed Antihypertensive Medication Status.", Toast.LENGTH_LONG).show()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getPrescribedSteroids() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select PrescribedSteroids Status" , Toast.LENGTH_LONG).show()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getSiblingWithDiabetes() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select Sibling Family History Status" , Toast.LENGTH_LONG).show()
                correctRGInput = false
                toastMessageShown = true
            }
            if (getSmokingStatus() == "")
            {
                if (!toastMessageShown)
                    Toast.makeText(mainActivity.applicationContext, " Please select Smoking Status" , Toast.LENGTH_LONG).show()
                correctRGInput = false
                toastMessageShown = true
            }

            //call the method to pass the arguments to view model for verification and process
            if (correctRGInput)
            {
                checkDiabetesPatientViewModel.checkPatientForDiabetesData(
                    getPatientSex(), getPrescribedAntiHypertensionMedication(), getPrescribedSteroids(),
                    getPatientAge(), getPatientBMI(), getSiblingWithDiabetes(), getSmokingStatus()
                )
            }

        }

        //initialize popUpMenu Component
        popUpMenu = PopUpMenu(diabetesCheckBinding.includePopUpMenu.termsRelLayout, mainActivity, this, loginFragment, registerFragment)

        diabetesCheckBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenu.showPopUp(diabetesCheckBinding.includeCvdTitleForm.userIcon)
        }

        diabetesCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        diabetesCheckBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }



    }

    private fun hideSoftKeyboard()
    {
        val view : View = mainActivity.window.decorView.rootView
        val imm = mainActivity.applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken , 0)
    }

    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthPixel = displayMetrics.widthPixels

        diabetesCheckBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        diabetesCheckBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        diabetesCheckBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        diabetesCheckBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }



    private fun getPatientSex() : String
    {
        var patientSex : String = ""
        when(diabetesCheckBinding.sexRG.checkedRadioButtonId) {
            diabetesCheckBinding.sexRG.getChildAt(0).id -> {
                patientSex = "MALE"
            }
            diabetesCheckBinding.sexRG.getChildAt(1).id -> {
                patientSex = "FEMALE"
            }

        }
        return patientSex
    }


    private fun getPrescribedAntiHypertensionMedication() : String
    {
        var patientPAM = ""
        when(diabetesCheckBinding.pamRG.checkedRadioButtonId) {
            diabetesCheckBinding.pamRG.getChildAt(0).id -> {
                patientPAM = "YES"
            }
            diabetesCheckBinding.pamRG.getChildAt(1).id -> {
                patientPAM = "NO"
            }
        }
        return patientPAM
    }

    private fun getPrescribedSteroids() : String
    {
        var patientSteroids = ""
        when (diabetesCheckBinding.steroidsRG.checkedRadioButtonId) {
            diabetesCheckBinding.steroidsRG.getChildAt(0).id -> {
                patientSteroids = "YES"
            }
            diabetesCheckBinding.steroidsRG.getChildAt(1).id -> {
                patientSteroids = "NO"
            }
        }
            return patientSteroids
    }

    private fun getPatientAge() : String
    {
        val patientAge: String = diabetesCheckBinding.editTextAge.text.toString()
        return patientAge
    }

    private fun getPatientBMI() : String
    {
        val patientBMI : String = diabetesCheckBinding.editTextBMI.text.toString()
        return patientBMI
    }

    private fun getSiblingWithDiabetes() : String
    {
        var diabeteSibling = ""
        when(diabetesCheckBinding.siblingsRG.checkedRadioButtonId) {
            diabetesCheckBinding.siblingsYesBothRB.id -> {
                diabeteSibling = "Parent And Sibling With Diabetes"
            }
            diabetesCheckBinding.siblingsYesOrRB.id -> {
                diabeteSibling = "Parent Or Sibling With Diabetes"
            }
            diabetesCheckBinding.siblingsNoRB.id -> {
                diabeteSibling = "No Diabetes"
            }
        }
        return diabeteSibling

    }

    private fun getSmokingStatus() : String
    {
        var smokingStatus =""
        when(diabetesCheckBinding.SmokeRGr.checkedRadioButtonId) {
            diabetesCheckBinding.smokeRB1.id -> {
                smokingStatus = "Current"
            }
            diabetesCheckBinding.smokeRB2.id -> {
                smokingStatus = "Former"
            }
            diabetesCheckBinding.smokeRB3.id -> {
                smokingStatus = "Never"
            }
        }
        return smokingStatus
    }


    fun displayAgeError(error : String)
    {
        diabetesCheckBinding.editTextAge.error = error
    }

    fun displayBMIError(error : String)
    {
        diabetesCheckBinding.editTextBMI.error = error
    }

    private fun initPatientData()
    {
        //set sex on UI
        diabetesCheckBinding.sexRG.clearCheck()
        diabetesCheckBinding.pamRG.clearCheck()
        diabetesCheckBinding.steroidsRG.clearCheck()
        diabetesCheckBinding.editTextBMI.setText("")
        diabetesCheckBinding.editTextAge.setText("")
    }

    private fun setPatientData(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed(kotlinx.coroutines.Runnable {
            initPatientData()
            val sex = patient.patientSex
            //set sex on UI
            if (sex == "MALE")
                diabetesCheckBinding.sexRG.check(R.id.maleRB1)
            if (sex == "FEMALE")
                diabetesCheckBinding.sexRG.check(R.id.femaleRB1)

            val pam = patient.patientPAM
            if (pam == "YES")
            {
                diabetesCheckBinding.pamRG.check(R.id.pampositiveRB1)
            }
            if (pam == "NO")
                diabetesCheckBinding.pamRG.check(R.id.pamnegative)

            val steroids = patient.patientSteroids
            if (steroids == "YES")
            {
                diabetesCheckBinding.steroidsRG.check(R.id.steroidspositiveRB1)
            }
            if (steroids == "NO")
                diabetesCheckBinding.steroidsRG.check(R.id.steroidsnegativeRB2)


            var BMI = patient.patientBMI
            diabetesCheckBinding.editTextBMI.setText(BMI.toString())

            var age = patient.patientAge
            diabetesCheckBinding.editTextAge.setText(age.toString())

            var siblings = patient.patientSiblings
            if (siblings == "Parent And Sibling With Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsYesBothRB)
            }
            if (siblings == "Parent Or Sibling With Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsYesOrRB)
            }
            if (siblings == "No Diabetes")
            {
                diabetesCheckBinding.siblingsRG.check(R.id.siblingsNoRB)
            }

            var smokingStatus = patient.smoker
            if (smokingStatus == "Current")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB1)
            }

            if (smokingStatus == "Former")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB2)
            }

            if (smokingStatus == "Never")
            {
                diabetesCheckBinding.SmokeRGr.check(R.id.smokeRB3)
            }
        } , 1000)

    }

    fun showResult(calculation : String)
    {
        diabetesCheckBinding.resultsTitle.text = "RESULTS : " + calculation
        var percentage  = (calculation.substring(0 , 4).toDouble() * 100)
        diabetesCheckBinding.results.text = getString(R.string.result) +  " ${percentage}%"
    }



    private fun showTermsOfUseLayout() {

        diabetesCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        diabetesCheckBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DiabetesCheckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DiabetesCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
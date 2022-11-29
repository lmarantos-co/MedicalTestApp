package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.databinding.FragmentRegisterBinding
import com.example.cvdriskestimator.viewModels.RegisterPatientViewModel
import com.example.cvdriskestimator.viewModels.RegisterPatientViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var registerBinding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterPatientViewModel
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()

    private lateinit var popupMenuComp : PopUpMenu

    private lateinit var loginFragment: LoginFragment
    private var mteTitleHeight : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val actionBar: androidx.appcompat.app.ActionBar? = mainActivity.supportActionBar
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
//        actionBar!!.hide()
//        mainActivity.supportActionBar!!.hide()

        // Inflate the layout for this fragment
        registerBinding = FragmentRegisterBinding.inflate(inflater , container, false)

        //create the relevant MODEL classes and viewmodel instance
//        val dao = PatientDatabase.getInstance(activity!!.applicationContext).patientDAO
//        val repository = PatientRepository(dao)
        val factory = RegisterPatientViewModelFactory()
        setSharedPrefs()
        setTitleFormListener()
        registerViewModel = ViewModelProvider(this , factory)[RegisterPatientViewModel::class.java]
        registerBinding.registerViewModel = registerViewModel
        registerBinding.lifecycleOwner = this
        registerViewModel.setFragment(this)
        registerViewModel.setActivity(mainActivity)
//        setUserIconDimens()
        userNameError(resources.getString(R.string.username_error))
        passwordError(resources.getString(R.string.password_error))
        registerBinding.inclusiveConLayout.findViewById<Button>(R.id.registerSbmBtn).setOnClickListener {

            registerViewModel.insertOrUpdatePatient()
        }

        registerBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

        registerBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE


        return registerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginFragment = LoginFragment.newInstance()
        popupMenuComp = PopUpMenu(registerBinding.includePopUpMenu.termsRelLayout as RelativeLayout, mainActivity, this, loginFragment , null , null ,leaderBoardFragment)

        registerBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popupMenuComp.showPopUp(registerBinding.includeCvdTitleForm.userIcon)
        }

        registerBinding.includeCvdTitleForm.userIcon.alpha = 1f

        registerBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleHeight = registerBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

        registerBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }

        registerBinding.includePopUpMenu.includePopupMenu.setOnClickListener {
            hideTermsOfUseLayout()
        }

        registerBinding.formConLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }
    }

    private fun setSharedPrefs(){
        mainActivity.getPreferences(Context.MODE_PRIVATE).getString("MSG", "CVDRisk Estimator")
    }

    private fun setTitleFormListener()
    {
        registerBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }


    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthPixel = displayMetrics.widthPixels

        registerBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        registerBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        registerBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        registerBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }



    fun userNameError(error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById<EditText>(R.id.userNameRegEditText) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById<EditText>(R.id.userNameRegEditText) as EditText).error = error
    }

    fun passwordError(error : String){
        (registerBinding.inclusiveConLayout.findViewById<EditText>(R.id.editTextRegPassword) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById<EditText>(R.id.editTextRegPassword) as EditText).error = error
    }

    fun patientNameError(error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById(R.id.patientNameEditTxt) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.patientNameEditTxt) as EditText).error = error
    }

    fun patientLastNameError(error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById(R.id.patientLatNameEditTxt) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.patientLatNameEditTxt) as EditText).error = error
    }

    fun passwordMatchError(error : String){
        (registerBinding.inclusiveConLayout.findViewById(R.id.editTextRegValidatePassword) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.editTextRegValidatePassword) as EditText).error = error
    }

    fun userDateError(error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById(R.id.userDateOfBirthEditText) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.userDateOfBirthEditText) as EditText).error = error
    }

    fun userOccupationError(error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById(R.id.userOccupationEditText) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.userOccupationEditText) as EditText).error = error
    }

    fun userYearsError (error : String)
    {
        (registerBinding.inclusiveConLayout.findViewById(R.id.userYearsOfApprenticeEditText) as EditText).requestFocus()
        (registerBinding.inclusiveConLayout.findViewById(R.id.userYearsOfApprenticeEditText) as EditText).error = error
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    private fun showTermsOfUseLayout() {

        registerBinding.includePopUpMenu.termsRelLayout.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        registerBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            RegisterFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
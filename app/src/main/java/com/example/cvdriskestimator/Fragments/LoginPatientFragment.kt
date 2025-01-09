package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.databinding.FragmentDoctorLoginBinding
import com.example.cvdriskestimator.databinding.FragmentLoginPatientBinding
import com.example.cvdriskestimator.viewModels.LoginDoctorViewModel
import com.example.cvdriskestimator.viewModels.LoginDoctorViewModelFactory
import com.example.cvdriskestimator.viewModels.LoginPatientViewModel
import com.example.cvdriskestimator.viewModels.LoginPatientViewModelFactory


class LoginPatientFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private lateinit var loginPatientViewModel: com.example.cvdriskestimator.viewModels.LoginPatientViewModel
    private lateinit var loginPatientBinding: FragmentLoginPatientBinding
    private lateinit var popUpMenuComp: PopUpMenu
    private var mteTitleHieght : Int = 0

    private var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val actionBar: androidx.appcompat.app.ActionBar? = mainActivity.supportActionBar
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
//        actionBar!!.hide()
//        mainActivity.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        loginPatientBinding = FragmentLoginPatientBinding.inflate(inflater, container , false)
        val factory  = LoginPatientViewModelFactory()
        setSharedPrefs()
        //set the ViewModel
        loginPatientViewModel = ViewModelProvider(this, factory)[LoginPatientViewModel::class.java]
        loginPatientBinding.loginPatientViewModel = loginPatientViewModel
        loginPatientBinding.lifecycleOwner = this
        loginPatientViewModel.setActivity(mainActivity)
        loginPatientViewModel.setFragment(this)
//        setTitleBarListener()
//        setUserIconDimens()
        loginPatientBinding.loginSbmBtn.setOnClickListener {
            loginPatientViewModel.loginUser()
        }


        return loginPatientBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        popUpMenuComp = PopUpMenu(loginDoctorBinding.includePopUpMenu.termsRelLayout , mainActivity, this, null, registerFragment , null ,leaderBoardFragment)

//        loginDoctorBinding.includeCvdTitleForm.userIcon.setOnClickListener {
//            popUpMenuComp.showPopUp(loginDoctorBinding.includeCvdTitleForm.userIcon)
//        }

        loginPatientBinding.includeCvdTitleForm.userIcon.alpha = 0f

        loginPatientBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleHieght = loginPatientBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

//        loginDoctorBinding.includePopUpMenu.closeBtn.setOnClickListener {
//            hideTermsOfUseLayout()
//        }


        loginPatientBinding.userNameLoginEditText.requestFocus()

        loginPatientBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.setContentViewForFirstAppScreen()
        }

    }

    private fun setSharedPrefs(){
        mainActivity.getPreferences(Context.MODE_PRIVATE).getString("MSG", "CVDRisk Estimator")
    }

    private  fun setTitleBarListener()
    {
        loginPatientBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }


    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        var widthPixel = displayMetrics.widthPixels

        loginPatientBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        loginPatientBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        loginPatientBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        loginPatientBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun userNameError(error : String)
    {
        loginPatientBinding.userNameLoginEditText.requestFocus()
        loginPatientBinding.userNameLoginEditText.error = error
    }

    fun passwordError(error : String){
        loginPatientBinding.editTextLoginPassword.requestFocus()
        loginPatientBinding.editTextLoginPassword.error = error
    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginPatientFragment().apply {

            }
    }
}
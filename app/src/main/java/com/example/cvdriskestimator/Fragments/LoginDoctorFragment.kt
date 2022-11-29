package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.databinding.FragmentDoctorLoginBinding
import com.example.cvdriskestimator.viewModels.LoginDoctorViewModel
import com.example.cvdriskestimator.viewModels.LoginDoctorViewModelFactory
import com.example.cvdriskestimator.viewModels.LoginPatientViewModelFactory
import com.example.cvdriskestimator.viewModels.LoginPatientViewModel


class LoginDoctorFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private lateinit var loginDoctorViewModel: LoginDoctorViewModel
    private lateinit var loginDoctorBinding: FragmentDoctorLoginBinding
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
        loginDoctorBinding = FragmentDoctorLoginBinding.inflate(inflater, container , false)
        val factory  = LoginDoctorViewModelFactory()
        setSharedPrefs()
        //set the ViewModel
        loginDoctorViewModel = ViewModelProvider(this, factory)[LoginDoctorViewModel::class.java]
        loginDoctorBinding.loginDoctorViewModel = loginDoctorViewModel
        loginDoctorBinding.lifecycleOwner = this
        loginDoctorViewModel.setActivity(mainActivity)
        loginDoctorViewModel.setFragment(this)
        setTitleBarListener()
//        setUserIconDimens()
        loginDoctorBinding.loginSbmBtn.setOnClickListener {
            loginDoctorViewModel.loginUser()
        }
        loginDoctorBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

        loginDoctorBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        return loginDoctorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popUpMenuComp = PopUpMenu(loginDoctorBinding.includePopUpMenu.termsRelLayout , mainActivity, this, null, registerFragment , null ,leaderBoardFragment)

        loginDoctorBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenuComp.showPopUp(loginDoctorBinding.includeCvdTitleForm.userIcon)
        }

        loginDoctorBinding.includeCvdTitleForm.userIcon.alpha = 1f

        loginDoctorBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleHieght = loginDoctorBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

        loginDoctorBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        loginDoctorBinding.formConLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }
    }

    private fun setSharedPrefs(){
        mainActivity.getPreferences(Context.MODE_PRIVATE).getString("MSG", "CVDRisk Estimator")
    }

    private  fun setTitleBarListener()
    {
        loginDoctorBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }


    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        var widthPixel = displayMetrics.widthPixels

        loginDoctorBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        loginDoctorBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        loginDoctorBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        loginDoctorBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun userNameError(error : String)
    {
        loginDoctorBinding.userNameLoginEditText.requestFocus()
        loginDoctorBinding.userNameLoginEditText.error = error
    }

    fun passwordError(error : String){
        loginDoctorBinding.editTextLoginPassword.requestFocus()
        loginDoctorBinding.editTextLoginPassword.error = error
    }


    private fun showTermsOfUseLayout() {

        loginDoctorBinding.includePopUpMenu.termsRelLayout.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        loginDoctorBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginDoctorFragment().apply {

            }
    }
}
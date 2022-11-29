package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.databinding.FragmentLoginBinding
import com.example.cvdriskestimator.viewModels.LoginPatientViewModel
import com.example.cvdriskestimator.viewModels.LoginPatientViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private lateinit var loginPatientViewModel: LoginPatientViewModel
    private lateinit var loginBinding: FragmentLoginBinding
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
        loginBinding = FragmentLoginBinding.inflate(inflater, container , false)
        val factory  = LoginPatientViewModelFactory()
        setSharedPrefs()
        //set the ViewModel
        loginPatientViewModel = ViewModelProvider(this, factory)[LoginPatientViewModel::class.java]
        loginBinding.loginViewModel = loginPatientViewModel
        loginBinding.lifecycleOwner = this
        loginPatientViewModel.setActivity(mainActivity)
        loginPatientViewModel.setFragment(this)
        setTitleBarListener()
//        setUserIconDimens()
        loginBinding.loginSbmBtn.setOnClickListener {
            loginPatientViewModel.loginUser()
        }
        loginBinding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

        loginBinding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE

        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popUpMenuComp = PopUpMenu(loginBinding.includePopUpMenu.termsRelLayout , mainActivity, this, null, registerFragment , null ,  leaderBoardFragment)

        loginBinding.includeCvdTitleForm.userIcon.setOnClickListener {
            popUpMenuComp.showPopUp(loginBinding.includeCvdTitleForm.userIcon)
        }

        loginBinding.includeCvdTitleForm.userIcon.alpha = 1f

        loginBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleHieght = loginBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

        loginBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }

        loginBinding.includePopUpMenu.closeBtn.setOnClickListener {
            hideTermsOfUseLayout()
        }

        loginBinding.formConLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }
    }

    private fun setSharedPrefs(){
        mainActivity.getPreferences(Context.MODE_PRIVATE).getString("MSG", "CVDRisk Estimator")
    }

    private  fun setTitleBarListener()
    {
        loginBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }

    fun backToActivity() {
        mainActivity.setLogOutUI()
    }

    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        var widthPixel = displayMetrics.widthPixels

        loginBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        loginBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        loginBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        loginBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun userNameError(error : String)
    {
        loginBinding.userNameEditText.requestFocus()
        loginBinding.userNameEditText.setError(error)
    }

    fun passwordError(error : String){
        loginBinding.editTextPassword.requestFocus()
        loginBinding.editTextPassword.setError(error)
    }


    private fun showTermsOfUseLayout() {

        loginBinding.includePopUpMenu.termsRelLayout.visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        loginBinding.includePopUpMenu.termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {

            }
    }
}
package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.databinding.FragmentDoctorRegisterBinding
import com.example.cvdriskestimator.viewModels.RegisterDoctorViewModel
import com.example.cvdriskestimator.viewModels.RegisterDoctorViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterDoctorFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var registerDoctorBinding: FragmentDoctorRegisterBinding
    private lateinit var registerViewModel: RegisterDoctorViewModel
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


        // Inflate the layout for this fragment
        registerDoctorBinding = FragmentDoctorRegisterBinding.inflate(inflater , container, false)


        val factory = RegisterDoctorViewModelFactory()
        setSharedPrefs()
//        setTitleFormListener()
        registerViewModel = ViewModelProvider(this , factory)[RegisterDoctorViewModel::class.java]
        registerDoctorBinding.registerDoctorViewModel = registerViewModel
        registerDoctorBinding.lifecycleOwner = this
        registerViewModel.passFragment(this)
        registerViewModel.passActivity(mainActivity)
        registerViewModel.initRealm()
//        setUserIconDimens()
        userNameError(resources.getString(R.string.username_error))
        passwordError(resources.getString(R.string.password_error))
        registerDoctorBinding.inclusiveConLayout.findViewById<Button>(R.id.registerSbmBtn).setOnClickListener {

            registerViewModel.insertOrUpdatePatient()
        }




        return registerDoctorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginFragment = LoginFragment.newInstance()
//        popupMenuComp = PopUpMenu(registerDoctorBinding.inclusiveConLayout.getViewById(R.id.termsRelLayout) as RelativeLayout, mainActivity, this, loginFragment , null , this , leaderBoardFragment)

//        registerDoctorBinding.includeCvdTitleForm.userIcon.setOnClickListener {
//            popupMenuComp.showPopUp(registerDoctorBinding.includeCvdTitleForm.userIcon)
//        }

        registerDoctorBinding.includeCvdTitleForm.userIcon.alpha = 0f

        registerDoctorBinding.includeCvdTitleForm.cvdTitleForm.post {
            mteTitleHeight = registerDoctorBinding.includeCvdTitleForm.cvdTitleForm.layoutParams.height
        }

        registerDoctorBinding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.setContentViewForFirstAppScreen()
        }


    }

    private fun setSharedPrefs(){
        mainActivity.getPreferences(Context.MODE_PRIVATE).getString("MSG", "CVDRisk Estimator")
    }

    private fun setTitleFormListener()
    {
        registerDoctorBinding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()
        }
    }


    private fun setUserIconDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthPixel = displayMetrics.widthPixels

        registerDoctorBinding.includeCvdTitleForm.covarianceLogo.layoutParams.width = widthPixel / 10
        registerDoctorBinding.includeCvdTitleForm.covarianceLogo.layoutParams.height = widthPixel / 12


        registerDoctorBinding.includeCvdTitleForm.userIcon.layoutParams.width = widthPixel / 10
        registerDoctorBinding.includeCvdTitleForm.userIcon.layoutParams.height = widthPixel / 10
    }



    fun userNameError(error : String)
    {
        registerDoctorBinding.inclusiveConLayout.findViewById<EditText>(R.id.userNameRegisterEditText).requestFocus()
        (registerDoctorBinding.inclusiveConLayout.findViewById(R.id.userNameRegisterEditText) as EditText).error = error
    }

    fun passwordError(error : String){
        (registerDoctorBinding.inclusiveConLayout.findViewById<EditText>(R.id.editTextRegisterPassword) as EditText).requestFocus()
        (registerDoctorBinding.inclusiveConLayout.findViewById(R.id.editTextRegisterPassword) as EditText).error = error
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    private fun showTermsOfUseLayout() {

        registerDoctorBinding.inclusiveConLayout.getViewById(R.id.termsRelLayout).visibility = View.VISIBLE
    }

    private fun hideTermsOfUseLayout() {
        registerDoctorBinding.inclusiveConLayout.getViewById(R.id.termsRelLayout).visibility = View.GONE
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
            RegisterDoctorFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
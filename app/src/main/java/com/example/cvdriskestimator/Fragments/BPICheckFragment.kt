package com.example.cvdriskestimator.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Patient
import com.example.cvdriskestimator.databinding.FragmentBPICheckBinding
import com.example.cvdriskestimator.viewModels.CheckBPIPatientViewModel
import com.example.cvdriskestimator.viewModels.CheckPatientBPIViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BPICheckFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BPICheckFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentBPICheckBinding
    private lateinit var bpiPatientViewModelFactory: CheckPatientBPIViewModelFactory
    private lateinit var bpiPatientViewModel: CheckBPIPatientViewModel
    private lateinit var popUpMenu : PopUpMenu
    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var leaderBoardFragment: LeaderBoardFragment
    private  var allPatientAnswers = arrayListOf<Int?>(1 ,1 ,1, 1, 1, 1, 1, 1, 1, 1, 1 , 1, 1)
    private lateinit var showCircle : Animation
    private var screenWidth : Float = 0f
    private var screenHeight : Float = 0f
    private var redCircleX : Float? = null
    private var redCircleY : Float? = null
    private var CircleCoordinates = arrayListOf<Float?>(0f, 0f)
    private  var TempuserInitialised = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //set the binding instance
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_b_p_i_check , container, false)
        bpiPatientViewModelFactory = CheckPatientBPIViewModelFactory()
        bpiPatientViewModel = CheckBPIPatientViewModel()
        bpiPatientViewModel = ViewModelProviders.of(this).get(bpiPatientViewModel::class.java)
        binding.lifecycleOwner = this
        binding.checkBPIPatientViewModel = bpiPatientViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initRadioGroups()
    }


    private fun initUI()
    {
        bpiPatientViewModel.passActivity(mainActivity)
        bpiPatientViewModel.passFragmentInstance(this)
        bpiPatientViewModel.initRealm(mainActivity.applicationContext)
        showCircle = AnimationUtils.loadAnimation(mainActivity.applicationContext , R.anim.show_circle)
        binding.includeCvdTitleForm.userIcon.alpha = 1f
        binding.includeCvdTitleForm.MTEConLayout.setOnClickListener {
            mainActivity.backToActivity()
        }
        binding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
        binding.includePopUpMenu.termsRelLayout.setOnClickListener {
            binding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
        }
        val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")

        Log.d("THREAD" , Thread.currentThread().name)
        if (username != "tempUser")
        {
            bpiPatientViewModel.setPatientDataOnForm(username!!)
        }
        else
        {
            bpiPatientViewModel.initPatientData()
        }
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        leaderBoardFragment = LeaderBoardFragment.newInstance()
        popUpMenu = PopUpMenu(binding.includePopUpMenu.termsRelLayout , mainActivity , this , loginFragment , registerFragment , leaderBoardFragment)
         binding.includeCvdTitleForm.userIcon.setOnClickListener {
             popUpMenu.showPopUp(it)
        }

        redCircleX = binding.redCircleImgV.x
        redCircleY = binding.redCircleImgV.y
        CircleCoordinates.add(redCircleY!!)
        CircleCoordinates.add(redCircleY!!)

        binding.humanBodyImgV.setOnTouchListener { v, event ->
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                var xCoordinate = event!!.x
                var yCoordinate = event!!.y
                getSceenDimensions()
                var xOffset = screenWidth / 9
                var yOffset = screenHeight / 14

                var imageX = binding.humanBodyImgV.x
                var imageY = binding.humanBodyImgV.y
                var circle = binding.redCircleImgV
                circle.x = imageX + xCoordinate + circle.width / 2 - xOffset
                circle.y = imageY + yCoordinate + circle.width / 2 - yOffset
                circle.animate().alphaBy(1f).duration = 500
                redCircleX = circle.x
                redCircleY = circle.y
                circle.startAnimation(showCircle)
            }
            true
        }

        binding.clearBtn.setOnClickListener {
            AlertDialog.Builder(this.activity)
                .setTitle("Clear All Data")
                .setMessage("Are you sure you want to delete the user data?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation

                        initialiseFormData()

                    })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

        binding.submitBtn.setOnClickListener {

            allPatientAnswers[0] = null
            if (checkRadioButtonId(binding.g2RGa.checkedRadioButtonId , binding.g2RGa))
            {
                allPatientAnswers[0] = getAnswerFromRadioGroupA(binding.g2RGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g2RGb.checkedRadioButtonId , binding.g2RGb))
                {
                    allPatientAnswers[0] = getAnswerFromRadioGroupB(binding.g2RGb)
                }
            }

            allPatientAnswers[1] = null
            if (checkRadioButtonId(binding.q3RGa.checkedRadioButtonId , binding.q3RGa))
            {
                allPatientAnswers[1] = getAnswerFromRadioGroupA(binding.q3RGa)
            }
            else
            {
                if (checkRadioButtonId(binding.q3RGb.checkedRadioButtonId , binding.q3RGb))
                {
                    allPatientAnswers[1] = getAnswerFromRadioGroupB(binding.q3RGb)
                }
            }

            allPatientAnswers[2] = null
            if (checkRadioButtonId(binding.q4aRG.checkedRadioButtonId , binding.q4aRG))
            {
                allPatientAnswers[2] = getAnswerFromRadioGroupA(binding.q4aRG)
            }
            else
            {
                if (checkRadioButtonId(binding.q4RGb.checkedRadioButtonId , binding.q4RGb))
                {
                    allPatientAnswers[2] = getAnswerFromRadioGroupB(binding.q4RGb)
                }
            }

            allPatientAnswers[3] = null
            if (checkRadioButtonId(binding.q5RGa.checkedRadioButtonId , binding.q5RGa))
            {
                allPatientAnswers[3] = getAnswerFromRadioGroupA(binding.q5RGa)
            }
            else
            {
                if (checkRadioButtonId(binding.q5RGb.checkedRadioButtonId , binding.q5RGb))
                {
                    allPatientAnswers[3] = getAnswerFromRadioGroupB(binding.q5RGb)
                }
            }

            allPatientAnswers[4] = null
            if (checkRadioButtonId(binding.q6RGa.checkedRadioButtonId , binding.q6RGa))
            {
                allPatientAnswers[4] = getAnswerFromRadioGroupA(binding.q6RGa)
            }
            else
            {
                if (checkRadioButtonId(binding.q6RGb.checkedRadioButtonId , binding.q6RGb))
                {
                    allPatientAnswers[4] = getAnswerFromRadioGroupB(binding.q6RGb)
                }
            }

            allPatientAnswers[5] = null
            if (checkRadioButtonId(binding.g7aRGa.checkedRadioButtonId , binding.g7aRGa))
            {
                allPatientAnswers[5] = getAnswerFromRadioGroupA(binding.g7aRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7aRGb.checkedRadioButtonId , binding.g7aRGb))
                {
                    allPatientAnswers[5] = getAnswerFromRadioGroupB(binding.g7aRGb)
                }
            }

            allPatientAnswers[6] = null
            if (checkRadioButtonId(binding.g7bRGa.checkedRadioButtonId , binding.g7bRGa))
            {
                allPatientAnswers[6] = getAnswerFromRadioGroupA(binding.g7bRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7bRGb.checkedRadioButtonId , binding.g7bRGb))
                {
                    allPatientAnswers[6] = getAnswerFromRadioGroupB(binding.g7bRGb)
                }
            }

            allPatientAnswers[7] = null
            if (checkRadioButtonId(binding.g7cRGa.checkedRadioButtonId , binding.g7cRGa))
            {
                allPatientAnswers[7] = getAnswerFromRadioGroupA(binding.g7cRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7cRGb.checkedRadioButtonId , binding.g7cRGb))
                {
                    allPatientAnswers[7] = getAnswerFromRadioGroupB(binding.g7cRGb)
                }
            }

            allPatientAnswers[8] = null
            if (checkRadioButtonId(binding.g7dRGa.checkedRadioButtonId , binding.g7dRGa))
            {
                allPatientAnswers[8] = getAnswerFromRadioGroupA(binding.g7dRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7dRGb.checkedRadioButtonId , binding.g7dRGb))
                {
                    allPatientAnswers[8] = getAnswerFromRadioGroupB(binding.g7dRGb)
                }
            }

            allPatientAnswers[9] = null
            if (checkRadioButtonId(binding.g7eRGa.checkedRadioButtonId , binding.g7eRGa))
            {
                allPatientAnswers[9] = getAnswerFromRadioGroupA(binding.g7eRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7eRGb.checkedRadioButtonId , binding.g7eRGb))
                {
                    allPatientAnswers[9] = getAnswerFromRadioGroupB(binding.g7eRGb)
                }
            }

            allPatientAnswers[10] = null
            if (checkRadioButtonId(binding.g7fRGa.checkedRadioButtonId , binding.g7fRGa))
            {
                allPatientAnswers[10] = getAnswerFromRadioGroupA(binding.g7fRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7fRGb.checkedRadioButtonId , binding.g7fRGb))
                {
                    allPatientAnswers[10] = getAnswerFromRadioGroupB(binding.g7fRGb)
                }
            }

            allPatientAnswers[11] = null
            if (checkRadioButtonId(binding.g7gRGa.checkedRadioButtonId , binding.g7gRGa))
            {
                allPatientAnswers[11] = getAnswerFromRadioGroupA(binding.g7gRGa)
            }
            else
            {
                if (checkRadioButtonId(binding.g7gRGb.checkedRadioButtonId , binding.g7gRGb))
                {
                    allPatientAnswers[11] = getAnswerFromRadioGroupB(binding.g7gRGb)
                }
            }



            bpiPatientViewModel.checkBPITestPAtient(allPatientAnswers, CircleCoordinates)
        }

        bpiPatientViewModel.patientData.observe(viewLifecycleOwner) {
            setPatientDataOnForm(it!!)
        }
    }

    fun setPatientInitialisedStatus(status : Boolean)
    {
        TempuserInitialised = status
    }

    private fun drawCircleOnHumanBody(x : Float , y : Float , radius : Float, canvasBitmap: Bitmap)
    {
        val canvas = Canvas(canvasBitmap)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(x , y , radius , paint)
        binding.humanBodyImgV.invalidate()
    }

    private fun initRadioGroups()
    {

        setRadioButtonOnClickListener(binding.bpiA1Rb0 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb1 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb2 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb3 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb4 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb5 , binding.g2RGb)
        setRadioButtonOnClickListener(binding.bpiA1Rb6 , binding.g2RGa)
        setRadioButtonOnClickListener(binding.bpiA1Rb7 , binding.g2RGa)
        setRadioButtonOnClickListener(binding.bpiA1Rb8 , binding.g2RGa)
        setRadioButtonOnClickListener(binding.bpiA1Rb9 , binding.g2RGa)
        setRadioButtonOnClickListener(binding.bpiA1Rb10 , binding.g2RGa)

        setRadioButtonOnClickListener(binding.bpiA3Rb0 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb1 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb2 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb3 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb4 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb5 , binding.q3RGb)
        setRadioButtonOnClickListener(binding.bpiA3Rb6 , binding.q3RGa)
        setRadioButtonOnClickListener(binding.bpiA3Rb7 , binding.q3RGa)
        setRadioButtonOnClickListener(binding.bpiA3Rb8 , binding.q3RGa)
        setRadioButtonOnClickListener(binding.bpiA3Rb9 , binding.q3RGa)
        setRadioButtonOnClickListener(binding.bpiA3Rb10 , binding.q3RGa)

        setRadioButtonOnClickListener(binding.bpiA4Rb0 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb1 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb2 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb3 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb4 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb5 , binding.q4RGb)
        setRadioButtonOnClickListener(binding.bpiA4Rb6 , binding.q4aRG)
        setRadioButtonOnClickListener(binding.bpiA4Rb7 , binding.q4aRG)
        setRadioButtonOnClickListener(binding.bpiA4Rb8 , binding.q4aRG)
        setRadioButtonOnClickListener(binding.bpiA4Rb9 , binding.q4aRG)
        setRadioButtonOnClickListener(binding.bpiA4Rb10 , binding.q4aRG)

        setRadioButtonOnClickListener(binding.bpiA5Rb0 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb1 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb2 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb3 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb4 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb5 , binding.q5RGb)
        setRadioButtonOnClickListener(binding.bpiA5Rb6 , binding.q5RGa)
        setRadioButtonOnClickListener(binding.bpiA5Rb7 , binding.q5RGa)
        setRadioButtonOnClickListener(binding.bpiA5Rb8 , binding.q5RGa)
        setRadioButtonOnClickListener(binding.bpiA5Rb9 , binding.q5RGa)
        setRadioButtonOnClickListener(binding.bpiA5Rb10 , binding.q5RGa)

        setRadioButtonOnClickListener(binding.bpiA6Rb0 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb1 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb2 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb3 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb4 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb5 , binding.q6RGb)
        setRadioButtonOnClickListener(binding.bpiA6Rb6 , binding.q6RGa)
        setRadioButtonOnClickListener(binding.bpiA6Rb7 , binding.q6RGa)
        setRadioButtonOnClickListener(binding.bpiA6Rb8 , binding.q6RGa)
        setRadioButtonOnClickListener(binding.bpiA6Rb9 , binding.q6RGa)
        setRadioButtonOnClickListener(binding.bpiA6Rb10 , binding.q6RGa)

        setRadioButtonOnClickListener(binding.bpiA7aRb0 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb1 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb2 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb3 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb4 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb5 , binding.g7aRGb)
        setRadioButtonOnClickListener(binding.bpiA7aRb6 , binding.g7aRGa)
        setRadioButtonOnClickListener(binding.bpiA7aRb7 , binding.g7aRGa)
        setRadioButtonOnClickListener(binding.bpiA7aRb8 , binding.g7aRGa)
        setRadioButtonOnClickListener(binding.bpiA7aRb9 , binding.g7aRGa)
        setRadioButtonOnClickListener(binding.bpiA7aRb10 , binding.g7aRGa)

        setRadioButtonOnClickListener(binding.bpiA7bRb0 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb1 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb2 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb3 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb4 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb5 , binding.g7bRGb)
        setRadioButtonOnClickListener(binding.bpiA7bRb6 , binding.g7bRGa)
        setRadioButtonOnClickListener(binding.bpiA7bRb7 , binding.g7bRGa)
        setRadioButtonOnClickListener(binding.bpiA7bRb8 , binding.g7bRGa)
        setRadioButtonOnClickListener(binding.bpiA7bRb9 , binding.g7bRGa)
        setRadioButtonOnClickListener(binding.bpiA7bRb10 , binding.g7bRGa)

        setRadioButtonOnClickListener(binding.bpiA7cRb0 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb1 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb2 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb3 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb4 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb5 , binding.g7cRGb)
        setRadioButtonOnClickListener(binding.bpiA7cRb6 , binding.g7cRGa)
        setRadioButtonOnClickListener(binding.bpiA7cRb7 , binding.g7cRGa)
        setRadioButtonOnClickListener(binding.bpiA7cRb8 , binding.g7cRGa)
        setRadioButtonOnClickListener(binding.bpiA7cRb9 , binding.g7cRGa)
        setRadioButtonOnClickListener(binding.bpiA7cRb10 , binding.g7cRGa)

        setRadioButtonOnClickListener(binding.bpiA7dRb0 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb1 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb2 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb3 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb4 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb5 , binding.g7dRGb)
        setRadioButtonOnClickListener(binding.bpiA7dRb6 , binding.g7dRGa)
        setRadioButtonOnClickListener(binding.bpiA7dRb7 , binding.g7dRGa)
        setRadioButtonOnClickListener(binding.bpiA7dRb8 , binding.g7dRGa)
        setRadioButtonOnClickListener(binding.bpiA7dRb9 , binding.g7dRGa)
        setRadioButtonOnClickListener(binding.bpiA7dRb10 , binding.g7dRGa)

        setRadioButtonOnClickListener(binding.bpiA7eRb0 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb1 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb2 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb3 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb4 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb5 , binding.g7eRGb)
        setRadioButtonOnClickListener(binding.bpiA7eRb6 , binding.g7eRGa)
        setRadioButtonOnClickListener(binding.bpiA7eRb7 , binding.g7eRGa)
        setRadioButtonOnClickListener(binding.bpiA7eRb8 , binding.g7eRGa)
        setRadioButtonOnClickListener(binding.bpiA7eRb9 , binding.g7eRGa)
        setRadioButtonOnClickListener(binding.bpiA7eRb10 , binding.g7eRGa)

        setRadioButtonOnClickListener(binding.bpiA7fRb0 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb1 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb2 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb3 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb4 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb5 , binding.g7fRGb)
        setRadioButtonOnClickListener(binding.bpiA7fRb6 , binding.g7fRGa)
        setRadioButtonOnClickListener(binding.bpiA7fRb7 , binding.g7fRGa)
        setRadioButtonOnClickListener(binding.bpiA7fRb8 , binding.g7fRGa)
        setRadioButtonOnClickListener(binding.bpiA7fRb9 , binding.g7fRGa)
        setRadioButtonOnClickListener(binding.bpiA7fRb10 , binding.g7fRGa)

        setRadioButtonOnClickListener(binding.bpiA7gRb0 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb1 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb2 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb3 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb4 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb5 , binding.g7gRGb)
        setRadioButtonOnClickListener(binding.bpiA7gRb6 , binding.g7gRGa)
        setRadioButtonOnClickListener(binding.bpiA7gRb7 , binding.g7gRGa)
        setRadioButtonOnClickListener(binding.bpiA7gRb8 , binding.g7gRGa)
        setRadioButtonOnClickListener(binding.bpiA7gRb9 , binding.g7gRGa)
        setRadioButtonOnClickListener(binding.bpiA7gRb10 , binding.g7gRGa)

    }

    private fun setRadioButtonOnClickListener(radiobutton : RadioButton, radioGroup : RadioGroup)
    {
        radiobutton.setOnClickListener {
            radioGroup.clearCheck()
        }
    }


    private fun setPatientDataOnForm(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialiseFormData()
            if (patient.patientBPIQ1 != null) {
                if ((patient.patientBPIQ1!! >= 0) && (patient.patientBPIQ1!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g2RGa, patient.patientBPIQ1)
                }
                if ((patient.patientBPIQ1!! >= 6) && (patient.patientBPIQ1!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g2RGb, patient.patientBPIQ1)
                }

                if ((patient.patientBPIQ2!! >= 0) && (patient.patientBPIQ2!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.q3RGa, patient.patientBPIQ2)
                }
                if ((patient.patientBPIQ2!! >= 6) && (patient.patientBPIQ2!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.q3RGb, patient.patientBPIQ2)
                }

                if ((patient.patientBPIQ3!! >= 0) && (patient.patientBPIQ3!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.q4aRG, patient.patientBPIQ3)
                }
                if ((patient.patientBPIQ3!! >= 6) && (patient.patientBPIQ3!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.q4RGb, patient.patientBPIQ3)
                }

                if ((patient.patientBPIQ4!! >= 0) && (patient.patientBPIQ4!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.q5RGa, patient.patientBPIQ4)
                }
                if ((patient.patientBPIQ4!! >= 6) && (patient.patientBPIQ4!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.q5RGb, patient.patientBPIQ4)
                }

                if ((patient.patientBPIQ5!! >= 0) && (patient.patientBPIQ5!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.q6RGa, patient.patientBPIQ5)
                }
                if ((patient.patientBPIQ5!! >= 6) && (patient.patientBPIQ5!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.q6RGb, patient.patientBPIQ4)
                }

                if ((patient.patientBPIQ6!! >= 0) && (patient.patientBPIQ6!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7aRGa, patient.patientBPIQ6)
                }
                if ((patient.patientBPIQ6!! >= 6) && (patient.patientBPIQ6!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7aRGb, patient.patientBPIQ6)
                }

                if ((patient.patientBPIQ7!! >= 0) && (patient.patientBPIQ7!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7bRGa, patient.patientBPIQ7)
                }
                if ((patient.patientBPIQ7!! >= 6) && (patient.patientBPIQ7!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7bRGb, patient.patientBPIQ7)
                }

                if ((patient.patientBPIQ8!! >= 0) && (patient.patientBPIQ8!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7cRGa, patient.patientBPIQ8)
                }
                if ((patient.patientBPIQ8!! >= 6) && (patient.patientBPIQ8!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7cRGb, patient.patientBPIQ8)
                }

                if ((patient.patientBPIQ9!! >= 0) && (patient.patientBPIQ9!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7dRGa, patient.patientBPIQ9)
                }
                if ((patient.patientBPIQ9!! >= 6) && (patient.patientBPIQ9!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7dRGb, patient.patientBPIQ9)
                }

                if ((patient.patientBPIQ10!! >= 0) && (patient.patientBPIQ10!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7eRGa, patient.patientBPIQ10)
                }
                if ((patient.patientBPIQ10!! >= 6) && (patient.patientBPIQ10!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7eRGb, patient.patientBPIQ10)
                }

                if ((patient.patientBPIQ11!! >= 0) && (patient.patientBPIQ11!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7fRGa, patient.patientBPIQ11)
                }
                if ((patient.patientBPIQ11!! >= 6) && (patient.patientBPIQ11!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7fRGb, patient.patientBPIQ11)
                }

                if ((patient.patientBPIQ12!! >= 0) && (patient.patientBPIQ12!! <= 5)) {
                    setQuestionOfRadioGroupA(binding.g7gRGa, patient.patientBPIQ12)
                }
                if ((patient.patientBPIQ12!! >= 6) && (patient.patientBPIQ12!! <= 10)) {
                    setQuestionOfRadioGroupB(binding.g7gRGb, patient.patientBPIQ12)
                }
                mainActivity.runOnUiThread {
                    setRedCircePos(patient.patientBPIcircleX!!, patient.patientBPIcircleY!!)
                }
            }
        } , 1000)
    }

    private fun setQuestionOfRadioGroupA(rg : RadioGroup , patientValue : Int?)
    {
        when (patientValue)
        {
            0 -> {
                (rg.getChildAt(0) as RadioButton).isChecked = true
            }

            1 -> {
                (rg.getChildAt(1) as RadioButton).isChecked = true
            }

            2 -> {
                (rg.getChildAt(2) as RadioButton).isChecked = true
            }

            3 -> {
                (rg.getChildAt(3) as RadioButton).isChecked = true
            }

            4 -> {
                (rg.getChildAt(4) as RadioButton).isChecked = true
            }

            5 -> {
                (rg.getChildAt(5) as RadioButton).isChecked = true
            }
        }
    }


    private fun setQuestionOfRadioGroupB(rg : RadioGroup , patientValue : Int?)
    {
        when (patientValue)
        {

            6-> {
                (rg.getChildAt(6) as RadioButton).isChecked = true
            }

            7 -> {
                (rg.getChildAt(7) as RadioButton).isChecked = true
            }

            8 -> {
                (rg.getChildAt(8) as RadioButton).isChecked = true
            }

            9 -> {
                (rg.getChildAt(9) as RadioButton).isChecked = true
            }

            10 -> {
                (rg.getChildAt(10) as RadioButton).isChecked = true
            }
        }
    }

    private fun getAnswerFromRadioGroupA(rg : RadioGroup) : Int?
    {
        val answer = rg.checkedRadioButtonId

        if (rg[0].id == answer)
            return 0
        if (rg[1].id == answer)
            return 1
        if (rg[2].id == answer)
            return 2
        if (rg[3].id == answer)
            return 3
        if (rg[4].id == answer)
            return 4
        if (rg[5].id == answer)
            return 5
        else return null
    }

    private fun getAnswerFromRadioGroupB(rg : RadioGroup) : Int?
    {
        val answer = rg.checkedRadioButtonId

        if (rg[0].id == answer)
            return 6
        if (rg[1].id == answer)
            return 7
        if (rg[2].id == answer)
            return 8
        if (rg[3].id == answer)
            return 9
        if (rg[4].id == answer)
            return 10
        else return null
    }

    private fun checkRadioButtonId(id : Int , radioGroup : RadioGroup) : Boolean
    {
        return radioGroup.findViewById<RadioButton>(id) != null
    }



    private fun initialiseFormData()
    {
        binding.g2RGa.clearCheck()
        binding.g2RGb.clearCheck()
        binding.q3RGa.clearCheck()
        binding.q3RGb.clearCheck()
        binding.q4aRG.clearCheck()
        binding.q4RGb.clearCheck()
        binding.q5RGa.clearCheck()
        binding.q5RGb.clearCheck()
        binding.q6RGa.clearCheck()
        binding.q6RGb.clearCheck()
        binding.g7aRGa.clearCheck()
        binding.g7aRGb.clearCheck()
        binding.g7bRGa.clearCheck()
        binding.g7bRGb.clearCheck()
        binding.g7cRGa.clearCheck()
        binding.g7cRGb.clearCheck()
        binding.g7dRGa.clearCheck()
        binding.g7dRGb.clearCheck()
        binding.g7eRGa.clearCheck()
        binding.g7eRGb.clearCheck()
        binding.g7fRGa.clearCheck()
        binding.g7fRGb.clearCheck()
        binding.g7gRGa.clearCheck()
        binding.g7gRGb.clearCheck()

        binding.redCircleImgV.alpha = 0F
    }

    private fun setRedCircePos(CircleX : Float , CircleY : Float)
    {
        var circle = binding.redCircleImgV
        circle.x = CircleX
        circle.y = CircleY
        circle.animate().alphaBy(1f).duration = 500
        circle.startAnimation(showCircle)
        circle.invalidate()
    }

    fun setErrorOnForm(error : String, questionNo : Int)
    {
        Toast.makeText(mainActivity.applicationContext , error , Toast.LENGTH_LONG).show()
        when(questionNo)
        {
            1 -> {
                (binding.bpiQ1RelLayout.findViewById(R.id.bpi_q1_txtV) as TextView).requestFocus()
            }
            2 ->
            {
                (binding.bpiQ2RelLayout.findViewById(R.id.bpi_q2_txtV) as TextView).requestFocus()
            }
            3 ->
            {
                (binding.bpiQ3RelLayout.findViewById(R.id.bpi_q3_txtV) as TextView).requestFocus()
            }
            4 ->
            {
                (binding.bpiQ4RelLayout.findViewById(R.id.bpi_q4_txtV) as TextView).requestFocus()
            }
            5 ->
            {
                (binding.bpiQ5RelLayout.findViewById(R.id.bpi_q5_txtV) as TextView).requestFocus()
            }
            6 ->
            {
                (binding.bpiQ6RelLayout.findViewById(R.id.bpi_q6_txtV) as TextView).requestFocus()
            }
            7 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7a_txtV) as TextView).requestFocus()
            }
            8 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7b_txtV) as TextView).requestFocus()
            }
            9 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7c_txtV) as TextView).requestFocus()
            }
            10 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7d_txtV) as TextView).requestFocus()
            }
            11 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7e_txtV) as TextView).requestFocus()
            }
            12 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7f_txtV) as TextView).requestFocus()
            }
            13 ->
            {
                (binding.bpiQ7RelLayout.findViewById(R.id.bpi_q7g_txtV) as TextView).requestFocus()
            }
        }
    }

    fun hidePopPutermsLayout()
    {
        binding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity

    }

    private fun getSceenDimensions()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
        screenWidth = displayMetrics.widthPixels.toFloat()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *

         * @return A new instance of fragment BPICheckFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BPICheckFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
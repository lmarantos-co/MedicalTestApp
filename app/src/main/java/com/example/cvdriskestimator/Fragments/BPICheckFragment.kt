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
    private  var allPatientAnswers = arrayListOf<Int?>(1 ,1 ,1, 1, 1, 1, 1, 1, 1, 1, 1 , 1)
    private lateinit var showCircle : Animation
    private var screenWidth : Float = 0f
    private var screenHeight : Float = 0f


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
    }

    private fun initUI()
    {
        bpiPatientViewModel.passActivity(mainActivity)
        bpiPatientViewModel.passFragmentInstance(this)
        bpiPatientViewModel.initRealm(mainActivity.applicationContext)
        showCircle = AnimationUtils.loadAnimation(mainActivity.applicationContext , R.anim.show_circle)
        binding.includeCvdTitleForm.userIcon.alpha = 1f
        binding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
        binding.includePopUpMenu.termsRelLayout.setOnClickListener {
            binding.includePopUpMenu.termsRelLayout.visibility = View.INVISIBLE
        }
        val username = mainActivity.getPreferences(Context.MODE_PRIVATE).getString("userName", "tempUser")

        Log.d("THREAD" , Thread.currentThread().name)
        if (username == "tempUser")
            bpiPatientViewModel.initPatientData()
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        popUpMenu = PopUpMenu(binding.includePopUpMenu.termsRelLayout , mainActivity , this , loginFragment , registerFragment)
         binding.includeCvdTitleForm.userIcon.setOnClickListener {
             popUpMenu.showPopUp(it)
        }


        //listeners on form
        binding.includePopUpMenu.termsRelLayout.setOnClickListener {
            hidePopPutermsLayout()
        }

        binding.includePopUpMenu.closeBtn.setOnClickListener {
            hidePopPutermsLayout()
        }

        binding.includeCvdTitleForm.cvdTitleForm.setOnClickListener {
            mainActivity.backToActivity()

        }

        binding.humanBodyImgV.setOnTouchListener (object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
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
                    circle.startAnimation(showCircle)

                }
                return true
            }

        })

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

            if (getAnswerFromRadioGroupA(binding.g2RGa) != null)
                allPatientAnswers[0] = getAnswerFromRadioGroupA(binding.g2RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g2RGb) != null)
                    allPatientAnswers[0] = getAnswerFromRadioGroupB(binding.g2RGb)
                else
                {
                    allPatientAnswers[0] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.q3RGa) != null)
                allPatientAnswers[1] = getAnswerFromRadioGroupA(binding.q3RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.q3RGb) != null)
                    allPatientAnswers[1] = getAnswerFromRadioGroupB(binding.q3RGb)
                else
                {
                    allPatientAnswers[1] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.q4RGa) != null)
                allPatientAnswers[2] = getAnswerFromRadioGroupA(binding.q4RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.q4RGb) != null)
                    allPatientAnswers[2] = getAnswerFromRadioGroupB(binding.q4RGb)
                else
                {
                    allPatientAnswers[2] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.q5RGa) != null)
                allPatientAnswers[3] = getAnswerFromRadioGroupA(binding.q5RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.q5RGb) != null)
                    allPatientAnswers[3] = getAnswerFromRadioGroupB(binding.q5RGb)
                else
                {
                    allPatientAnswers[3] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.q6RGa) != null)
                allPatientAnswers[4] = getAnswerFromRadioGroupA(binding.q6RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.q6RGb) != null)
                    allPatientAnswers[4] = getAnswerFromRadioGroupB(binding.q6RGb)
                else
                {
                    allPatientAnswers[4] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7RGa) != null)
                allPatientAnswers[5] = getAnswerFromRadioGroupA(binding.g7RGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7aRGb) != null)
                    allPatientAnswers[5] = getAnswerFromRadioGroupB(binding.g7aRGb)
                else
                {
                    allPatientAnswers[5] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7bRGa) != null)
                allPatientAnswers[6] = getAnswerFromRadioGroupA(binding.g7bRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7bRGb) != null)
                    allPatientAnswers[6] = getAnswerFromRadioGroupB(binding.g7bRGb)
                else
                {
                    allPatientAnswers[6] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7cRGa) != null)
                allPatientAnswers[7] = getAnswerFromRadioGroupA(binding.g7cRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7cRGb) != null)
                    allPatientAnswers[7] = getAnswerFromRadioGroupB(binding.g7cRGb)
                else
                {
                    allPatientAnswers[7] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7dRGa) != null)
                allPatientAnswers[8] = getAnswerFromRadioGroupA(binding.g7dRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7dRGb) != null)
                    allPatientAnswers[8] = getAnswerFromRadioGroupB(binding.g7dRGb)
                else
                {
                    allPatientAnswers[8] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7eRGa) != null)
                allPatientAnswers[9] = getAnswerFromRadioGroupA(binding.g7eRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7eRGb) != null)
                    allPatientAnswers[9] = getAnswerFromRadioGroupB(binding.g7eRGb)
                else
                {
                    allPatientAnswers[9] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7fRGa) != null)
                allPatientAnswers[10] = getAnswerFromRadioGroupA(binding.g7fRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7fRGb) != null)
                    allPatientAnswers[10] = getAnswerFromRadioGroupB(binding.g7fRGb)
                else
                {
                    allPatientAnswers[10] = null
                }
            }

            if (getAnswerFromRadioGroupA(binding.g7gRGa) != null)
                allPatientAnswers[11] = getAnswerFromRadioGroupA(binding.g7gRGa)
            else
            {
                if (getAnswerFromRadioGroupB(binding.g7gRGb) != null)
                    allPatientAnswers[11] = getAnswerFromRadioGroupB(binding.g7gRGb)
                else
                {
                    allPatientAnswers[11] = null
                }
            }

            bpiPatientViewModel.checkBPITestPAtient(allPatientAnswers)
        }

        bpiPatientViewModel.patientData.observe(viewLifecycleOwner, {
            setPatientDataOnForm(it!!)
        })
    }

    private fun drawCircleOnHumanBody(x : Float , y : Float , radius : Float, canvasBitmap: Bitmap)
    {
        val canvas = Canvas(canvasBitmap)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(x , y , radius , paint)
        binding.humanBodyImgV.invalidate()
    }

    private fun setPatientDataOnForm(patient : Patient)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            initialiseFormData()
            if ((patient.patientBPIQ1!! >= 0) && (patient.patientBPIQ1!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g2RGa , patient.patientBPIQ1!!)
            }
            if ((patient.patientBPIQ1!! >= 6) && (patient.patientBPIQ1!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g2RGb , patient.patientBPIQ1!!)
            }
            if ((patient.patientBPIQ2!! >= 0) && (patient.patientBPIQ2!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.q3RGa , patient.patientBPIQ2!!)
            }
            if ((patient.patientBPIQ2!! >= 6) && (patient.patientBPIQ2!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.q3RGb , patient.patientBPIQ2!!)
            }
            if ((patient.patientBPIQ3!! >= 0) && (patient.patientBPIQ3!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.q4RGa , patient.patientBPIQ3!!)
            }
            if ((patient.patientBPIQ3!! >= 6) && (patient.patientBPIQ3!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.q4RGb , patient.patientBPIQ3!!)
            }
            if ((patient.patientBPIQ4!! >= 0) && (patient.patientBPIQ4!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.q5RGa , patient.patientBPIQ4!!)
            }
            if ((patient.patientBPIQ4!! >= 6) && (patient.patientBPIQ4!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.q5RGb , patient.patientBPIQ4!!)
            }
            if ((patient.patientBPIQ5!! >= 0) && (patient.patientBPIQ5!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.q6RGa , patient.patientBPIQ5!!)
            }
            if ((patient.patientBPIQ5!! >= 6) && (patient.patientBPIQ5!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.q6RGb , patient.patientBPIQ5!!)
            }
            if ((patient.patientBPIQ6!! >= 0) && (patient.patientBPIQ6!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7RGa , patient.patientBPIQ6!!)
            }
            if ((patient.patientBPIQ6!! >= 6) && (patient.patientBPIQ6!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7aRGb , patient.patientBPIQ6!!)
            }
            if ((patient.patientBPIQ7!! >= 0) && (patient.patientBPIQ7!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7bRGa , patient.patientBPIQ7!!)
            }
            if ((patient.patientBPIQ7!! >= 6) && (patient.patientBPIQ7!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7bRGb , patient.patientBPIQ7!!)
            }
            if ((patient.patientBPIQ8!! >= 0) && (patient.patientBPIQ8!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7cRGa , patient.patientBPIQ8!!)
            }
            if ((patient.patientBPIQ8!! >= 6) && (patient.patientBPIQ8!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7cRGb , patient.patientBPIQ8!!)
            }
            if ((patient.patientBPIQ9!! >= 0) && (patient.patientBPIQ9!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7dRGa , patient.patientBPIQ9!!)
            }
            if ((patient.patientBPIQ9!! >= 6) && (patient.patientBPIQ9!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7dRGb , patient.patientBPIQ9!!)
            }
            if ((patient.patientBPIQ10!! >= 0) && (patient.patientBPIQ10!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7eRGa , patient.patientBPIQ10!!)
            }
            if ((patient.patientBPIQ10!! >= 6) && (patient.patientBPIQ10!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7eRGb , patient.patientBPIQ10!!)
            }
            if ((patient.patientBPIQ11!! >= 0) && (patient.patientBPIQ11!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7fRGa , patient.patientBPIQ11!!)
            }
            if ((patient.patientBPIQ11!! >= 6) && (patient.patientBPIQ11!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7fRGb , patient.patientBPIQ11!!)
            }
            if ((patient.patientBPIQ12!! >= 0) && (patient.patientBPIQ12!! <= 5))
            {
                setQuestionOfRadioGroupA(binding.g7gRGa , patient.patientBPIQ12!!)
            }
            if ((patient.patientBPIQ12!! >= 6) && (patient.patientBPIQ12!! <= 10))
            {
                setQuestionOfRadioGroupB(binding.g7gRGb , patient.patientBPIQ12!!)
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
        var answer = rg.checkedRadioButtonId

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

        else return null
    }

    private fun initialiseFormData()
    {
        binding.g2RGa.clearCheck()
        binding.g2RGb.clearCheck()
        binding.q3RGa.clearCheck()
        binding.q4RGb.clearCheck()
        binding.q4RGa.clearCheck()
        binding.q4RGb.clearCheck()
        binding.q5RGa.clearCheck()
        binding.q5RGb.clearCheck()
        binding.q6RGa.clearCheck()
        binding.q6RGb.clearCheck()
        binding.g7RGa.clearCheck()
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
    }

    fun setErrorOnForm(error : String)
    {
        Toast.makeText(mainActivity.applicationContext , error , Toast.LENGTH_LONG).show()
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
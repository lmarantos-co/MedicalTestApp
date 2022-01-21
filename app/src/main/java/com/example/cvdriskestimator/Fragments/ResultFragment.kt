package com.example.cvdriskestimator.Fragments

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.palette.graphics.Palette
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import java.lang.reflect.Method


/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "risk_result"
private const val ARG_PARAM2 = "test_type"


class ResultFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private var riskResult: Double? = null
    private lateinit var testHeadling : TextView
    private lateinit var riskResultTxtV : TextView
    private lateinit var riskResultTxtVSum : TextView
    private lateinit var MTEtitle : ConstraintLayout
    private lateinit var menuConLayout : ConstraintLayout
    private lateinit var cvdTitleForm : RelativeLayout
    private lateinit var companyLogo : ImageView
    private lateinit var userIcon : ImageView
    private lateinit var testTitle : TextView
    private lateinit var cvdTestResults : TextView
    private lateinit var testResultInfoLayout : RelativeLayout
    private lateinit var ratingBarTitle : TextView
    private lateinit var lowRiskLayout : RelativeLayout
    private lateinit var borderRiskLayout : RelativeLayout
    private lateinit var intermediateRiskLayout : RelativeLayout
    private lateinit var highRiskLayout : RelativeLayout
    private lateinit var lowRiskView : TextView
    private lateinit var borderRiskLowTxtV : TextView
    private lateinit var intermediateRiskLowTxtV : TextView
    private lateinit var hightRIskHighTxtV : TextView

    private lateinit var diabetesRatingBar : RelativeLayout
    private lateinit var drawPercView : View
    private lateinit var upPerc : TextView
    private lateinit var resultPerc : TextView
    private lateinit var bottomPerc : TextView
    private var ratingBarHegiht : Int = 0
    private var mteTitleHeight : Int = 0

    private lateinit var newConstraintSet: ConstraintSet
    private lateinit var palette : Palette

    private  var loginFragment: LoginFragment = LoginFragment.newInstance()
    private  var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private lateinit var popupMenuComp : PopUpMenu
    private lateinit var closeBtn : Button
    private lateinit var formConLayout : ConstraintLayout
    private lateinit var termsRelLayout : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            riskResult = it.getDouble(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val actionBar: androidx.appcompat.app.ActionBar? = mainActivity.supp+ortActionBar
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
        // Inflate the layout for this fragment
        var view = View(mainActivity.applicationContext)
        val test_type : Int = arguments!!.getInt(ARG_PARAM2)
        if (test_type == 1)
        {
            view = inflater.inflate(R.layout.fragment_result, container, false)
            formConLayout = view.findViewById(R.id.results_constraint_layout)
            testHeadling = view.findViewById(R.id.loginTitleTxtV2)
            riskResultTxtV = view.findViewById(R.id.txtVTestResultTitle)
            riskResultTxtVSum = view.findViewById(R.id.txtViewTestResultSummary)
            cvdTestResults = view.findViewById(R.id.txtVcvdTestDetails)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testTitle = view.findViewById(R.id.txtVTestResultTitle)
            testResultInfoLayout = view.findViewById(R.id.testResultInfoRelLayout)
            ratingBarTitle = view.findViewById(R.id.ratingBarTitle)
            highRiskLayout = view.findViewById(R.id.hightRiskRelLayout)
            intermediateRiskLayout = view.findViewById(R.id.intermediateRiskLRelLayout)
            borderRiskLayout = view.findViewById(R.id.borderRiskRelLayout)
            lowRiskLayout = view.findViewById(R.id.lowRiskRelLayout)
            lowRiskView = view.findViewById(R.id.lowRiskView)
            borderRiskLowTxtV = view.findViewById(R.id.borderRiskLowTxtV)
            intermediateRiskLowTxtV = view.findViewById(R.id.intermediateRisklowTxtV)
            hightRIskHighTxtV = view.findViewById(R.id.highRiskTxtV)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            return view
        }
        if (test_type == 2)
        {
            view = inflater.inflate(R.layout.fragment_result_alt, container, false)
            formConLayout = view.findViewById(R.id.results_constraint_layout_alt)
            testHeadling = view.findViewById(R.id.loginTitleTxtV2)
            riskResultTxtV = view.findViewById(R.id.txtVTestResultTitle)
            riskResultTxtVSum = view.findViewById(R.id.txtViewTestResultSummary)
            cvdTestResults = view.findViewById(R.id.txtVcvdTestDetails)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            diabetesRatingBar = view.findViewById(R.id.ratingBar)
            val viewTreeObserver: ViewTreeObserver = diabetesRatingBar.getViewTreeObserver()
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener {
                    ratingBarHegiht = diabetesRatingBar.getHeight()
                    drawDiabetesPercentage(arguments!!.getDouble(ARG_PARAM1))
                }
            }
            upPerc = view.findViewById(R.id.upPerc)
            resultPerc = view.findViewById(R.id.userResultPerc)
            bottomPerc = view.findViewById(R.id.bottomPerc)
            drawPercView = view.findViewById(R.id.drawPercView)
            testTitle = view.findViewById(R.id.txtVTestResultTitle)
            testResultInfoLayout = view.findViewById(R.id.testResultInfoRelLayout)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            return view
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvdTitleForm = MTEtitle.findViewById(R.id.cvdTitleForm)

        cvdTitleForm.post {
            mteTitleHeight = cvdTitleForm.layoutParams.height
        }

        MTEtitle.setOnClickListener {
            mainActivity.backToActivity()
        }

        menuConLayout.findViewById<Button>(R.id.closeBtn).setOnClickListener {
            hideTermsOfUseLayout()
        }

        val covLogoBitmap = BitmapFactory.decodeResource(resources , R.drawable.covariance_logo)
        //get the palette from the cov logo image
        palette = createPaletteSync(covLogoBitmap)
        setIconsDimens()

        popupMenuComp = PopUpMenu(termsRelLayout, mainActivity, this, loginFragment, registerFragment)

        userIcon.setOnClickListener {
            popupMenuComp.showPopUp(userIcon)
        }


        termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

        val test_type : Int = arguments!!.getInt(ARG_PARAM2)
        if (test_type == 1) {
            setFormColors(1)
            setRatingBarColors(1)
            testHeadling.setText(R.string.cvd_test_results)
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(mainActivity.getDrawable(R.drawable.ic_favorite_white_24dp) , null, null, null)
//            val resultString = getString(R.string.cvd_test_result) + " " + "<b>" +
//            String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1)) + " %." + "</b>"
            barFlashAnimation(arguments!!.getDouble(ARG_PARAM1))
//            riskResultTxtVSum.text = resources.getString(R.string.cvd_response_text)
            ratingBarTitle.setText("Result : " + String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1)) +" %")
            cvdTestResults.setTypeface(null , Typeface.ITALIC)
            cvdTestResults.setText(R.string.cvd_response_text)
        }
        if (test_type == 2) {
//            setIconsDimens()
            setFormColors(2)
            testHeadling.setText(R.string.diabetes_test_results)
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(mainActivity.getDrawable(R.drawable.blood_drop_24dp) , null, null, null)
            riskResultTxtV.setText("Result")
            var resultString = getString(R.string.diabetes_test_result)
            resultString = resultString + " " + "<b>" + String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1) * 100) + "</b>" + "% ."
            riskResultTxtVSum.text = Html.fromHtml(resultString)

        }
    }

    private fun setIconsDimens() {
        var displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        var widthPixels = displayMetrics.widthPixels

        companyLogo.layoutParams.width = widthPixels / 10
        companyLogo.layoutParams.height = widthPixels / 12


        userIcon.layoutParams.width = widthPixels / 10
        userIcon.layoutParams.height = widthPixels / 10
    }

    private fun setAltLLayout()
    {
        newConstraintSet = ConstraintSet()
        newConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_alt)
    }

    private fun setFormColors(testType : Int) {
        when (testType) {
            1 -> {
                ratingBarTitle.setBackgroundResource(R.drawable.cvd_test_relative_layout)
                testHeadling.setBackgroundResource(R.drawable.orange_cvd_title_textview)
                testResultInfoLayout.setBackgroundResource(R.drawable.cvdtest_relative_layout_style)
            }
            2 -> {
                testHeadling.setBackgroundResource(R.drawable.green_diabetes_form_title_style)
                testResultInfoLayout.setBackgroundResource(R.drawable.diabetes_relativelayout_style)
            }
        }
    }

    private fun createPaletteSync(bitmap  : Bitmap) : Palette
    {
        val logoBitmap = BitmapFactory.decodeResource(resources , R.drawable.covariance_logo)
        val builder = Palette.Builder(bitmap)
        palette = builder.generate()
        return palette
    }

    private fun setRatingBarColors(result : Int)
    {
        when(result)
        {
            1 ->
            {
                highRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                intermediateRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                borderRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                lowRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
            }
        }
    }


    private fun barFlashAnimation(percentage : Double) {
        if ((percentage >= 0) && (percentage <= 5)) {
            higlightWiningView(lowRiskLayout)
        }
        if ((percentage > 5) && (percentage <= 7.4)) {
            higlightWiningView(borderRiskLayout)
        }
        if ((percentage >= 7.5) && (percentage <= 19.9)) {
            higlightWiningView(intermediateRiskLayout)
        }
        if (percentage >= 20) {
            higlightWiningView(highRiskLayout)
        }
    }

    private fun higlightWiningView(view : RelativeLayout)
    {
        if ((view.id == R.id.borderRiskRelLayout) || (view.id == R.id.intermediateRiskLRelLayout))
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
        if (view.id == R.id.hightRiskRelLayout)
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
        if (view.id == R.id.lowRiskRelLayout)
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
    }

    private fun drawDiabetesPercentage(perc : Double)
    {
        var resultPercHeight = ratingBarHegiht * perc
        drawPercView.setBackgroundColor(mainActivity.resources.getColor(R.color.Red))
//        increaseViewSize(drawPercView , resultPercHeight.toInt())
        drawPercView.layoutParams.height = resultPercHeight.toInt()
//        resultPerc.y = drawPercView.y + drawPercView.layoutParams.height - resultPercHeight.toInt()
        resultPerc.layoutParams.height = drawPercView.layoutParams.height * 2
        //check if the bottomPerc gets invisible by the userResultPerc
        if (arguments!!.getDouble(ARG_PARAM1) * 100 < 10) {
            bottomPerc.visibility = View.INVISIBLE
            resultPerc.text = "  " + String.format("%.2f", arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }

        if (arguments!!.getDouble(ARG_PARAM1) * 100 >  90) {
            upPerc.visibility = View.INVISIBLE
            resultPerc.text = "  " + String.format("%.2f", arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }

        if ((arguments!!.getDouble(ARG_PARAM1) * 100 >= 10) && (arguments!!.getDouble(ARG_PARAM1) * 100 <= 90))
        {
            resultPerc.text = "  " + String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }
    }

    private fun increaseViewSize(view: View , heightIncr : Int) {
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight + heightIncr)
        valueAnimator.duration = 500L
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    private fun startViewFadeAnimation(view : RelativeLayout) {
        val animation: Animation = AlphaAnimation(1.0f, 0.0f)
        animation.interpolator = LinearInterpolator()
        animation.duration = 500
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        view.startAnimation(animation)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    private fun hideTermsOfUseLayout() {
        termsRelLayout.visibility = View.GONE
//        termsOFUseView.animate().scaleX(0f).scaleY(0f).setDuration(1000).withEndAction {
////            termsOFUseView.visibility = View.GONE
//        }

    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Double, param2: Int) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2 , param2)
                }
            }
    }
}
package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.os.FileUtils
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.style.TypefaceSpan
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.palette.graphics.Palette
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cvdriskestimator.CustomClasses.PopUpMenu
import com.example.cvdriskestimator.CustomClasses.customDerpesionProgressView
import kotlinx.coroutines.*
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.updateLayoutParams
import java.lang.Runnable
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "risk_result1"
private const val ARG_PARAM2 = "risk_result2"
private const val ARG_PARAM3 = "test_type"


class ResultFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var riskResult: Double? = null
    private lateinit var testHeadling: TextView
    private lateinit var riskResultTxtV: TextView
    private lateinit var riskResultTxtVSum: TextView
    private lateinit var MTEtitle: ConstraintLayout
    private lateinit var menuConLayout: ConstraintLayout
    private lateinit var cvdTitleForm: RelativeLayout
    private lateinit var companyLogo: ImageView
    private lateinit var userIcon: ImageView
    private lateinit var testTitle: TextView
    private lateinit var cvdTestResults: TextView
    private lateinit var testResultInfoLayout: RelativeLayout
    private lateinit var ratingBarTitle: TextView
    private lateinit var lowRiskLayout: RelativeLayout
    private lateinit var borderRiskLayout: RelativeLayout
    private lateinit var intermediateRiskLayout: RelativeLayout
    private lateinit var highRiskLayout: RelativeLayout
    private lateinit var lowRiskView: TextView
    private lateinit var borderRiskLowTxtV: TextView
    private lateinit var intermediateRiskLowTxtV: TextView
    private lateinit var hightRIskHighTxtV: TextView

    private lateinit var diabetesRatingBar: RelativeLayout
    private lateinit var diabetesBottomBar : RelativeLayout
    private lateinit var drawPercView: View
    private lateinit var upPerc: TextView
    private lateinit var resultPerc: TextView
    private lateinit var bottomPerc: TextView
    private var ratingBarHegiht: Int = 0
    private var mteTitleHeight: Int = 0

    private lateinit var newConstraintSet: ConstraintSet
    private lateinit var palette: Palette

    private var loginFragment: LoginFragment = LoginFragment.newInstance()
    private var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private lateinit var popupMenuComp: PopUpMenu
    private lateinit var closeBtn: ImageView
    private lateinit var formConLayout: ConstraintLayout
    private lateinit var termsRelLayout: RelativeLayout

    private lateinit var noDepressionLayout: RelativeLayout
    private lateinit var mildDepressionLayout: RelativeLayout
    private lateinit var moderateDepressionLayout: RelativeLayout
    private lateinit var servereDepressionLayout: RelativeLayout
    private lateinit var totalScore: TextView
    private lateinit var depressStatus: TextView
    private var noDepressionFormWidth: Int = 0
    private var mildDepressionFormWidth: Int = 0
    private var moderateDepressionFormWidth: Int = 0
    private var severeDepressionFormWidth: Int = 0
    private var depressionResultBarHeight: Int = 0
    private lateinit var depressionProgressBar: RelativeLayout

    private lateinit var baiTestSummary: TextView
    private lateinit var lowAnxietyRelLayout : RelativeLayout
    private lateinit var moderateAnxietyRelLayout : RelativeLayout
    private lateinit var severeAnxietyRelLayout : RelativeLayout

    //mds test score variables
    private lateinit var mdsTestScore : TextView
    private lateinit var mdsTestResult: TextView
    private lateinit var view1 : View
    private lateinit var view2 : View
    private lateinit var view3 : View
    private lateinit var view4 : View
    private lateinit var view5 : View
    private lateinit var view6 : View
    private lateinit var view7 : View
    private lateinit var view8 : View
    private lateinit var view9 : View
    private lateinit var view10 : View
    private lateinit var view11 : View

    //BPI Score Test Result variables
    private lateinit var pssTestScore : TextView
    private lateinit var pssTestResult : TextView
    private lateinit var pisTestScore : TextView
    private lateinit var pisTestResult : TextView
    private lateinit var bpiresultBar : RelativeLayout
    private lateinit var moderatePainBar : RelativeLayout

    private lateinit var lowResultView : View
    private lateinit var mildResultView : View
    private lateinit var moderateResultView : View
    private lateinit var severeResultView : View
    private lateinit var lowResultPointer : Guideline
    private lateinit var mildResultPointer : Guideline
    private lateinit var moderateResultPointer : Guideline
    private lateinit var severeResultPointer : Guideline
    private lateinit var newPainConstraintSet : ConstraintSet

    private var lowProgressViewHeight = 0
    private var mildProgressViewHeight = 0
    private var moderateProgressViewHeight = 0
    private var severeProgreessViewHeight = 0


    private lateinit var lowProgressBar : ProgressBar
    private lateinit var mildProgressBar : ProgressBar
    private lateinit var moderateProgressBar : ProgressBar
    private lateinit var severeProgressBar : ProgressBar

    private lateinit var totalPSSScore : TextView
    private lateinit var totalPISScore : TextView


    //screen dimensions
    var screenWidth: Int = 0
    var screenHeight: Int = 0

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

        getScreenDimens()
        val test_type: Int = arguments!!.getInt(ARG_PARAM3)
        if (test_type == 1) {
            view = inflater.inflate(R.layout.fragment_result, container, false)
            formConLayout = view.findViewById(R.id.results_constraint_layout)
            testHeadling = view.findViewById(R.id.testTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.txtVTestResultTitle)
            riskResultTxtVSum = view.findViewById(R.id.txtViewTestResultSummary)
            cvdTestResults = view.findViewById(R.id.txtVcvdTestDetails)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
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
        }
        if (test_type == 2) {
            view = inflater.inflate(R.layout.fragment_result_alt, container, false)
            formConLayout = view.findViewById(R.id.results_constraint_layout_alt)
            testHeadling = view.findViewById(R.id.testTitleTxtV)
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
            diabetesBottomBar = view.findViewById(R.id.bottomBar)
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
        }
        if (test_type == 3) {
            view = inflater.inflate(R.layout.fragment_result_mdi_test, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_mdi_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.mditestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.testResultTxtV)
            depressionProgressBar = RelativeLayout(mainActivity.applicationContext)
            depressionProgressBar = view.findViewById(R.id.resulVerticalProgressBar)
            noDepressionLayout = view.findViewById(R.id.noDepView)
            mildDepressionLayout = view.findViewById(R.id.mildDepView)
            moderateDepressionLayout = view.findViewById(R.id.moderateDepView)
            servereDepressionLayout = view.findViewById(R.id.severeDepView)
            totalScore = view.findViewById(R.id.totalScore)
            depressStatus = view.findViewById(R.id.depressionStatus)
            setMDIResultsOnForm(
                arguments!!.getDouble(ARG_PARAM1).toInt(),
                getMDIResult(arguments!!.getDouble(ARG_PARAM1).toInt())
            )

            noDepressionLayout.post {
                noDepressionFormWidth = noDepressionLayout.width
                depressionResultBarHeight = noDepressionLayout.height
            }


            mildDepressionLayout.post {
                mildDepressionFormWidth = mildDepressionLayout.width

            }


            moderateDepressionLayout.post {
                moderateDepressionFormWidth = moderateDepressionLayout.width
            }


            servereDepressionLayout.post {
                severeDepressionFormWidth = servereDepressionLayout.width
                GlobalScope.launch(Dispatchers.Main) {
                    createDepressionResultBarViews()
                }
            }
        }
        if (test_type == 4) {
            view = inflater.inflate(R.layout.fragment_result_bai_test, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_bpi_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.baiTestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.testResultTxtV)
            totalScore = view.findViewById(R.id.totalPSScore)
            val score = arguments!!.getDouble(ARG_PARAM1).toInt()
            totalScore.setText(score.toString())
            baiTestSummary = view.findViewById(R.id.BAItestSummary)
            lowAnxietyRelLayout = view.findViewById(R.id.lowAnxietyResultBar)
            moderateAnxietyRelLayout = view.findViewById(R.id.moderateAnxietyResultBar)
            severeAnxietyRelLayout = view.findViewById(R.id.severeAnxietyResultBar)
            setBaiTestSummary(score)
        }

        if (test_type == 5) {
            view = inflater.inflate(R.layout.fragment_result_mds_layout, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_bpi_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.mdsTestTitleTxtV)
            mdsTestScore = view.findViewById(R.id.totalPSScore)
            mdsTestResult = view.findViewById(R.id.totalScoreDescTxtV)
            val score = arguments!!.getDouble(ARG_PARAM1)
            mdsTestScore.text = (String.format(
                resources.getString(R.string.mds_test_score),
                String.format("%.2f", score.toFloat())
            ))
            mdsTestResult.text = getMDSResult(score.toInt())
            view1 = view.findViewById(R.id.view1)
            view2 = view.findViewById(R.id.view2)
            view3 = view.findViewById(R.id.view3)
            view4 = view.findViewById(R.id.view4)
            view5 = view.findViewById(R.id.view5)
            view6 = view.findViewById(R.id.view6)
            view7 = view.findViewById(R.id.view7)
            view8 = view.findViewById(R.id.view8)
            view9 = view.findViewById(R.id.view9)
            view10 = view.findViewById(R.id.view10)
            view11 = view.findViewById(R.id.view11)
        }
        if (test_type == 6) {
            view = inflater.inflate(R.layout.fragment_result_bpi_test, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_bpi_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.bpiTestTitleTxtV)
            pssTestScore = view.findViewById(R.id.totalPSScore)
            pssTestResult = view.findViewById(R.id.totalPSSScoreDescTxtV)
            pisTestScore = view.findViewById(R.id.totalPIScore)
            pisTestResult = view.findViewById(R.id.totalPISScoreDescTxtV)
            bpiresultBar = view.findViewById(R.id.resultBarView)
            moderatePainBar = view.findViewById(R.id.moderatePainRelLayout)

            lowResultView = view.findViewById(R.id.lowResultView)
            mildResultView = view.findViewById(R.id.mildResultView)
            moderateResultView = view.findViewById(R.id.moderateResultView)
            severeResultView = view.findViewById(R.id.severeResultView)
            lowResultPointer = view.findViewById(R.id.lowResultPointerGL)
            mildResultPointer = view.findViewById(R.id.mildResultPointerGL)
            moderateResultPointer = view.findViewById(R.id.moderateResultPointerGL)
            severeResultPointer = view.findViewById(R.id.severeResultPointerGL)

            lowProgressBar = view.findViewById(R.id.lowResultProgressBar)
            mildProgressBar = view.findViewById(R.id.mildResultProgressBar)
            moderateProgressBar = view.findViewById(R.id.moderateResultProgressBar)
            severeProgressBar = view.findViewById(R.id.severeResultProgressBar)

            totalPSSScore = view.findViewById(R.id.totalPSSScoreDescTxtV)
            totalPISScore = view.findViewById(R.id.totalPISScoreDescTxtV)

            formConLayout.post {
                lowProgressViewHeight = lowResultView.height
                mildProgressViewHeight = mildResultView.height
                moderateProgressViewHeight = moderateResultView.height
                severeProgreessViewHeight = severeResultView.height
            }

            //coroutine implementation of BPI Bar view
            var runningTest: Int = 0
            var counter : Int = 0
            GlobalScope.launch(Dispatchers.Main) {
                // Do something here on the main thread
                while (counter < 1000000) {
//                    createBPIResultBarViews(runningTest)
//                    showBPIProgressBar(runningTest)
//                    highlightPainViews(runningTest)
                    showBPIResultBArViews(runningTest)
                    counter++
                    runningTest = Math.floorMod(counter, 2)
                }
            }


// Create the Handler object (on the main thread by default)
//            var runningTest: Int = 0
//            var counter : Int = 0
//            val handler = Handler()
//            // Define the code block to be executed
//
//
//
//            val runnableCode: Runnable = object : Runnable {
//                override fun run() {
//                    // Do something here on the main thread
//                    createBPIResultBarViews(runningTest)
//                    counter ++
//                    runningTest = Math.floorMod(counter , 2)
//                    // Repeat this the same runnable code block again another 2 seconds
//                    // 'this' is referencing the Runnable object
//                    handler.postDelayed(this, 2000)
//                }
//            }
//            // Start the initial runnable task by posting through the handler
//            handler.post(runnableCode)
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

        menuConLayout.findViewById<ImageView>(R.id.closeBtn).setOnClickListener {
            hideTermsOfUseLayout()
        }

        termsRelLayout.visibility = View.INVISIBLE

        val covLogoBitmap = BitmapFactory.decodeResource(resources, R.drawable.covariance_logo)
        //get the palette from the cov logo image
        palette = createPaletteSync(covLogoBitmap)


        popupMenuComp =
            PopUpMenu(termsRelLayout, mainActivity, this, loginFragment, registerFragment)

        userIcon.setOnClickListener {
            popupMenuComp.showPopUp(userIcon)
        }


        termsRelLayout.setOnClickListener {
            hideTermsOfUseLayout()
        }

        val test_type: Int = arguments!!.getInt(ARG_PARAM3)
        if (test_type == 1) {
            setFormColors(1)
            setRatingBarColors(1)
            testHeadling.setText(R.string.cvd_test_results)
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(
                mainActivity.getDrawable(R.drawable.ic_favorite_black_24dp),
                null,
                null,
                null
            )
//            val resultString = getString(R.string.cvd_test_result) + " " + "<b>" +
//            String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1)) + " %." + "</b>"
            barFlashAnimation(arguments!!.getDouble(ARG_PARAM1))
//            riskResultTxtVSum.text = resources.getString(R.string.cvd_response_text)
            ratingBarTitle.setText(
                "Result : " + String.format(
                    "%.2f",
                    arguments!!.getDouble(ARG_PARAM1)
                ) + " %"
            )
            cvdTestResults.setTypeface(null, Typeface.ITALIC)
            cvdTestResults.setText(R.string.cvd_response_text)
        }
        if (test_type == 2) {
            setFormColors(2)
            testHeadling.setText(R.string.diabetes_test_results)
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(
                mainActivity.getDrawable(R.drawable.blood_drop_24dp),
                null,
                null,
                null
            )
            riskResultTxtV.setText("Result")
            var resultString = getString(R.string.diabetes_test_result)
            resultString = resultString + " " + "<b>" + String.format(
                "%.2f",
                arguments!!.getDouble(ARG_PARAM1) * 100
            ) + "</b>" + "% ."
            riskResultTxtVSum.text = Html.fromHtml(resultString)
        }
        if (test_type == 3) {
            setFormColors(3)
            testHeadling.setText("MDI Test")
            riskResultTxtV.setText("Result")
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(
                mainActivity.getDrawable(R.drawable.ic_depression_24),
                null,
                null,
                null
            )
        }
        if (test_type == 4) {
            setFormColors(4)
            testHeadling.setText("BAI Test")
            riskResultTxtV.setText("Result")
            testHeadling.setCompoundDrawablesWithIntrinsicBounds(
                mainActivity.getDrawable(R.drawable.ic_anxiety_24_black),
                null,
                null,
                null
            )
        }

        if (test_type == 5)
        {
            setFormColors(testType = 5)
            GlobalScope.launch(Dispatchers.Main)
            {
                setMDSTestBarColors(arguments!!.getDouble(ARG_PARAM1).toInt())
            }
        }
        if (test_type == 6)
        {
            setFormColors(testType = 6)
            pssTestScore.text = String.format("%.2f" , arguments!!.getDouble(ARG_PARAM1))
            pisTestScore.text = String.format("%.2f" , arguments!!.getDouble(ARG_PARAM2))
        }
    }

    private fun setAltLLayout() {
        newConstraintSet = ConstraintSet()
        newConstraintSet.clone(mainActivity.applicationContext, R.layout.fragment_result_alt)
    }

    private fun setFormColors(testType: Int) {
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
            3 -> {
                testHeadling.setBackgroundResource(R.drawable.purple_mditest_form_title_style)
                riskResultTxtV.setBackgroundResource(R.drawable.mdi_test_relative_layout)
            }
            4 -> {
                testHeadling.setBackgroundResource(R.drawable.blue_baitest_form_title_style)
                riskResultTxtV.setBackgroundResource(R.drawable.baitest_rel_layout)
            }
            5 ->
            {
                testHeadling.setBackgroundResource(R.drawable.yellow_mdstest_form_title_style)
            }
            6 ->
            {
                testHeadling.setBackgroundResource(R.drawable.red_bpi_form_style)
            }
        }
    }

    private fun createPaletteSync(bitmap: Bitmap): Palette {
        val builder = Palette.Builder(bitmap)
        palette = builder.generate()
        return palette
    }

    private fun setRatingBarColors(result: Int) {
        when (result) {
            1 -> {
                highRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                intermediateRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                borderRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
                lowRiskLayout.setBackgroundResource(R.drawable.white_middle_rellayout_style)
            }
        }
    }


    private fun barFlashAnimation(percentage: Double) {
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

    private fun higlightWiningView(view: RelativeLayout) {
        if ((view.id == R.id.borderRiskRelLayout) || (view.id == R.id.intermediateRiskLRelLayout))
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
        if (view.id == R.id.hightRiskRelLayout)
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
        if (view.id == R.id.lowRiskRelLayout)
            view.setBackgroundColor(resources.getColor(R.color.red_violet))
    }

    private fun drawDiabetesPercentage(perc: Double) {
        var resultPercHeight = ratingBarHegiht * perc
        drawPercView.setBackgroundColor(mainActivity.resources.getColor(R.color.Red))
//        increaseViewSize(drawPercView , resultPercHeight.toInt())
        drawPercView.layoutParams.height = resultPercHeight.toInt()
//        resultPerc.y = drawPercView.y + drawPercView.layoutParams.height - resultPercHeight.toInt()
        resultPerc.layoutParams.height = drawPercView.layoutParams.height * 2
        if (arguments!!.getDouble(ARG_PARAM1) * 100 < 4)
        {
            resultPerc.layoutParams.height = diabetesBottomBar.layoutParams.height
        }
        //check if the bottomPerc gets invisible by the userResultPerc
        if (arguments!!.getDouble(ARG_PARAM1) * 100 < 10) {
            bottomPerc.visibility = View.INVISIBLE
            resultPerc.text =
                "  " + String.format("%.2f", arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }

        if (arguments!!.getDouble(ARG_PARAM1) * 100 > 90) {
            upPerc.visibility = View.INVISIBLE
            resultPerc.text =
                "  " + String.format("%.2f", arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }

        if ((arguments!!.getDouble(ARG_PARAM1) * 100 >= 10) && (arguments!!.getDouble(ARG_PARAM1) * 100 <= 90)) {
            resultPerc.text =
                "  " + String.format("%.2f", arguments!!.getDouble(ARG_PARAM1) * 100) + " %"
        }
    }

    private fun getMDIResult(mdiResult: Int): String {
        if ((mdiResult >= 0) && (mdiResult < 20)) {
            return "No Depression"
        }
        if ((mdiResult >= 20) && (mdiResult <= 24)) {
            return "Mild depression"
        }
        if ((mdiResult >= 25) && (mdiResult <= 29)) {
            return "Moderate depression"
        }
        if (mdiResult >= 30) {
            return "Severe depression"
        }
        return ""
    }

    private fun setMDIResultsOnForm(mdiDepressionInt: Int, mdiDepressionType: String) {
        totalScore.text = Html.fromHtml("<b>" + mdiDepressionInt.toString() + "</b>")
        depressStatus.text = Html.fromHtml("<b>" + mdiDepressionType+ "</b>")
//        setMDIResultTextViewColors(mdiDepressionInt)
    }

    private fun setMDIResultTextViewColors(mdiDepressionInt: Int) {
        if ((mdiDepressionInt >= 0) && (mdiDepressionInt < 20)) {
            totalScore.setTextColor(resources.getColor(R.color.blue))
            depressStatus.setTextColor(resources.getColor(R.color.blue))
        }
        if ((mdiDepressionInt >= 20) && (mdiDepressionInt <= 24)) {
            totalScore.setTextColor(resources.getColor(R.color.green_5))
            depressStatus.setTextColor(resources.getColor(R.color.green_5))
        }
        if ((mdiDepressionInt >= 25) && (mdiDepressionInt <= 29)) {
            totalScore.setTextColor(resources.getColor(R.color.orange_5))
            depressStatus.setTextColor(resources.getColor(R.color.orange_5))
        }
        if (mdiDepressionInt >= 30) {
            totalScore.setTextColor(resources.getColor(R.color.dark_red))
            depressStatus.setTextColor(resources.getColor(R.color.dark_red))
        }
    }

    private fun getMDSResult(score : Int) : String
    {
        if ((0 >= score) && (score <= 22))
        {
            return (resources.getString(R.string.mds_test_result1))
        }
        if ((27.5 >= score) && (score <= 33))
        {
            return (resources.getString(R.string.mds_test_result2))
        }
        if ((38.5 >= score) && (score <= 44))
        {
            return (resources.getString(R.string.mds_test_result3))
        }
        if ((49.5 >= score) && (score <= 55))
        {
            return (resources.getString(R.string.mds_test_result4))
        }
        return ""
    }

    private suspend fun setMDSTestBarColors(score : Int)
    {
        if (score > 0)
        {
            view1.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 5)
        {
            view2.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 10)
        {
            view3.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 15)
        {
            view4.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 20)
        {
            view5.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 25)
        {
            view6.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 30)
        {
            view7.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 35)
        {
            view8.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 40)
        {
            view9.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 45)
        {
            view10.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
        if (score > 50)
        {
            view11.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
        }
    }

    //function to create Views above the result bar
    fun createDepressionResultBarViews() {

        depressionProgressBar.layoutParams.width = 0
        depressionProgressBar.layoutParams.height = (RelativeLayout.LayoutParams.WRAP_CONTENT)
        val viewWidth = (screenWidth  / 100) * 15
//        val viewHeight = (screenHeight / 51).toFloat() /2
        val viewHeight = screenHeight / 100
        val yDepressionResultBar = depressionProgressBar.y
        val xDepressionResultBar = depressionProgressBar.x
        val userMDIResult = (arguments!!.getDouble(ARG_PARAM1) * 1).toInt()


        val paint = Paint()
        var depResBarView = customDerpesionProgressView(
                        mainActivity.applicationContext,
                        xDepressionResultBar ,
                          yDepressionResultBar,
                        xDepressionResultBar + screenWidth,
                        yDepressionResultBar + (userMDIResult) * viewHeight.toFloat(),
                        paint    )

                depResBarView.layoutParams
        setProgresViewColor(depResBarView , userMDIResult)
        //add the created view to the relativelayout
                depResBarView.invalidate()
                depressionProgressBar.addView(
                    depResBarView,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
        depressionProgressBar.invalidate()

        val scale = ScaleAnimation(1f , 1f , 0f , 1f)
        scale.fillAfter = true
        scale.duration = 1000
        depressionProgressBar.startAnimation(scale)

    }


    suspend fun showBPIResultBArViews(runningResult: Int)
    {
        val userPSSResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
        val userPISResult = (arguments!!.getDouble(ARG_PARAM2) * 1)
        var barHeightSet = false
        when(runningResult)
        {
            0 ->
            {
                highlightBPiTextView(1)
                if (userPSSResult < 1)
                {
                    mainActivity.runOnUiThread {
                        var newLowViewHeight = userPSSResult.toFloat() * lowProgressViewHeight
                        var lowY = lowResultView.y
                        lowResultView.updateLayoutParams {
                            height = newLowViewHeight.toInt()
                        }
                        val lowLayoutParams = lowResultView.layoutParams as ConstraintLayout.LayoutParams
                        lowLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        lowResultView.y = lowY
                        barHeightSet =true
                        var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleAnimation.fillAfter = true
                        scaleAnimation.duration = 400
                        lowResultView.startAnimation(scaleAnimation)
                        scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                lowResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                }
                if ((userPSSResult == 1.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowResultView.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleAnimation.fillAfter = true
                    scaleAnimation.duration = 400
                    lowResultView.startAnimation(scaleAnimation)
                }
                if ((userPSSResult < 4) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newMildViewHeight : Float = (userPSSResult.toFloat() -1) / 3 * mildProgressViewHeight
                        var mildY = mildResultView.y
                        mildResultView.updateLayoutParams {
                            height = newMildViewHeight.toInt()
                        }
                        val mildLayoutParams = mildResultView.layoutParams as ConstraintLayout.LayoutParams
                        mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        mildResultView.y = mildY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {

                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                if ((userPSSResult == 4.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowResultView.alpha = 1f
                        mildResultView.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {

                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                if ((userPSSResult < 7) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userPSSResult.toFloat() - 4) / 3 * moderateProgressViewHeight
                        var modY = moderateResultView.y
                        moderateResultView.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val modLayoutParams = moderateResultView.layoutParams as ConstraintLayout.LayoutParams
                        modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        moderateResultView.y = modY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateResultView.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateResultView.alpha =1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                        }

                                        override fun onAnimationRepeat(p0: Animation?) {
                                        }

                                    })
                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                if ((userPSSResult == 7.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateResultView.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateResultView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                        }

                                        override fun onAnimationRepeat(p0: Animation?) {
                                        }

                                    })
                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                if ((userPSSResult < 10) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userPSSResult.toFloat() - 7) / 3 * severeProgreessViewHeight
                        var severeY = severeResultView.y
                        severeResultView.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val sevLayoutParams = severeResultView.layoutParams as ConstraintLayout.LayoutParams
                        sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        severeResultView.y = severeY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateResultView.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateResultView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleSevereAnimation.fillAfter = true
                                            scaleSevereAnimation.duration = 400
                                            severeResultView.startAnimation(scaleSevereAnimation)
                                            scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                                override fun onAnimationStart(p0: Animation?) {
                                                    severeResultView.alpha = 1f
                                                }

                                                override fun onAnimationEnd(p0: Animation?) {

                                                }

                                                override fun onAnimationRepeat(p0: Animation?) {
                                                }

                                            })
                                        }

                                        override fun onAnimationRepeat(p0: Animation?) {
                                        }

                                    })
                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                if ((userPSSResult == 10.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {

                    }
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowResultView.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildResultView.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateResultView.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateResultView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleSevereAnimation.fillAfter = true
                                            scaleSevereAnimation.duration = 400
                                            severeResultView.startAnimation(scaleSevereAnimation)
                                            scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    severeResultView.alpha = 1f
                                                }

                                                override fun onAnimationEnd(p0: Animation?) {
                                                }

                                                override fun onAnimationRepeat(p0: Animation?) {
                                                }

                                            })
                                        }

                                        override fun onAnimationRepeat(p0: Animation?) {
                                        }

                                    })
                                }

                                override fun onAnimationRepeat(p0: Animation?) {
                                }

                            })
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }
                delay(4000)
                lowResultView.alpha = 0f
                mildResultView.alpha = 0f
                moderateResultView.alpha = 0f
                severeResultView.alpha = 0f
            }
        1 ->
        {
            highlightBPiTextView(2)
            if (userPISResult < 1)
            {
                mainActivity.runOnUiThread {
                    var newLowViewHeight = userPISResult.toFloat() * lowProgressViewHeight
                    var lowY = lowResultView.y
                    lowResultView.updateLayoutParams {
                        height = newLowViewHeight.toInt()
                    }
                    val lowLayoutParams = lowResultView.layoutParams as ConstraintLayout.LayoutParams
                    lowLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    lowResultView.y = lowY
                    barHeightSet =true
                    var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleAnimation.fillAfter = true
                    scaleAnimation.duration = 400
                    lowResultView.startAnimation(scaleAnimation)
                    scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                        }

                        override fun onAnimationRepeat(p0: Animation?) {

                        }

                    })
                }

            }
            if ((userPISResult == 1.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    lowResultView.alpha = 1f
                }
                barHeightSet = true
                var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleAnimation.fillAfter = true
                scaleAnimation.duration = 400
                lowResultView.startAnimation(scaleAnimation)
            }
            if ((userPISResult < 4) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newMildViewHeight : Float = (userPISResult.toFloat() -1) / 3 * mildProgressViewHeight
                    var mildY = mildResultView.y
                    mildResultView.updateLayoutParams {
                        height = newMildViewHeight.toInt()
                    }
                    val mildLayoutParams = mildResultView.layoutParams as ConstraintLayout.LayoutParams
                    mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    mildResultView.y = mildY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)
                        scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {

                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            if ((userPISResult == 4.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    lowResultView.alpha = 1f
                    mildResultView.alpha = 1f
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)
                        scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {

                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            if ((userPISResult < 7) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newModViewHeight : Float = (userPISResult.toFloat() - 4) / 3 * moderateProgressViewHeight
                    var modY = moderateResultView.y
                    moderateResultView.updateLayoutParams{
                        height = newModViewHeight.toInt()
                    }
                    val modLayoutParams = moderateResultView.layoutParams as ConstraintLayout.LayoutParams
                    modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    moderateResultView.y = modY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateResultView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateResultView.alpha =1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {

                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            if ((userPISResult == 7.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateResultView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateResultView.alpha = 1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {

                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            if ((userPISResult < 10) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newModViewHeight : Float = (userPISResult.toFloat() - 7) / 3 * severeProgreessViewHeight
                    var severeY = severeResultView.y
                    severeResultView.updateLayoutParams{
                        height = newModViewHeight.toInt()
                    }
                    val sevLayoutParams = severeResultView.layoutParams as ConstraintLayout.LayoutParams
                    sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    severeResultView.y = severeY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateResultView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateResultView.alpha = 1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleSevereAnimation.fillAfter = true
                                        scaleSevereAnimation.duration = 400
                                        severeResultView.startAnimation(scaleSevereAnimation)
                                        scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                            override fun onAnimationStart(p0: Animation?) {
                                                severeResultView.alpha = 1f
                                            }

                                            override fun onAnimationEnd(p0: Animation?) {

                                            }

                                            override fun onAnimationRepeat(p0: Animation?) {
                                            }

                                        })
                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            if ((userPISResult == 10.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {

                }
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                lowResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        lowResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateResultView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateResultView.alpha = 1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleSevereAnimation.fillAfter = true
                                        scaleSevereAnimation.duration = 400
                                        severeResultView.startAnimation(scaleSevereAnimation)
                                        scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                            override fun onAnimationStart(p0: Animation?) {
                                                severeResultView.alpha = 1f
                                            }

                                            override fun onAnimationEnd(p0: Animation?) {

                                            }

                                            override fun onAnimationRepeat(p0: Animation?) {
                                            }

                                        })
                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }
            delay(4000)
            lowResultView.alpha = 0f
            mildResultView.alpha = 0f
            moderateResultView.alpha = 0f
            severeResultView.alpha = 0f
        }
        }

    }

    private fun highlightBPiTextView(textViewNo : Int)
    {
        when(textViewNo)
        {
            1 ->
            {   mainActivity.runOnUiThread {
                totalPSSScore.setTypeface(totalPSSScore.getTypeface(), android.graphics.Typeface.BOLD)
                totalPISScore.setTypeface(null , android.graphics.Typeface.NORMAL)
            }

            }
            2 ->
            {
                mainActivity.runOnUiThread {
                    totalPISScore.setTypeface(totalPISScore.getTypeface(), android.graphics.Typeface.BOLD)
                    totalPSSScore.setTypeface(null, android.graphics.Typeface.NORMAL)
                }

            }
        }
    }

    suspend fun showBPIProgressBar(runningResult : Int)
    {
        val userPSSResult = (arguments!!.getDouble(ARG_PARAM1) * 1).toInt()
        val userPISResult = (arguments!!.getDouble(ARG_PARAM2) * 1).toInt()
        when(runningResult) {
            0 -> {
            if (userPSSResult < 1) {
                var newPerc = (4 * userPSSResult.toFloat())
                    newPerc = newPerc / 4 * 100
                    lowProgressBar.alpha = 1f
                    drawProgressBar(newPerc, lowProgressBar)

            }
            if (userPSSResult == 1)
            {
                var newPerc = 100f
                lowProgressBar.alpha = 1f
                    drawProgressBar(newPerc, lowProgressBar)
            }
                if (userPSSResult < 4)
                {
                    var newPerc = (13 * (userPSSResult.toFloat() - 1) / (3))
                    newPerc = newPerc / 13 * 100
                    var lowPerc = 100f
                    lowProgressBar.alpha = 1f
                    mildProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(newPerc , mildProgressBar)
                }
                if (userPSSResult == 4)
                {
                    var newPerc = 100f
                    var lowPerc = 100f
                    lowProgressBar.alpha = 1f
                    mildProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(newPerc , mildProgressBar)
                }
                if (userPSSResult < 7)
                {
                    var newPerc = (12 * (userPSSResult.toFloat() - 4) / 3)
                    newPerc = (newPerc / 12) * 100
                    var lowPerc = 100f
                    var mildPerc = 100f
                    lowProgressBar.alpha =1f
                    mildProgressBar.alpha =1f
                    moderateProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(mildPerc , mildProgressBar)
                    drawProgressBar(newPerc , moderateProgressBar)
                }
                if (userPSSResult == 7)
                {
                    var newPerc = 100f
                    var lowPerc = 100f
                    var mildPerc = 100f
                    lowProgressBar.alpha =1f
                    mildProgressBar.alpha =1f
                    moderateProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(mildPerc , mildProgressBar)
                    drawProgressBar(newPerc , moderateProgressBar)
                }
                if (userPSSResult < 10)
                {
                    var modPerc = 100f
                    var lowPerc = 100f
                    var mildPerc = 100f
                    var newPerc = ((15 * (userPSSResult.toFloat() - 7) / 3))
                    newPerc = (newPerc / 15 ) * 100
                    lowProgressBar.alpha =1f
                    mildProgressBar.alpha =1f
                    moderateProgressBar.alpha =1f
                    severeProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(mildPerc , mildProgressBar)
                    drawProgressBar(modPerc , moderateProgressBar)
                    drawProgressBar(newPerc , severeProgressBar)
                }
                if (userPSSResult == 10)
                {
                    var modPerc = 100f
                    var lowPerc = 100f
                    var mildPerc = 100f
                    var sevPerc = 100f
                    lowProgressBar.alpha =1f
                    mildProgressBar.alpha =1f
                    moderateProgressBar.alpha =1f
                    severeProgressBar.alpha =1f
                    drawProgressBar(lowPerc , lowProgressBar)
                    drawProgressBar(mildPerc , mildProgressBar)
                    drawProgressBar(modPerc , moderateProgressBar)
                    drawProgressBar(sevPerc , severeProgressBar)
                }
        }
        }
    }

    fun drawProgressBar( progress : Float , progressBar : ProgressBar) {
        progressBar.setProgress(progress.toInt(), true)
    }


    suspend fun highlightPainViews(runningResult : Int)
    {
        val userPSSResult = (arguments!!.getDouble(ARG_PARAM1) * 1).toInt()
        val userPISResult = (arguments!!.getDouble(ARG_PARAM2) * 1).toInt()
        when(runningResult)
        {
            0 ->
            {
                coroutineScope {
                    if (userPSSResult < 1)
                    {
                        var newPerc  = ((4 * userPSSResult.toFloat() / 1  + 46) / 100)
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        mildResultView.alpha = 0f
                        mildResultView.invalidate()
                        moderateResultView.alpha = 0f
                        moderateResultView.invalidate()
                        severeResultView.alpha = 0f
                        severeResultView.invalidate()
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        newPainConstraintSet.connect(R.id.lowResultView , ConstraintSet.BOTTOM , R.id.lowResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()
                        var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleAnimation.fillAfter = true
                        scaleAnimation.duration = 300
                        lowResultView.startAnimation(scaleAnimation)
                    }
                    if (userPSSResult == 1)
                    {
                        var newPerc = (50f / 100f)
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        mildResultView.alpha = 0f
                        moderateResultView.alpha = 0f
                        severeResultView.alpha = 0f
                        mildResultView.invalidate()
                        moderateResultView.invalidate()
                        severeResultView.invalidate()
                        newPainConstraintSet.connect(R.id.lowResultView , ConstraintSet.BOTTOM , R.id.lowResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()
                        var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleAnimation.fillAfter = true
                        scaleAnimation.duration = 300
                        lowResultView.startAnimation(scaleAnimation)
                    }

                    if (userPSSResult < 4)
                    {
                        var newPerc = ((13 * (userPSSResult.toFloat() - 1) / (3) + 50) / 100)
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        lowResultView.alpha = 1f
                        moderateResultView.alpha = 0f
                        severeResultView.alpha = 0f
                        lowResultView.invalidate()
                        moderateResultView.invalidate()
                        severeResultView.invalidate()
                        newPainConstraintSet.connect(R.id.mildResultView , ConstraintSet.BOTTOM , R.id.mildResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()
                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }
                    if (userPSSResult == 4)
                    {
                        var newPerc = (63f / 100f ).toFloat()
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        lowResultView.alpha = 1f
                        moderateResultView.alpha = 0f
                        severeResultView.alpha = 0f
                        lowResultView.invalidate()
                        moderateResultView.invalidate()
                        severeResultView.invalidate()
                        newPainConstraintSet.connect(R.id.mildResultView , ConstraintSet.BOTTOM , R.id.mildResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()

                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    if (userPSSResult < 7)
                    {
                        var newPerc = ((12 * ((userPSSResult.toFloat() - 4) / 3) + 63) / 100)
                        lowResultPointer.setGuidelinePercent(newPerc)
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        mildResultView.alpha = 1f
                        lowResultView.alpha = 1f
                        severeResultView.alpha = 0f
                        mildResultView.invalidate()
                        lowResultView.invalidate()
                        severeResultView.invalidate()
                        newPainConstraintSet.connect(R.id.moderateResultView , ConstraintSet.BOTTOM , R.id.moderateResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()

                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)

                                scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleModerateAnimation.fillAfter = true
                                        scaleModerateAnimation.duration = 300
                                        moderateResultView.startAnimation(scaleModerateAnimation)                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }
                    if (userPSSResult == 7)
                    {
                        var newPerc = (75f / 100f)
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        mildResultView.alpha = 1f
                        lowResultView.alpha = 1f
                        severeResultView.alpha = 0f
                        mildResultView.invalidate()
                        lowResultView.invalidate()
                        severeResultView.invalidate()
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.connect(R.id.moderateResultView , ConstraintSet.BOTTOM , R.id.moderateResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()

                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)

                                scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleModerateAnimation.fillAfter = true
                                        scaleModerateAnimation.duration = 300
                                        moderateResultView.startAnimation(scaleModerateAnimation)                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                    if (userPSSResult < 10)
                    {
                        var newPerc = ((15 * (userPSSResult.toFloat() - 7) / 3) + 75) /100f
                        lowResultPointer.setGuidelinePercent(newPerc.toFloat())
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        mildResultView.alpha = 1f
                        moderateResultView.alpha = 1f
                        lowResultView.alpha = 1f
                        mildResultView.invalidate()
                        moderateResultView.invalidate()
                        lowResultView.invalidate()
                        newPainConstraintSet.connect(R.id.severeResultView , ConstraintSet.BOTTOM , R.id.severeResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()

                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)

                                scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleModerateAnimation.fillAfter = true
                                        scaleModerateAnimation.duration = 300
                                        moderateResultView.startAnimation(scaleModerateAnimation)
                                        scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                        {
                                            override fun onAnimationStart(p0: Animation?) {
                                            }

                                            override fun onAnimationEnd(p0: Animation?) {
                                                var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                                scaleSevereAnimation.fillAfter = true
                                                scaleSevereAnimation.duration = 300
                                                severeResultView.startAnimation(scaleSevereAnimation)                                             }

                                            override fun onAnimationRepeat(p0: Animation?) {
                                            }

                                        })
                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }
                    if (userPSSResult == 10)
                    {
                        var newPerc = (90f).toFloat()
                        lowResultPointer.setGuidelinePercent(newPerc)
                        newPainConstraintSet = ConstraintSet()
                        newPainConstraintSet.clone(mainActivity.applicationContext , R.layout.fragment_result_bpi_test)
                        mildResultView.alpha = 1f
                        moderateResultView.alpha = 1f
                        lowResultView.alpha = 1f
                        mildResultView.invalidate()
                        moderateResultView.invalidate()
                        lowResultView.invalidate()
                        newPainConstraintSet.connect(R.id.severeResultView , ConstraintSet.BOTTOM , R.id.severeResultPointerGL , ConstraintSet.TOP)
                        newPainConstraintSet.applyTo(formConLayout)
                        formConLayout.invalidate()
                        var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleLowAnimation.fillAfter = true
                        scaleLowAnimation.duration = 300
                        lowResultView.startAnimation(scaleLowAnimation)
                        scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleMildAnimation.fillAfter = true
                                scaleMildAnimation.duration = 300
                                mildResultView.startAnimation(scaleMildAnimation)

                                scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleModerateAnimation.fillAfter = true
                                        scaleModerateAnimation.duration = 300
                                        moderateResultView.startAnimation(scaleModerateAnimation)
                                        scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                        {
                                            override fun onAnimationStart(p0: Animation?) {
                                            }

                                            override fun onAnimationEnd(p0: Animation?) {
                                                var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                                scaleSevereAnimation.fillAfter = true
                                                scaleSevereAnimation.duration = 300
                                                severeResultView.startAnimation(scaleSevereAnimation)                                             }

                                            override fun onAnimationRepeat(p0: Animation?) {
                                            }

                                        })
                                    }

                                    override fun onAnimationRepeat(p0: Animation?) {
                                    }

                                })
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }
                }
            }
        }
    }

    //function to create Views above the bpi result bar
    suspend fun createBPIResultBarViews(runningResult : Int) {

        bpiresultBar.layoutParams.width = 0
        bpiresultBar.layoutParams.height = (RelativeLayout.LayoutParams.WRAP_CONTENT)
        val viewHeight = screenHeight / 20
        val ybpiresultBar = bpiresultBar.y
        val xbpiresultBar = bpiresultBar.x
        val userPSSResult = (arguments!!.getDouble(ARG_PARAM1) * 1).toInt()
        val userPISResult = (arguments!!.getDouble(ARG_PARAM2) * 1).toInt()
        var progressBarSubViews = ArrayList<customDerpesionProgressView>()
        val paint = Paint()


        when (runningResult)
        {
            0 ->
            {
                coroutineScope {
//                    var bpiPSSResBarView = customDerpesionProgressView(
//                        mainActivity.applicationContext,
//                        xbpiresultBar ,
//                        ybpiresultBar,
//                        xbpiresultBar + screenWidth,
//                        ybpiresultBar + (userPSSResult) * viewHeight.toFloat(),
//                        paint    )

//                    bpiPSSResBarView.layoutParams
//                    setBPIProgresViewColor(bpiPSSResBarView , userPSSResult)
                    //add the created view to the relativelayout
                    bpiresultBar.removeAllViews()
                    progressBarSubViews = createBPIBarView(userPSSResult , viewHeight.toFloat())
                    for (view in progressBarSubViews.iterator())
                    {
                        view.invalidate()
                        bpiresultBar.addView(
                            view,
                            ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        )
                    }
                    bpiresultBar.invalidate()
//                    bpiPSSResBarView.invalidate()
//                    bpiresultBar.addView(
//                        bpiPSSResBarView,
//                        ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT
//                        )
//                    )
//                    bpiresultBar.invalidate()

                    val scale = ScaleAnimation(1f , 1f , 0f , 1f)
                    scale.fillAfter = true
                    scale.duration = 1000
                    bpiresultBar.startAnimation(scale)
                    delay(3000)
                }
            }

            1 ->
            {
                coroutineScope {
//                    var bpiPISResBarView = customDerpesionProgressView(
//                        mainActivity.applicationContext,
//                        xbpiresultBar ,
//                        ybpiresultBar,
//                        xbpiresultBar + screenWidth,
//                        ybpiresultBar + (userPISResult) * viewHeight.toFloat(),
//                        paint    )
//
//                    bpiPISResBarView.layoutParams
//                    setBPIProgresViewColor(bpiPISResBarView , userPISResult)
                    //add the created view to the relativelayout
//                    bpiPISResBarView.invalidate()
//                    bpiresultBar.addView(
//                        bpiPISResBarView,
//                        ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT
//                        )
//                    )
//                    bpiresultBar.invalidate()
                    bpiresultBar.removeAllViews()
                    progressBarSubViews = createBPIBarView(userPISResult , viewHeight.toFloat())
                    for (view in progressBarSubViews)
                    {
                        view.invalidate()
                        bpiresultBar.addView(
                            view,
                            ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        )
                    }
                    bpiresultBar.invalidate()

                    val scale = ScaleAnimation(1f , 1f , 0f , 1f)
                    scale.fillAfter = true
                    scale.duration = 1000
                    bpiresultBar.startAnimation(scale)
                    delay(3000)
                }

            }
        }
    }

    private fun setProgresViewColor(view: View, index: Int) {
        if ((index >= 0) && (index < 20)) {
            view.background = resources.getDrawable(R.drawable.blue_progressbar_style)

        }
        if ((index >= 20) && (index <= 24)) {
            view.background = resources.getDrawable(R.drawable.green_progressbar_style)
        }
        if ((index >= 25) && (index <= 29)) {
            view.background = resources.getDrawable(R.drawable.orange_progressbar_style)
        }

        if (index >= 30) {
            view.background = resources.getDrawable(R.drawable.red_progressbar_style)
        }
    }

    private fun createBPIBarView(score : Int, viewHeight : Float) : ArrayList<customDerpesionProgressView>
    {
        var xResultBaseBar  = bpiresultBar.x
        var yResultBaseBar = bpiresultBar.y
        var progressBarSubViews = ArrayList<customDerpesionProgressView>()
        val paint = Paint()
        var barResultViewComplete = false
        var lowProgressBarView = View(mainActivity.applicationContext)
        var mildProgressBarView = View(mainActivity.applicationContext)
        var moderateProgressBarView = View(mainActivity.applicationContext)
        var severeProgressBarView = View(mainActivity.applicationContext)
        if (score < 1)
        {
//            var dummyCustomView = customDerpesionProgressView(mainActivity.applicationContext, 100f, 100f, 200f, 300f, paint )
//            dummyCustomView.setBackgroundColor(resources.getColor(R.color.blue))
//            progressBarSubViews.add(dummyCustomView)
            lowProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar , 0f , xResultBaseBar + screenWidth , ((score) * viewHeight) , paint)
            setBPIProgresViewColor(lowProgressBarView , score)
            progressBarSubViews.add(lowProgressBarView)
            barResultViewComplete = true
        }
        if (score >= 1)
        {
            lowProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar ,  0f   , xResultBaseBar + screenWidth ,((1) * viewHeight) , paint)
            setBPIProgresViewColor(lowProgressBarView, 1)
            progressBarSubViews.add(lowProgressBarView)
            if (score == 1)
                barResultViewComplete = true
        }
        if ((score < 4) && (!barResultViewComplete))
        {
            mildProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar ,  lowProgressBarView.height +  (1 * viewHeight) , xResultBaseBar + screenWidth , lowProgressBarView.height + ((score) * viewHeight), paint)
            setBPIProgresViewColor(mildProgressBarView , score)
            progressBarSubViews.add(mildProgressBarView)
            barResultViewComplete = true
        }
        if (score >= 4)
        {
            mildProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar ,lowProgressBarView.height +  (1 * viewHeight) , xResultBaseBar + screenWidth ,lowProgressBarView.height +  ((4) * viewHeight) , paint)
            setBPIProgresViewColor(mildProgressBarView , 4)

            progressBarSubViews.add(mildProgressBarView)
            if (score == 4)
                barResultViewComplete = true
        }
        if ((score < 7) && (!barResultViewComplete))
        {
            moderateProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar , lowProgressBarView.height + mildProgressBarView.height + (4 * viewHeight), xResultBaseBar + screenWidth , lowProgressBarView.height + mildProgressBarView.height  +  ((score) * viewHeight) , paint)
            setBPIProgresViewColor(moderateProgressBarView , score)
            progressBarSubViews.add(moderateProgressBarView)
            barResultViewComplete = true
        }
        if (score >= 7)
        {
            moderateProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar ,lowProgressBarView.height + mildProgressBarView.height +  (4 * viewHeight) , xResultBaseBar + screenWidth , lowProgressBarView.height + mildProgressBarView.height + ((7) * viewHeight) , paint)
            setBPIProgresViewColor(moderateProgressBarView , 7)
            progressBarSubViews.add(moderateProgressBarView)
            if (score == 7)
                barResultViewComplete = true
        }
        if ((score < 10) && (!barResultViewComplete))
        {
            severeProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar , lowProgressBarView.height + mildProgressBarView.height + moderateProgressBarView.height +(7 * viewHeight) , xResultBaseBar + screenWidth , lowProgressBarView.height + mildProgressBarView.height + moderateProgressBarView.height + ((score) * viewHeight) , paint)
            setBPIProgresViewColor(severeProgressBarView , score)
            progressBarSubViews.add(severeProgressBarView)
            barResultViewComplete = true
        }
        if (score == 10)
        {
            severeProgressBarView = customDerpesionProgressView(mainActivity.applicationContext , xResultBaseBar , lowProgressBarView.height + mildProgressBarView.height + moderateProgressBarView.height + (7 * viewHeight), xResultBaseBar + screenWidth , lowProgressBarView.height + mildProgressBarView.height + moderateProgressBarView.height + ((10) * viewHeight)  , paint)
            setBPIProgresViewColor(severeProgressBarView , 10)
            progressBarSubViews.add(severeProgressBarView)
            if (score == 10)
                barResultViewComplete = true
        }

        return progressBarSubViews
    }

    private fun setBPIProgresViewColor(view: View, index: Int) {
        if ((index >= 0) && (index <= 1)) {
            view.background = resources.getDrawable(R.drawable.blue_progressbar_style)

        }
        if ((index > 1) && (index <= 4)) {
            view.background = resources.getDrawable(R.drawable.green_progressbar_style)

        }
        if ((index > 4) && (index <= 7)) {
            view.background = resources.getDrawable(R.drawable.orange_progressbar_style)
        }
        if ((index > 7) && (index <= 10)) {
            view.background = resources.getDrawable(R.drawable.red_progressbar_style)
        }
    }

    fun setBaiTestSummary(testSum : Int)
    {
        if ((testSum >=0) && (testSum <= 21))
        {
            baiTestSummary.text = mainActivity.resources.getString(R.string.BAIRESULT1)
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
        }
        if ((testSum >=22) && (testSum < 35))
        {
            baiTestSummary.text = mainActivity.resources.getString(R.string.BAIRESULT2)
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
        }
        if (testSum >=36)
        {
            baiTestSummary.text = mainActivity.resources.getString(R.string.BAIRESULT3)
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
        }
    }


    fun animateDepressionProgressBar(depressionResult : Int) {
        //get programmatically the children of the views
        for (i in 0 until depressionResult) {
            if ((i >= 0) && (i < 36)) {
                depressionProgressBar.getChildAt(i).animate().alpha(0.5F).setDuration(100).start()
                depressionProgressBar.getChildAt(i).animate().alpha(1F).setDuration(100).withEndAction {
                    depressionProgressBar.getChildAt(i)
                        .background = resources.getDrawable(R.drawable.blue_progressbar_style)
                }

            }
            if ((i >= 36) && (i <= 44)) {
                depressionProgressBar.getChildAt(i).animate().alpha(0.5F).setDuration(100).start()
                depressionProgressBar.getChildAt(i).animate().alpha(1F).setDuration(100).withEndAction {
                    depressionProgressBar.getChildAt(i)
                        .background = resources.getDrawable(R.drawable.green_progressbar_style)
                }

            }
            if ((i >= 45) && (i <= 54)) {
                depressionProgressBar.getChildAt(i).animate().alpha(0.5F).setDuration(100).start()
                depressionProgressBar.getChildAt(i).animate().alpha(1F).setDuration(100).withEndAction {
                    depressionProgressBar.getChildAt(i)
                        .background = resources.getDrawable(R.drawable.orange_progressbar_style)
                }

            }
            if (i > 54) {
                depressionProgressBar.getChildAt(i).animate().alpha(0.5F).setDuration(100).start()
                depressionProgressBar.getChildAt(i).animate().alpha(1F).setDuration(100).withEndAction {
                    depressionProgressBar.getChildAt(i)
                        .background = resources.getDrawable(R.drawable.red_progressbar_style)
                }

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun getScreenDimens()
    {
        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight= displayMetrics.heightPixels
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
        fun newInstance(param1: Double, param2: Double , param3: Int) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, param1)
                    putDouble(ARG_PARAM2 , param2)
                    putInt(ARG_PARAM3 , param3)
                }
            }
    }
}
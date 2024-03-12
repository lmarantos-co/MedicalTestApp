package com.example.cvdriskestimator.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.customClasses.customDerpesionProgressView
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.customClasses.BarChartView
import com.example.cvdriskestimator.customClasses.CustomRelativeLayout
import com.example.cvdriskestimator.customClasses.PieChartView
import kotlinx.coroutines.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "risk_result1"
private const val ARG_PARAM2 = "risk_result2"
private const val ARG_PARAM4 = "risk_result4"
private const val ARG_PARAM3 = "test_type"
private const val ARG_PARAM5 = "oqpol_selections"


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

    private var registerFragment: RegisterFragment = RegisterFragment.newInstance()
    private var leaderBoardFragment = LeaderBoardFragment.newInstance()
    private lateinit var popupMenuComp: PopUpMenu
    private lateinit var closeBtn: ImageView
    private lateinit var formConLayout: ConstraintLayout
    private lateinit var termsRelLayout: RelativeLayout

    private lateinit var noDepressionLayout: RelativeLayout
    private lateinit var mildDepressionLayout: RelativeLayout
    private lateinit var moderateDepressionLayout: RelativeLayout
    private lateinit var servereDepressionLayout: RelativeLayout
    private lateinit var mostSevereDepressionLayout : RelativeLayout
    private lateinit var noDepressionResultView : View
    private lateinit var mildDepressionResultView : View
    private lateinit var moderateDepressionView : View
    private lateinit var severeDepressionView : View
    private lateinit var mostSevereDepressionView : View
    private var noDepViewHeight : Int = 0
    private var mildDepViewHeight : Int = 0
    private var modDepViewHeight : Int = 0
    private var sevDepViewHeight : Int = 0
    private var moreSevDepViewHeight : Int = 0
    private lateinit var totalScore: TextView
    private lateinit var depressStatus: TextView
    private var noDepressionFormWidth: Int = 0
    private var mildDepressionFormWidth: Int = 0
    private var moderateDepressionFormWidth: Int = 0
    private var severeDepressionFormWidth: Int = 0
    private var mostSevereDepressionFormWidth : Int = 0
    private var depressionResultBarHeight: Int = 0
    private lateinit var depressionProgressBar: RelativeLayout

    private lateinit var baiTestSummary: TextView
    private lateinit var baiTestSummaryB: TextView
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

    //GDS test score variables
    private lateinit var gdsTestScore : TextView
    private lateinit var gdsTestResult : TextView
    private lateinit var gdsView1 : View
    private lateinit var gdsView2 : View
    private lateinit var gdsView3 : View
    private lateinit var gdsView4 : View
    private lateinit var gdsView5 : View
    private lateinit var gdsView6 : View
    private lateinit var gdsView7 : View
    private lateinit var gdsView8 : View
    private lateinit var gdsView9 : View
    private lateinit var gdsView10 : View
    private lateinit var gdsView11 : View
    private lateinit var gdsView12 : View
    private lateinit var gdsView13 : View
    private lateinit var gdsView14 : View
    private lateinit var gdsView15 :  View

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

    private lateinit var bpiTestResultTxtV  : TextView
    private lateinit var totalPSSScore : TextView
    private lateinit var totalPISScore : TextView

    private lateinit var staiTestResultTxtV  : TextView
    private lateinit var staiStateAScore : TextView
    private lateinit var staiTraitAScore : TextView
    private lateinit var staiResultBar : RelativeLayout

    //DASS Test

    private lateinit var dassNormalDepression : TextView
    private lateinit var dassNormalAnxiety : TextView
    private lateinit var dassNormalStress : TextView

    private lateinit var dassMildDepression : TextView
    private lateinit var dassMildAnxiety : TextView
    private lateinit var dassMildStress : TextView

    private lateinit var dassModerateDepression : TextView
    private lateinit var dassModerateAnxiety : TextView
    private lateinit var dassModerateStress : TextView

    private lateinit var dassSevereDepression : TextView
    private lateinit var dassSevereAnxiety : TextView
    private lateinit var dassSevereStress : TextView

    private lateinit var dassExtSevereDepression : TextView
    private lateinit var dassExtSevereAnxiety : TextView
    private lateinit var dassExtSevereStress : TextView

    //OPQOL Test
    private lateinit var opqolTestResult : TextView
    private lateinit var pieChartView : RelativeLayout

    //GAS Test
    private lateinit var lowAnxietyResultView : RelativeLayout
    private lateinit var mildAnxietyResultView : RelativeLayout
    private lateinit var moderateAnxietyResultView : RelativeLayout
    private lateinit var severeAnxietyResultView : RelativeLayout
    private lateinit var totalGASScore : TextView
    private lateinit var gasTestTextResult : TextView
    private lateinit var GAStestSummary : TextView
    private lateinit var GAStestSummaryB : TextView

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
            view = inflater.inflate(R.layout.fragment_result_mdi_test, container, false) as View
            formConLayout = view.findViewById(R.id.results_con_layout_opqol_test)
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
            noDepressionResultView = view.findViewById(R.id.noDepResultView)
            mildDepressionResultView = view.findViewById(R.id.mildDepResultView)
            moderateDepressionView = view.findViewById(R.id.modDepResultView)
            severeDepressionView = view.findViewById(R.id.severeDepResultView)
            totalScore = view.findViewById(R.id.totalScore)
            depressStatus = view.findViewById(R.id.QOLStatus)
            setMDIResultsOnForm(
                arguments!!.getDouble(ARG_PARAM1).toInt(),
                getMDIResult(arguments!!.getDouble(ARG_PARAM1).toInt())
            )

            noDepressionLayout.post {
                noDepressionFormWidth = noDepressionLayout.width
                depressionResultBarHeight = noDepressionLayout.height
                noDepViewHeight = noDepressionLayout.height
            }


            mildDepressionLayout.post {
                mildDepressionFormWidth = mildDepressionLayout.width
                mildDepViewHeight = mildDepressionLayout.height

            }


            moderateDepressionLayout.post {
                moderateDepressionFormWidth = moderateDepressionLayout.width
                modDepViewHeight = moderateDepressionLayout.height
            }


            servereDepressionLayout.post {
                severeDepressionFormWidth = servereDepressionLayout.width
                sevDepViewHeight = servereDepressionLayout.height
                GlobalScope.launch(Dispatchers.Main) {
//                    setDimensionsForDepressionViews()
                    showMDIResultBarViews()
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
            baiTestSummaryB = view.findViewById(R.id.BAItestSummaryB)
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
            mdsTestResult = view.findViewById(R.id.totalScoreDescOPQOLTxtV)
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

            lowResultView = view.findViewById(R.id.lowPainRelLayout)
            mildResultView = view.findViewById(R.id.mildPainRelLayout)
            moderateResultView = view.findViewById(R.id.moderatePainRelLayout)
            severeResultView = view.findViewById(R.id.severePainRelLayout)
            lowResultPointer = view.findViewById(R.id.lowResultPointerGL)
            mildResultPointer = view.findViewById(R.id.mildResultPointerGL)
            moderateResultPointer = view.findViewById(R.id.moderateResultPointerGL)
            severeResultPointer = view.findViewById(R.id.severeResultPointerGL)

            lowProgressBar = view.findViewById(R.id.lowResultProgressBar)
            mildProgressBar = view.findViewById(R.id.mildResultProgressBar)
            moderateProgressBar = view.findViewById(R.id.moderateResultProgressBar)
            severeProgressBar = view.findViewById(R.id.severeResultProgressBar)

            bpiTestResultTxtV = view.findViewById(R.id.testResultTxtV)

            totalPSSScore = view.findViewById(R.id.totalPSSScoreDescTxtV)
            totalPISScore = view.findViewById(R.id.totalPISScoreDescTxtV)

            formConLayout.post {
                lowProgressViewHeight = lowResultView.height
                mildProgressViewHeight = mildResultView.height
                moderateProgressViewHeight = moderateResultView.height
                severeProgreessViewHeight = severeResultView.height

                //coroutine implementation of BPI Bar view
                var runningTest: Int = 0
                var counter : Int = 0
                GlobalScope.launch(Dispatchers.Main) {
                    // Do something here on the main thread
                    while (counter < 1000000) {
//                    createBPIResultBarViews(runningTest)
//                    showBPIProgressBar(runningTest)
//                    highlightPainViews(runningTest)
                        ResultBPIBArViews(runningTest)
                        counter++
                        runningTest = Math.floorMod(counter, 2)
                    }
                }
            }
        }
        if (test_type == 7) {
            if (test_type == 3) {
                view = inflater.inflate(R.layout.fragment_result_mdi_test, container, false) as View
                formConLayout = view.findViewById(R.id.results_con_layout_opqol_test)
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
                mostSevereDepressionLayout = view.findViewById(R.id.mostSevereDepView)
                noDepressionResultView = view.findViewById(R.id.noDepResultView)
                mildDepressionResultView = view.findViewById(R.id.mildDepResultView)
                moderateDepressionView = view.findViewById(R.id.modDepResultView)
                severeDepressionView = view.findViewById(R.id.severeDepResultView)
                totalScore = view.findViewById(R.id.totalScore)
                depressStatus = view.findViewById(R.id.QOLStatus)
                setMDIResultsOnForm(
                    requireArguments().getDouble(ARG_PARAM1).toInt(),
                    getMDIResult(requireArguments().getDouble(ARG_PARAM1).toInt())
                )

                noDepressionLayout.post {
                    noDepressionFormWidth = noDepressionLayout.width
                    depressionResultBarHeight = noDepressionLayout.height
                    noDepViewHeight = noDepressionLayout.height
                }


                mildDepressionLayout.post {
                    mildDepressionFormWidth = mildDepressionLayout.width
                    mildDepViewHeight = mildDepressionLayout.height

                }


                moderateDepressionLayout.post {
                    moderateDepressionFormWidth = moderateDepressionLayout.width
                    modDepViewHeight = moderateDepressionLayout.height
                }


                servereDepressionLayout.post {
                    severeDepressionFormWidth = servereDepressionLayout.width
                    sevDepViewHeight = servereDepressionLayout.height
                    GlobalScope.launch(Dispatchers.Main) {
//                    setDimensionsForDepressionViews()
                        showMDIResultBarViews()
                    }
                }

            }
            view = inflater.inflate(R.layout.fragment_result_gds_test, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_gds_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.gdsTestTitleTxtV)
            gdsTestScore = view.findViewById(R.id.totalPSScore)
            gdsTestResult = view.findViewById(R.id.totalScoreDescOPQOLTxtV)
            val score = arguments!!.getDouble(ARG_PARAM1)
             gdsTestResult.text = (String.format(
                resources.getString(R.string.mds_test_score),
                score.toFloat())
            )
//            gdsTestResult.text = geGDSResult(score.toInt())
            gdsView1 = view.findViewById(R.id.view1)
            gdsView2 = view.findViewById(R.id.view2)
            gdsView3 = view.findViewById(R.id.view3)
            gdsView4 = view.findViewById(R.id.view4)
            gdsView5 = view.findViewById(R.id.view5)
            gdsView6 = view.findViewById(R.id.view6)
            gdsView7 = view.findViewById(R.id.view7)
            gdsView8 = view.findViewById(R.id.view8)
            gdsView9 = view.findViewById(R.id.view9)
            gdsView10 = view.findViewById(R.id.view10)
            gdsView11 = view.findViewById(R.id.view11)
            gdsView12 = view.findViewById(R.id.view12)
            gdsView13 = view.findViewById(R.id.view13)
            gdsView14 = view.findViewById(R.id.view14)
            gdsView15 = view.findViewById(R.id.view15)


        }

        if (test_type == 8) {
            view = inflater.inflate(R.layout.fragment_result_bdi_test, container, false) as View
            formConLayout = view.findViewById(R.id.results_con_layout_opqol_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.opqolTestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.testResultTxtV)
            depressionProgressBar = RelativeLayout(mainActivity.applicationContext)
            depressionProgressBar = view.findViewById(R.id.resulVerticalProgressBar)
            noDepressionLayout = view.findViewById(R.id.noDepView)
            mildDepressionLayout = view.findViewById(R.id.mildDepView)
            moderateDepressionLayout = view.findViewById(R.id.moderateDepView)
            servereDepressionLayout = view.findViewById(R.id.severeDepView)
            mostSevereDepressionLayout = view.findViewById(R.id.mostSevereDepView)
            noDepressionResultView = view.findViewById(R.id.noDepResultView)
            mildDepressionResultView = view.findViewById(R.id.mildDepResultView)
            moderateDepressionView = view.findViewById(R.id.modDepResultView)
            severeDepressionView = view.findViewById(R.id.severeDepResultView)
            mostSevereDepressionView = view.findViewById(R.id.mostSevereDepResultView)
            totalScore = view.findViewById(R.id.totalScore)
            depressStatus = view.findViewById(R.id.QOLStatus)
            setBDIResultsOnForm(
                requireArguments().getDouble(ARG_PARAM1).toInt(),
                getBDIResult(requireArguments().getDouble(ARG_PARAM1).toInt())
            )

            noDepressionLayout.post {
                noDepressionFormWidth = noDepressionLayout.width
                depressionResultBarHeight = noDepressionLayout.height
                noDepViewHeight = noDepressionLayout.height
            }


            mildDepressionLayout.post {
                mildDepressionFormWidth = mildDepressionLayout.width
                mildDepViewHeight = mildDepressionLayout.height

            }


            moderateDepressionLayout.post {
                moderateDepressionFormWidth = moderateDepressionLayout.width
                modDepViewHeight = moderateDepressionLayout.height
            }


            servereDepressionLayout.post {
                severeDepressionFormWidth = servereDepressionLayout.width
                sevDepViewHeight = servereDepressionLayout.height
            }

            mostSevereDepressionLayout.post {
                mostSevereDepressionFormWidth = mostSevereDepressionLayout.width
                moreSevDepViewHeight = mostSevereDepressionLayout.height
                GlobalScope.launch(Dispatchers.Main) {
//                    setDimensionsForDepressionViews()
                    showBDIResultBarViews()
                }
            }

        }

        if (test_type == 9) {
            view = inflater.inflate(R.layout.fragment_hammilton_result_fragment, container, false) as View
            formConLayout = view.findViewById(R.id.results_con_layout_opqol_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.hammTestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.testResultTxtV)
            depressionProgressBar = RelativeLayout(mainActivity.applicationContext)
            depressionProgressBar = view.findViewById(R.id.resulVerticalProgressBar)
            noDepressionLayout = view.findViewById(R.id.noDepView)
            mildDepressionLayout = view.findViewById(R.id.mildDepView)
            moderateDepressionLayout = view.findViewById(R.id.moderateDepView)
            servereDepressionLayout = view.findViewById(R.id.severeDepView)
            noDepressionResultView = view.findViewById(R.id.noDepResultView)
            mildDepressionResultView = view.findViewById(R.id.mildDepResultView)
            moderateDepressionView = view.findViewById(R.id.modDepResultView)
            severeDepressionView = view.findViewById(R.id.severeDepResultView)
            totalScore = view.findViewById(R.id.totalScore)
            depressStatus = view.findViewById(R.id.QOLStatus)
            setHAMResultsOnForm(
                requireArguments().getDouble(ARG_PARAM1).toInt(),
                getHAMMResult(requireArguments().getDouble(ARG_PARAM1).toInt())
            )

            noDepressionLayout.post {
                noDepressionFormWidth = noDepressionLayout.width
                depressionResultBarHeight = noDepressionLayout.height
                noDepViewHeight = noDepressionLayout.height
            }


            mildDepressionLayout.post {
                mildDepressionFormWidth = mildDepressionLayout.width
                mildDepViewHeight = mildDepressionLayout.height

            }


            moderateDepressionLayout.post {
                moderateDepressionFormWidth = moderateDepressionLayout.width
                modDepViewHeight = moderateDepressionLayout.height
            }


            servereDepressionLayout.post {
                severeDepressionFormWidth = servereDepressionLayout.width
                sevDepViewHeight = servereDepressionLayout.height

                GlobalScope.launch(Dispatchers.Main) {
//                    setDimensionsForDepressionViews()
                    showHAMMResultBarViews()
                }
            }
        }
        if (test_type == 10) {
            view = inflater.inflate(R.layout.fragment_stai_result, container, false)
            formConLayout = view.findViewById(R.id.results_con_layout_stai_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.staiTestTitleTxtV)
            pssTestScore = view.findViewById(R.id.totalSTAIStatescore)
            pssTestResult = view.findViewById(R.id.totalStateSTAIcoreDescTxtV)
            pisTestScore = view.findViewById(R.id.totalSTAITraitScore)
            pisTestResult = view.findViewById(R.id.totalTraitSTAIscoreDescTxtV)
            staiResultBar = view.findViewById(R.id.resultBarView)
            moderatePainBar = view.findViewById(R.id.moderatePainRelLayout)

            lowResultView = view.findViewById(R.id.lowResultView)
            mildResultView = view.findViewById(R.id.mildResultView)
            moderateResultView = view.findViewById(R.id.moderateResultView)
            lowResultPointer = view.findViewById(R.id.lowResultPointerGL)
            mildResultPointer = view.findViewById(R.id.mildResultPointerGL)
            moderateResultPointer = view.findViewById(R.id.moderateResultPointerGL)

            lowProgressBar = view.findViewById(R.id.lowResultProgressBar)
            mildProgressBar = view.findViewById(R.id.mildResultProgressBar)
            moderateProgressBar = view.findViewById(R.id.moderateResultProgressBar)

            bpiTestResultTxtV = view.findViewById(R.id.testResultTxtV)

            totalPSSScore = view.findViewById(R.id.totalStateSTAIcoreDescTxtV)
            totalPISScore = view.findViewById(R.id.totalTraitSTAIscoreDescTxtV)

            formConLayout.post {
                lowProgressViewHeight = lowResultView.height
                mildProgressViewHeight = mildResultView.height
                moderateProgressViewHeight = moderateResultView.height

                //coroutine implementation of BPI Bar view
                var runningTest: Int = 0
                var counter : Int = 0
                GlobalScope.launch(Dispatchers.Main) {
                    // Do something here on the main thread
                    while (counter < 1000000) {
//                    createBPIResultBarViews(runningTest)
//                    showBPIProgressBar(runningTest)
//                    highlightPainViews(runningTest)
                        ResultSTAIBArViews(runningTest)
                        counter++
                        runningTest = Math.floorMod(counter, 2)
                    }
                }
            }
        }

        if (test_type == 11) {

            view = inflater.inflate(R.layout.fragment_dass_result, container, false)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            dassNormalAnxiety = view.findViewById(R.id.anxietyNormalTxtV)
            dassNormalDepression = view.findViewById(R.id.depressionNormalTxtV)
            dassNormalStress = view.findViewById(R.id.stressNormaTxtV)
            dassMildAnxiety = view.findViewById(R.id.anxietyMildTxtV)
            dassMildDepression = view.findViewById(R.id.depressionMildTxtV)
            dassMildStress = view.findViewById(R.id.stressMildTxtV)
            dassModerateAnxiety = view.findViewById(R.id.anxietyModerateTxtV)
            dassModerateDepression = view.findViewById(R.id.depressionModerateTxtV)
            dassModerateStress = view.findViewById(R.id.stressModerateTxtV)
            dassSevereAnxiety = view.findViewById(R.id.anxietySevereTxtV)
            dassSevereDepression = view.findViewById(R.id.depressionSevereTxtV)
            dassSevereStress = view.findViewById(R.id.stressSevereTxtV)
            dassExtSevereAnxiety = view.findViewById(R.id.anxietyExtSevereTxtV)
            dassExtSevereDepression = view.findViewById(R.id.depressionExtSevereTxtV)
            dassExtSevereStress = view.findViewById(R.id.stressExtSevereTxtV)
            higlightDASSTxtViewsWithScore()
        }

        if (test_type == 12) {
            view = inflater.inflate(R.layout.fragment_result_zung_test, container, false) as View
            formConLayout = view.findViewById(R.id.results_con_layout_opqol_test)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            testHeadling = view.findViewById(R.id.opqolTestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.testResultTxtV)
            depressionProgressBar = RelativeLayout(mainActivity.applicationContext)
            depressionProgressBar = view.findViewById(R.id.resulVerticalProgressBar)
            noDepressionLayout = view.findViewById(R.id.noDepView)
            mildDepressionLayout = view.findViewById(R.id.mildDepView)
            moderateDepressionLayout = view.findViewById(R.id.moderateDepView)
            servereDepressionLayout = view.findViewById(R.id.severeDepView)
            mostSevereDepressionLayout = view.findViewById(R.id.mostSevereDepView)
            noDepressionResultView = view.findViewById(R.id.noDepResultView)
            mildDepressionResultView = view.findViewById(R.id.mildDepResultView)
            moderateDepressionView = view.findViewById(R.id.modDepResultView)
            severeDepressionView = view.findViewById(R.id.severeDepResultView)
            mostSevereDepressionView = view.findViewById(R.id.mostSevereDepResultView)
            totalScore = view.findViewById(R.id.totalScore)
            depressStatus = view.findViewById(R.id.QOLStatus)
            setBDIResultsOnForm(
                requireArguments().getDouble(ARG_PARAM1).toInt(),
                getZUNGResult(requireArguments().getDouble(ARG_PARAM1).toInt())
            )

            noDepressionLayout.post {
                noDepressionFormWidth = noDepressionLayout.width
                depressionResultBarHeight = noDepressionLayout.height
                noDepViewHeight = noDepressionLayout.height
            }


            mildDepressionLayout.post {
                mildDepressionFormWidth = mildDepressionLayout.width
                mildDepViewHeight = mildDepressionLayout.height

            }


            moderateDepressionLayout.post {
                moderateDepressionFormWidth = moderateDepressionLayout.width
                modDepViewHeight = moderateDepressionLayout.height
            }


            servereDepressionLayout.post {
                severeDepressionFormWidth = servereDepressionLayout.width
                sevDepViewHeight = servereDepressionLayout.height
            }

            mostSevereDepressionLayout.post {
                mostSevereDepressionFormWidth = mostSevereDepressionLayout.width
                moreSevDepViewHeight = mostSevereDepressionLayout.height
                GlobalScope.launch(Dispatchers.Main) {
//                    setDimensionsForDepressionViews()
                    showZUNGResultBarViews()
                }
            }

        }

        if (test_type == 13)
        {
            var score = arguments!!.getDouble(ARG_PARAM1)
            view = inflater.inflate(R.layout.fragment_result_opqol_test, container, false) as View
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            opqolTestResult = view.findViewById(R.id.totalScoreDescOPQOLTxtV)
            pieChartView = view.findViewById(R.id.pieChartView)
            opqolTestResult.setText((String.format(
                resources.getString(R.string.mds_test_score),
                String.format("%.2f", score.toFloat())
            )))

            // Inside your activity or fragment
            var angle = (score  / 140) * 360
            val circleView = PieChartView(context!!, angle.toFloat())
            val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            val selections = arguments!!.getSerializable(ARG_PARAM5) as ArrayList<Int>
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            circleView.layoutParams = layoutParams
            val barChartView = BarChartView(context!! , selections)
            pieChartView.addView(barChartView)
        }

        if (test_type == 14) {
            view = inflater.inflate(R.layout.fragment_result_gas_test, container, false)
//            formConLayout = view.findViewById(R.id.results_constraint_layout)
            testHeadling = view.findViewById(R.id.gasTestTitleTxtV)
            riskResultTxtV = view.findViewById(R.id.gasTestResultTxtV)
//            riskResultTxtVSum = view.findViewById(R.id.txtViewTestResultSummary)
//            cvdTestResults = view.findViewById(R.id.txtVcvdTestDetails)
            MTEtitle = view.findViewById(R.id.include_cvd_title_form)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            userIcon.alpha = 1f
            menuConLayout = view.findViewById(R.id.include_pop_up_menu)
            termsRelLayout = menuConLayout.findViewById(R.id.termsRelLayout)
            termsRelLayout.visibility = View.INVISIBLE
            closeBtn = menuConLayout.findViewById(R.id.closeBtn)
            companyLogo = MTEtitle.findViewById(R.id.covariance_logo)
            userIcon = MTEtitle.findViewById(R.id.userIcon)
            lowAnxietyResultView = view.findViewById(R.id.lowAnxietyResultView)
            mildAnxietyResultView = view.findViewById(R.id.mildAnxietyResultView)
            moderateAnxietyResultView = view.findViewById(R.id.moderateAnxietyResultView)
            severeAnxietyResultView = view.findViewById(R.id.severeAnxietyResultView)
            totalGASScore = view.findViewById(R.id.totalGASScore)
            val score = arguments!!.getDouble(ARG_PARAM1).toInt()
            totalGASScore.setText(score.toString())
            gasTestTextResult = view.findViewById(R.id.gasTestResultTxtV)
            GAStestSummary = view.findViewById(R.id.GAStestSummary)
            GAStestSummaryB = view.findViewById(R.id.GAStestSummaryB)
            setGasTestSummary(score)
        }
            return view
    }


    private fun higlightDASSTxtViewsWithScore() {

        //anxiety score
        var anxietyScore = arguments!!.getDouble(ARG_PARAM1)
        var scressScore = arguments!!.getDouble(ARG_PARAM2)
        var depressionScore = arguments!!.getDouble(ARG_PARAM4)
        if ((anxietyScore > 0) && (anxietyScore <= 7))
        {
            dassNormalAnxiety.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((anxietyScore >= 8) && (anxietyScore <= 9))
        {
            dassMildAnxiety.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((anxietyScore >= 10) && (anxietyScore <= 14))
        {
            dassModerateAnxiety.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((anxietyScore >= 15) && (anxietyScore <= 19))
        {
            dassSevereAnxiety.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if (anxietyScore >= 20)
        {
            dassExtSevereAnxiety.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((depressionScore > 0) && (depressionScore <= 9))
        {
            dassNormalDepression.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((depressionScore >= 10) && (depressionScore <= 13))
        {
            dassMildDepression.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((depressionScore >= 14) && (depressionScore <= 20))
        {
            dassModerateDepression.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((depressionScore >= 21) && (depressionScore <= 27))
        {
            dassSevereDepression.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if (depressionScore >= 28)
        {
            dassExtSevereDepression.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((scressScore > 0) && (scressScore <= 14))
        {
            dassNormalStress.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((scressScore >= 15) && (scressScore <= 18))
        {
            dassMildStress.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((scressScore >= 19) && (scressScore <= 25))
        {
            dassModerateStress.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if ((scressScore >= 26) && (scressScore <= 33))
        {
            dassSevereStress.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
        if (scressScore >= 34)
        {
            dassExtSevereStress.setBackgroundColor(resources.getColor(R.color.green_dass))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvdTitleForm = MTEtitle.findViewById(R.id.cvdTitleForm)

        cvdTitleForm.post {
            mteTitleHeight = cvdTitleForm.layoutParams.height
        }

        MTEtitle.setOnClickListener {
            mainActivity.hideResultFragment(this@ResultFragment)
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
            PopUpMenu(termsRelLayout, mainActivity, this,registerFragment , null ,leaderBoardFragment)

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
        if (test_type == 7)
        {
            setFormColors(testType = 7)
            GlobalScope.launch(Dispatchers.Main)
            {
                setGDSTestBarColors(arguments!!.getDouble(ARG_PARAM1).toInt())
            }
        }
        if (test_type == 10)
        {
            setFormColors(testType = 10)
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
                mdsTestScore.setBackgroundResource(R.drawable.mds_relative_layout)
            }
            6 ->
            {
                testHeadling.setBackgroundResource(R.drawable.red_bpi_form_style)
                bpiTestResultTxtV.setBackgroundResource(R.drawable.bpi_result_rel_layout)
            }
            7 -> {
                testHeadling.setBackgroundResource(R.drawable.purple_mditest_form_title_style)
                gdsTestScore.setBackgroundResource(R.drawable.mdi_test_relative_layout)
            }
            3 -> {
                testHeadling.setBackgroundResource(R.drawable.purple_mditest_form_title_style)
                riskResultTxtV.setBackgroundResource(R.drawable.mdi_test_relative_layout)
            }
            10 ->
            {
                testHeadling.setBackgroundResource(R.drawable.blue_baitest_form_title_style)
                bpiTestResultTxtV.setBackgroundResource(R.drawable.stai_results_rel_layout)
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

    private fun getBDIResult(mdiResult: Int): String {
        if ((mdiResult >= 0) && (mdiResult < 9)) {
            return "ΕΛΑΧΙΣΤΗ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((mdiResult >= 10) && (mdiResult <= 18)) {
            return "ΗΠΙΑ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((mdiResult >= 19) && (mdiResult <= 29)) {
            return "ΜΕΤΡΙΑ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((mdiResult >= 30) && (mdiResult <= 63)) {
            return "ΣΟΒΑΡΗ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((mdiResult >= 64) && (mdiResult <= 75)) {
            return "ΠΙΟ ΣΟΒΑΡΗ ΚΑΤΑΘΛΙΨΗ"
        }
        return ""
    }

    private fun getZUNGResult(zungResult: Int): String {
        if ((zungResult >= 0) && (zungResult < 25)) {
            return "ΕΛΑΧΙΣΤΗ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((zungResult >= 25) && (zungResult <= 49)) {
            return "ΦΥΣΙΟΛΟΓΙΚΗ ΚΑΤΑΣΤΑΣΗ"
        }
        if ((zungResult >= 50) && (zungResult <= 59)) {
            return "ΜΕΤΡΙΑ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((zungResult >= 60) && (zungResult <= 69)) {
            return "ΣΟΒΑΡΗ ΚΑΤΑΘΛΙΨΗ"
        }
        if (zungResult >= 70) {
            return "ΠΙΟ ΣΟΒΑΡΗ ΚΑΤΑΘΛΙΨΗ"
        }
        return ""
    }

    private fun getHAMMResult(hammResult: Int): String {
        if ((hammResult >= 0) && (hammResult < 7)) {
            return "ΕΛΑΧΙΣΤΗ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((hammResult >= 7) && (hammResult <= 17)) {
            return "ΗΠΙΑ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((hammResult >= 18) && (hammResult <= 24)) {
            return "ΜΕΤΡΙΑ ΚΑΤΑΘΛΙΨΗ"
        }
        if ((hammResult >= 25) && (hammResult <= 54)) {
            return "ΣΟΒΑΡΗ ΚΑΤΑΘΛΙΨΗ"
        }
        return ""
    }


    private fun setMDIResultsOnForm(mdiDepressionInt: Int, mdiDepressionType: String) {
        totalScore.text = Html.fromHtml("<b>" + mdiDepressionInt.toString() + "</b>")
        depressStatus.text = Html.fromHtml("<b>" + mdiDepressionType+ "</b>")
//        setMDIResultTextViewColors(mdiDepressionInt)
    }

    private fun setBDIResultsOnForm(bdiDepressionInt: Int, bdiDepressionType: String) {
        totalScore.text = Html.fromHtml("<b>" + bdiDepressionInt.toString() + "</b>")
        depressStatus.text = Html.fromHtml("<b>" + bdiDepressionType+ "</b>")
//        setMDIResultTextViewColors(mdiDepressionInt)
    }

    private fun setHAMResultsOnForm(hammDepressionInt: Int, hammDepressionType: String) {
        totalScore.text = Html.fromHtml("<b>" + hammDepressionInt.toString() + "</b>")
        depressStatus.text = Html.fromHtml("<b>" + hammDepressionType+ "</b>")
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

    private fun geGDSResult(score : Int) : String
    {
        if ((0 >= score) && (score <= 5))
        {
            return "Απουσία Κατάθληψης"
        }
        if ((6 <= score) && (score <= 10))
        {
            return "Παρουσία ήπιας κατάθληψης. Απαιτείται ιατρική/ψυχιατρική αξιολόγηση"
        }
        if (score >= 11)
        {
            return "Παρουσία σοβαρής κατάθληψης"
        }
        return ""
    }

    private suspend fun setGDSTestBarColors(score : Int)
    {
        var barsRemaining : Int = 0
        if (score > 0)
        {
            gdsView1.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 1)
        {
            gdsView2.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 2)
        {
            gdsView3.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 3)
        {
            gdsView4.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 4)
        {
            gdsView5.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 5)
        {
            gdsView6.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 6)
        {
            gdsView7.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 7)
        {
            gdsView8.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 8)
        {
            gdsView9.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 9)
        {
            gdsView10.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 10)
        {
            gdsView11.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 11)
        {
            gdsView12.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 12)
        {
            gdsView13.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 13)
        {
            gdsView14.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }
        if (score > 14)
        {
            gdsView15.setBackgroundColor(resources.getColor(R.color.purple))
            delay(100)
            barsRemaining ++
        }

        setGDSTestWhiteBarColors(barsRemaining)
    }

    private suspend fun setGDSTestWhiteBarColors(barsNo: Int)
    {
        var viewIDs = intArrayOf(gdsView1.id , gdsView2.id , gdsView3.id , gdsView4.id ,
        gdsView5.id , gdsView6.id, gdsView7.id , gdsView8.id , gdsView9.id , gdsView10.id,
        gdsView11.id , gdsView12.id , gdsView13.id , gdsView14.id , gdsView15.id)
        for (i in barsNo..14)
        {
            view?.findViewById<View>(viewIDs.get(i))!!.setBackgroundColor(resources.getColor(R.color.light_gray))
            delay(100)
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
        var barsRemaining  : Int = 0
        if (score > 0)
        {
            view1.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 5)
        {
            view2.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 10)
        {
            view3.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 15)
        {
            view4.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 20)
        {
            view5.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 25)
        {
            view6.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 30)
        {
            view7.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 35)
        {
            view8.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 40)
        {
            view9.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 45)
        {
            view10.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        if (score > 50)
        {
            view11.setBackgroundColor(resources.getColor(R.color.light_yellow))
            delay(100)
            barsRemaining ++
        }
        setMDSTestWhiteBarColors(barsRemaining)
    }

    suspend fun setMDSTestWhiteBarColors(barsNo : Int)
    {
        var viewIDs = intArrayOf(view1.id , view2.id , view3.id , view4.id , view5.id , view6.id , view7.id ,
            view8.id , view9.id , view10.id , view11.id)

        for (i in barsNo..10)
        {
            view?.findViewById<View>(viewIDs[i])!!.setBackgroundColor(resources.getColor(R.color.light_gray))
            delay(100)
        }
    }


    private fun setDimensionsForDepressionViews()
    {
        var viewWidth = screenWidth / 2
        var viewHeight = screenHeight / 100
        noDepressionLayout.layoutParams = RelativeLayout.LayoutParams(viewWidth , viewHeight * 30)
        var lp: RelativeLayout.LayoutParams? = noDepressionLayout.layoutParams as RelativeLayout.LayoutParams
        lp!!.addRule(RelativeLayout.CENTER_HORIZONTAL)
        lp!!.addRule(RelativeLayout.ABOVE , R.id.mdiTestTitleRelLayout)
        noDepressionLayout.layoutParams = lp
        mildDepressionLayout.layoutParams = RelativeLayout.LayoutParams(viewWidth , (viewHeight * 7.5).toInt())
        var mdlp: RelativeLayout.LayoutParams? = mildDepressionLayout.layoutParams as RelativeLayout.LayoutParams
        mdlp!!.addRule(RelativeLayout.CENTER_HORIZONTAL)
        mdlp!!.addRule(RelativeLayout.ABOVE , R.id.noDepView)
        mildDepressionLayout.layoutParams = mdlp
        moderateDepressionLayout.layoutParams = RelativeLayout.LayoutParams(viewWidth , (viewHeight * 7.5).toInt())
        var moddlp: RelativeLayout.LayoutParams? = moderateDepressionLayout.layoutParams as RelativeLayout.LayoutParams
        moddlp!!.addRule(RelativeLayout.CENTER_HORIZONTAL)
        moddlp!!.addRule(RelativeLayout.ABOVE , R.id.mildDepView)
        mildDepressionLayout.layoutParams = moddlp
        servereDepressionLayout.layoutParams = RelativeLayout.LayoutParams(viewWidth , (viewHeight * 50).toInt())
        var sevdlp: RelativeLayout.LayoutParams? = servereDepressionLayout.layoutParams as RelativeLayout.LayoutParams
        sevdlp!!.addRule(RelativeLayout.CENTER_HORIZONTAL)
        sevdlp!!.addRule(RelativeLayout.ABOVE , R.id.moderateResultView)
        servereDepressionLayout.layoutParams = sevdlp

    }

    //function to create Views above the result bar
    @SuppressLint("SuspiciousIndentation")
    fun createDepressionResultBarViews() {

        depressionProgressBar.layoutParams.width = 0
        depressionProgressBar.layoutParams.height = (RelativeLayout.LayoutParams.WRAP_CONTENT)
        val viewWidth = (screenWidth  / 100) * 15
//        val viewHeight = (screenHeight / 51).toFloat() /2
        val viewHeight = screenHeight / 140
        val yDepressionResultBar = depressionProgressBar.y
        val xDepressionResultBar = depressionProgressBar.x
        val userMDIResult = (arguments!!.getDouble(ARG_PARAM1) * 1).toInt()


        val paint = Paint()
        var depResBarView = customDerpesionProgressView(
                        mainActivity.applicationContext,
                        xDepressionResultBar ,
                          yDepressionResultBar,
                        xDepressionResultBar + screenWidth,
                        yDepressionResultBar + (userMDIResult ) * viewHeight.toFloat(),
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

fun showMDIResultBarViews()
{
    val userMDIResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
    var barHeightSet = false
            if (userMDIResult < 20)
            {
                mainActivity.runOnUiThread {
                    var newNoViewHeight = userMDIResult.toFloat() / 20 * noDepViewHeight
                    var lowY = noDepressionLayout.y
                    noDepressionResultView.updateLayoutParams {
                        height = newNoViewHeight.toInt()
                    }
                    val noLayoutParams = noDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                    noLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    noDepressionResultView.y = lowY
                    barHeightSet =true
                    var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleAnimation.fillAfter = true
                    scaleAnimation.duration = 400
                    noDepressionResultView.startAnimation(scaleAnimation)
                    scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            noDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                }

            }
            if ((userMDIResult == 20.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    noDepressionResultView.alpha = 1f
                }
                barHeightSet = true
                var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleAnimation.fillAfter = true
                scaleAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleAnimation)
            }
            if ((userMDIResult < 25) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newMildViewHeight : Float = (userMDIResult.toFloat() - 20f ) / 5 * mildProgressViewHeight
                    var mildY = mildDepressionLayout.y
                    mildDepressionResultView.updateLayoutParams {
                        height = newMildViewHeight.toInt()
                    }
                    val mildLayoutParams = mildDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                    mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    mildDepressionResultView.y = mildY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)
                        scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                            override fun onAnimationStart(p0: Animation?) {
                                mildDepressionResultView.alpha = 1f
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
            if ((userMDIResult == 25.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    noDepressionResultView.alpha = 1f
                    mildDepressionResultView.alpha = 1f
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)
                        scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildDepressionResultView.alpha = 1f
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
            if ((userMDIResult < 30) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newModViewHeight : Float = (userMDIResult.toFloat() - 25) / 5 * modDepViewHeight
                    var modY = moderateDepressionLayout.y
                    moderateDepressionView.updateLayoutParams{
                        height = newModViewHeight.toInt()
                    }
                    val modLayoutParams = moderateDepressionView.layoutParams as ConstraintLayout.LayoutParams
                    modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    moderateDepressionView.y = modY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildDepressionResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateDepressionView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateDepressionView.alpha =1f
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
            if ((userMDIResult == 30.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateDepressionView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateDepressionView.alpha = 1f
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
            if ((userMDIResult > 30) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newSevViewHeight : Float = (userMDIResult.toFloat() - 30) / 50 * sevDepViewHeight
                    var severeY = servereDepressionLayout.y
                    severeDepressionView.updateLayoutParams{
                        height = newSevViewHeight.toInt()
                    }
                    val sevLayoutParams = severeDepressionView.layoutParams as ConstraintLayout.LayoutParams
                    sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                    severeDepressionView.y = severeY
                }
                barHeightSet = true
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildDepressionResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateDepressionView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateDepressionView.alpha = 1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleSevereAnimation.fillAfter = true
                                        scaleSevereAnimation.duration = 400
                                        severeDepressionView.startAnimation(scaleSevereAnimation)
                                        scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                            override fun onAnimationStart(p0: Animation?) {
                                                severeDepressionView.alpha = 1f
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
            if ((userMDIResult == 50.0) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {

                }
                var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleLowAnimation.fillAfter = true
                scaleLowAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleLowAnimation)
                scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                        scaleMildAnimation.fillAfter = true
                        scaleMildAnimation.duration = 400
                        mildDepressionResultView.startAnimation(scaleMildAnimation)

                        scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                mildDepressionResultView.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                scaleModerateAnimation.fillAfter = true
                                scaleModerateAnimation.duration = 400
                                moderateDepressionView.startAnimation(scaleModerateAnimation)
                                scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                                {
                                    override fun onAnimationStart(p0: Animation?) {
                                        moderateDepressionView.alpha = 1f
                                    }

                                    override fun onAnimationEnd(p0: Animation?) {
                                        var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                        scaleSevereAnimation.fillAfter = true
                                        scaleSevereAnimation.duration = 400
                                        severeDepressionView.startAnimation(scaleSevereAnimation)
                                        scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                            override fun onAnimationStart(p0: Animation?) {
                                                severeDepressionView.alpha = 1f
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
    }


    private fun showBDIResultBarViews() {
        val userMDIResult = (requireArguments().getDouble(ARG_PARAM1) * 1)
        var barHeightSet = false
        if (userMDIResult < 9)
        {
            mainActivity.runOnUiThread {
                var newNoViewHeight = userMDIResult.toFloat() / 9 * noDepViewHeight
                var lowY = noDepressionLayout.y
                noDepressionResultView.updateLayoutParams {
                    height = newNoViewHeight.toInt()
                }
                val noLayoutParams = noDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                noLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                noDepressionResultView.y = lowY
                barHeightSet =true
                var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleAnimation.fillAfter = true
                scaleAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleAnimation)
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }

        }
        if ((userMDIResult == 9.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleAnimation.fillAfter = true
            scaleAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleAnimation)
        }
        if ((userMDIResult < 18) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newMildViewHeight : Float = (userMDIResult.toFloat() - 9f ) / 9 * mildProgressViewHeight
                var mildY = mildDepressionLayout.y
                mildDepressionResultView.updateLayoutParams {
                    height = newMildViewHeight.toInt()
                }
                val mildLayoutParams = mildDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mildDepressionResultView.y = mildY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userMDIResult == 18.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
                mildDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userMDIResult < 29) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newModViewHeight : Float = (userMDIResult.toFloat() - 18) / 11 * modDepViewHeight
                var modY = moderateDepressionLayout.y
                moderateDepressionView.updateLayoutParams{
                    height = newModViewHeight.toInt()
                }
                val modLayoutParams = moderateDepressionView.layoutParams as ConstraintLayout.LayoutParams
                modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                moderateDepressionView.y = modY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha =1f
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
        if ((userMDIResult == 29.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
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
        if ((userMDIResult < 63) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userMDIResult.toFloat() - 29) / 34 * sevDepViewHeight
                var severeY = servereDepressionLayout.y
                severeDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = severeDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                severeDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userMDIResult == 63.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userMDIResult > 63) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userMDIResult.toFloat() - 63) / 11 * moreSevDepViewHeight
                var severeY = mostSevereDepressionLayout.y
                mostSevereDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = mostSevereDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mostSevereDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                            var scaleMostSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleMostSevereAnimation.fillAfter = true
                                            scaleMostSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(scaleMostSevereAnimation)
                                            scaleMostSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
        if ((userMDIResult == 73.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var mostScaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            mostScaleSevereAnimation.fillAfter = true
                                            mostScaleSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(mostScaleSevereAnimation)
                                            mostScaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
    }

    private fun showZUNGResultBarViews() {
        val userZUNGResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
        var barHeightSet = false
        if (userZUNGResult < 25)
        {
            mainActivity.runOnUiThread {
                var newNoViewHeight = userZUNGResult.toFloat() / 24 * noDepViewHeight
                var lowY = noDepressionLayout.y
                noDepressionResultView.updateLayoutParams {
                    height = newNoViewHeight.toInt()
                }
                val noLayoutParams = noDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                noLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                noDepressionResultView.y = lowY
                barHeightSet =true
                var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleAnimation.fillAfter = true
                scaleAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleAnimation)
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }

        }
        if ((userZUNGResult == 25.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleAnimation.fillAfter = true
            scaleAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleAnimation)
        }
        if ((userZUNGResult < 49) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newMildViewHeight : Float = (userZUNGResult.toFloat() - 25f ) / 25 * mildProgressViewHeight
                var mildY = mildDepressionLayout.y
                mildDepressionResultView.updateLayoutParams {
                    height = newMildViewHeight.toInt()
                }
                val mildLayoutParams = mildDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mildDepressionResultView.y = mildY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userZUNGResult == 49.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
                mildDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userZUNGResult < 59) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newModViewHeight : Float = (userZUNGResult.toFloat() - 50) / 10 * modDepViewHeight
                var modY = moderateDepressionLayout.y
                moderateDepressionView.updateLayoutParams{
                    height = newModViewHeight.toInt()
                }
                val modLayoutParams = moderateDepressionView.layoutParams as ConstraintLayout.LayoutParams
                modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                moderateDepressionView.y = modY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha =1f
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
        if ((userZUNGResult == 59.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
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
        if ((userZUNGResult < 69) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userZUNGResult.toFloat() - 60) / 10 * sevDepViewHeight
                var severeY = servereDepressionLayout.y
                severeDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = severeDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                severeDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userZUNGResult == 69.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userZUNGResult > 70) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userZUNGResult.toFloat() - 69) / 11 * moreSevDepViewHeight
                var severeY = mostSevereDepressionLayout.y
                mostSevereDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = mostSevereDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mostSevereDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                            var scaleMostSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleMostSevereAnimation.fillAfter = true
                                            scaleMostSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(scaleMostSevereAnimation)
                                            scaleMostSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
        if ((userZUNGResult == 80.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var mostScaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            mostScaleSevereAnimation.fillAfter = true
                                            mostScaleSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(mostScaleSevereAnimation)
                                            mostScaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
    }

    private fun showHAMMResultBarViews() {
        val userMDIResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
        var barHeightSet = false
        if (userMDIResult < 7)
        {
            mainActivity.runOnUiThread {
                var newNoViewHeight = userMDIResult.toFloat() / 7 * noDepViewHeight
                var lowY = noDepressionLayout.y
                noDepressionResultView.updateLayoutParams {
                    height = newNoViewHeight.toInt()
                }
                val noLayoutParams = noDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                noLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                noDepressionResultView.y = lowY
                barHeightSet =true
                var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                scaleAnimation.fillAfter = true
                scaleAnimation.duration = 400
                noDepressionResultView.startAnimation(scaleAnimation)
                scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                {
                    override fun onAnimationStart(p0: Animation?) {
                        noDepressionResultView.alpha = 1f
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                })
            }

        }
        if ((userMDIResult == 7.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleAnimation.fillAfter = true
            scaleAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleAnimation)
        }
        if ((userMDIResult < 17) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newMildViewHeight : Float = (userMDIResult.toFloat() - 7f ) / 11 * mildProgressViewHeight
                var mildY = mildDepressionLayout.y
                mildDepressionResultView.updateLayoutParams {
                    height = newMildViewHeight.toInt()
                }
                val mildLayoutParams = mildDepressionResultView.layoutParams as ConstraintLayout.LayoutParams
                mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mildDepressionResultView.y = mildY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userMDIResult == 17.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                noDepressionResultView.alpha = 1f
                mildDepressionResultView.alpha = 1f
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)
                    scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
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
        if ((userMDIResult < 24) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newModViewHeight : Float = (userMDIResult.toFloat() - 17) / 7 * modDepViewHeight
                var modY = moderateDepressionLayout.y
                moderateDepressionView.updateLayoutParams{
                    height = newModViewHeight.toInt()
                }
                val modLayoutParams = moderateDepressionView.layoutParams as ConstraintLayout.LayoutParams
                modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                moderateDepressionView.y = modY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha =1f
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
        if ((userMDIResult == 24.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
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
        if ((userMDIResult < 54) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userMDIResult.toFloat() - 24) / 30 * sevDepViewHeight
                var severeY = servereDepressionLayout.y
                severeDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = severeDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                severeDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userMDIResult == 54.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
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
        if ((userMDIResult > 63) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {
                var newSevViewHeight : Float = (userMDIResult.toFloat() - 63) / 11 * moreSevDepViewHeight
                var severeY = mostSevereDepressionLayout.y
                mostSevereDepressionView.updateLayoutParams{
                    height = newSevViewHeight.toInt()
                }
                val sevLayoutParams = mostSevereDepressionView.layoutParams as ConstraintLayout.LayoutParams
                sevLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                mostSevereDepressionView.y = severeY
            }
            barHeightSet = true
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                            var scaleMostSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleMostSevereAnimation.fillAfter = true
                                            scaleMostSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(scaleMostSevereAnimation)
                                            scaleMostSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
        if ((userMDIResult == 73.0) && (!barHeightSet))
        {
            mainActivity.runOnUiThread {

            }
            var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
            scaleLowAnimation.fillAfter = true
            scaleLowAnimation.duration = 400
            noDepressionResultView.startAnimation(scaleLowAnimation)
            scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
            {
                override fun onAnimationStart(p0: Animation?) {
                    noDepressionResultView.alpha = 1f
                }

                override fun onAnimationEnd(p0: Animation?) {
                    var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                    scaleMildAnimation.fillAfter = true
                    scaleMildAnimation.duration = 400
                    mildDepressionResultView.startAnimation(scaleMildAnimation)

                    scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            mildDepressionResultView.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleModerateAnimation.fillAfter = true
                            scaleModerateAnimation.duration = 400
                            moderateDepressionView.startAnimation(scaleModerateAnimation)
                            scaleModerateAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    moderateDepressionView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleSevereAnimation.fillAfter = true
                                    scaleSevereAnimation.duration = 400
                                    severeDepressionView.startAnimation(scaleSevereAnimation)
                                    scaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                        override fun onAnimationStart(p0: Animation?) {
                                            severeDepressionView.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var mostScaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            mostScaleSevereAnimation.fillAfter = true
                                            mostScaleSevereAnimation.duration = 400
                                            mostSevereDepressionView.startAnimation(mostScaleSevereAnimation)
                                            mostScaleSevereAnimation.setAnimationListener(object : Animation.AnimationListener{
                                                override fun onAnimationStart(p0: Animation?) {
                                                    mostSevereDepressionView.alpha = 1f
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

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })
        }
    }

    suspend fun ResultBPIBArViews(runningResult: Int)
    {
        val userBAISeverityResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
        val userBAIInteferenceResult = (arguments!!.getDouble(ARG_PARAM2) * 1)
        var barHeightSet = false

        //re set the height for each resultView
        lowProgressBar.updateLayoutParams {
            height = lowProgressViewHeight
        }

        mildProgressBar.updateLayoutParams {
            height = mildProgressViewHeight
        }

        moderateProgressBar.updateLayoutParams {
            height = moderateProgressViewHeight
        }

        severeProgressBar.updateLayoutParams {
            height = severeProgreessViewHeight
        }

        when(runningResult)
        {
            0 ->
            {
                highlightBPiTextView(1)
                if (userBAISeverityResult < 1)
                {
                    mainActivity.runOnUiThread {
                        var newLowViewHeight = (userBAISeverityResult.toFloat() / 1)  * lowProgressViewHeight
                        var lowY = lowProgressBar.y
                        lowProgressBar.updateLayoutParams {
                            height = newLowViewHeight.toInt()
                        }
                        val lowLayoutParams = lowProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        lowLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        lowProgressBar.y = lowY
                        barHeightSet =true
                        var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleAnimation.fillAfter = true
                        scaleAnimation.duration = 400
                        lowProgressBar.startAnimation(scaleAnimation)
                        scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                lowProgressBar.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                }
                if ((userBAISeverityResult.toFloat() == 1.0f) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowProgressBar.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleAnimation.fillAfter = true
                    scaleAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleAnimation)
                }
                if ((userBAISeverityResult < 4) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newMildViewHeight : Float = (userBAISeverityResult.toFloat() -1) / 3 * mildProgressViewHeight
                        var mildY = mildProgressBar.y
                        mildProgressBar.updateLayoutParams {
                            height = newMildViewHeight.toInt()
                        }
                        val mildLayoutParams = mildProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        mildProgressBar.y = mildY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
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
                if ((userBAISeverityResult == 4.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowProgressBar.alpha = 1f
                        mildProgressBar.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
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
                if ((userBAISeverityResult < 7) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userBAISeverityResult.toFloat() - 4) / 3 * moderateProgressViewHeight
                        var modY = moderateProgressBar.y
                        moderateProgressBar.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val modLayoutParams = moderateProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        moderateProgressBar.y = modY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha =1f
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
                if ((userBAISeverityResult == 7.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha = 1f
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

                if ((userBAISeverityResult < 10) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userBAISeverityResult.toFloat() - 7) / 3 * severeProgreessViewHeight
                        var modY = severeProgressBar.y
                        severeProgressBar.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val modLayoutParams = severeProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        severeProgressBar.y = modY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            lowProgressBar.alpha = 1f
                                            moderateProgressBar.alpha =1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleSevereAnimation.fillAfter = true
                                            scaleSevereAnimation.duration = 400
                                            severeProgressBar.startAnimation(scaleSevereAnimation)
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
                if ((userBAISeverityResult == 10.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleModerateAnimation.fillAfter = true
                                            scaleModerateAnimation.duration = 400
                                            moderateProgressBar.startAnimation(scaleModerateAnimation)
                                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                            {
                                                override fun onAnimationStart(p0: Animation?) {
                                                    moderateProgressBar.alpha =1f
                                                }

                                                override fun onAnimationEnd(p0: Animation?) {
                                                    severeProgressBar.alpha =1f
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
                lowProgressBar.alpha = 0f
                mildProgressBar.alpha = 0f
                moderateProgressBar.alpha = 0f
                severeProgressBar.alpha = 0f
                //re set the height for each resultView
                lowResultView.updateLayoutParams {
                    height = lowProgressViewHeight
                }

                mildResultView.updateLayoutParams {
                    height = mildProgressViewHeight
                }

                moderateResultView.updateLayoutParams {
                    height = moderateProgressViewHeight
                }


                severeResultView.updateLayoutParams {
                    height = severeProgreessViewHeight
                }

            }
            1 ->
            {
                highlightBPiTextView(2)
                if (userBAIInteferenceResult < 1)
                {
                    mainActivity.runOnUiThread {
                        var newLowViewHeight = (userBAIInteferenceResult.toFloat() / 1)  * lowProgressViewHeight
                        var lowY = lowProgressBar.y
                        lowProgressBar.updateLayoutParams {
                            height = newLowViewHeight.toInt()
                        }
                        val lowLayoutParams = lowProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        lowLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        lowProgressBar.y = lowY
                        barHeightSet =true
                        var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                        scaleAnimation.fillAfter = true
                        scaleAnimation.duration = 400
                        lowProgressBar.startAnimation(scaleAnimation)
                        scaleAnimation.setAnimationListener(object : Animation.AnimationListener
                        {
                            override fun onAnimationStart(p0: Animation?) {
                                lowProgressBar.alpha = 1f
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                            }

                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                        })
                    }

                }
                if ((userBAIInteferenceResult.toFloat() == 1.0f) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowProgressBar.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleAnimation.fillAfter = true
                    scaleAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleAnimation)
                }
                if ((userBAIInteferenceResult < 4) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newMildViewHeight : Float = (userBAIInteferenceResult.toFloat() -1) / 3 * mildProgressViewHeight
                        var mildY = mildProgressBar.y
                        mildProgressBar.updateLayoutParams {
                            height = newMildViewHeight.toInt()
                        }
                        val mildLayoutParams = mildProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        mildLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        mildProgressBar.y = mildY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener{

                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
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
                if ((userBAIInteferenceResult == 4.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        lowProgressBar.alpha = 1f
                        mildProgressBar.alpha = 1f
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)
                            scaleMildAnimation.setAnimationListener(object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
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
                if ((userBAIInteferenceResult < 7) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userBAIInteferenceResult.toFloat() - 4) / 3 * moderateProgressViewHeight
                        var modY = moderateProgressBar.y
                        moderateProgressBar.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val modLayoutParams = moderateProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        moderateProgressBar.y = modY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha =1f
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
                if ((userBAIInteferenceResult == 7.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha = 1f
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
                if ((userBAISeverityResult < 10) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userBAIInteferenceResult.toFloat() - 7) / 3 * severeProgreessViewHeight
                        var modY = severeProgressBar.y
                        severeProgressBar.updateLayoutParams{
                            height = newModViewHeight.toInt()
                        }
                        val modLayoutParams = severeProgressBar.layoutParams as ConstraintLayout.LayoutParams
                        modLayoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
                        severeProgressBar.y = modY
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildResultView.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                    {
                                        override fun onAnimationStart(p0: Animation?) {
                                            lowProgressBar.alpha = 1f
                                            moderateProgressBar.alpha =1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {
                                            var scaleSevereAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleSevereAnimation.fillAfter = true
                                            scaleSevereAnimation.duration = 400
                                            severeProgressBar.startAnimation(scaleSevereAnimation)
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
                if ((userBAIInteferenceResult == 10.0) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                    }
                    barHeightSet = true
                    var scaleLowAnimation = ScaleAnimation(1f , 1f , 0f , 1f)
                    scaleLowAnimation.fillAfter = true
                    scaleLowAnimation.duration = 400
                    lowProgressBar.startAnimation(scaleLowAnimation)
                    scaleLowAnimation.setAnimationListener( object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?) {
                            lowProgressBar.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            var scaleMildAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                            scaleMildAnimation.fillAfter = true
                            scaleMildAnimation.duration = 400
                            mildProgressBar.startAnimation(scaleMildAnimation)

                            scaleMildAnimation.setAnimationListener( object : Animation.AnimationListener
                            {
                                override fun onAnimationStart(p0: Animation?) {
                                    mildProgressBar.alpha = 1f
                                }

                                override fun onAnimationEnd(p0: Animation?) {
                                    var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                    scaleModerateAnimation.fillAfter = true
                                    scaleModerateAnimation.duration = 400
                                    moderateProgressBar.startAnimation(scaleModerateAnimation)
                                    scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(p0: Animation?) {
                                            moderateProgressBar.alpha = 1f
                                        }

                                        override fun onAnimationEnd(p0: Animation?) {

                                            var scaleModerateAnimation = ScaleAnimation(1f, 1f, 0f, 1f)
                                            scaleModerateAnimation.fillAfter = true
                                            scaleModerateAnimation.duration = 400
                                            moderateProgressBar.startAnimation(scaleModerateAnimation)
                                            scaleModerateAnimation.setAnimationListener(object : Animation.AnimationListener
                                            {
                                                override fun onAnimationStart(p0: Animation?) {
                                                    moderateProgressBar.alpha =1f
                                                }

                                                override fun onAnimationEnd(p0: Animation?) {
                                                    severeProgressBar.alpha =1f
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
                lowProgressBar.alpha = 0f
                mildProgressBar.alpha = 0f
                moderateProgressBar.alpha = 0f
                severeProgressBar.alpha = 0f
                //re set the height for each resultView
                lowResultView.updateLayoutParams {
                    height = lowProgressViewHeight
                }

                mildResultView.updateLayoutParams {
                    height = mildProgressViewHeight
                }

                moderateResultView.updateLayoutParams {
                    height = moderateProgressViewHeight
                }


                severeResultView.updateLayoutParams {
                    height = severeProgreessViewHeight
                }

            }

        }

    }


    suspend fun ResultSTAIBArViews(runningResult: Int)
    {
        val userStaiStateResult = (arguments!!.getDouble(ARG_PARAM1) * 1)
        val userStaiTraitResult = (arguments!!.getDouble(ARG_PARAM2) * 1)
        var barHeightSet = false

        //re set the height for each resultView
        lowResultView.updateLayoutParams {
            height = lowProgressViewHeight
        }

        mildResultView.updateLayoutParams {
            height = mildProgressViewHeight
        }

        moderateResultView.updateLayoutParams {
            height = moderateProgressViewHeight
        }

        when(runningResult)
        {
            0 ->
            {
                highlightBPiTextView(1)
                if (userStaiStateResult < 38)
                {
                    mainActivity.runOnUiThread {
                        var newLowViewHeight = userStaiStateResult.toFloat() - 20 * lowProgressViewHeight
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
                if ((userStaiStateResult == 37.0) && (!barHeightSet))
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
                if ((userStaiStateResult < 45) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newMildViewHeight : Float = (userStaiStateResult.toFloat() -38) / 7 * mildProgressViewHeight
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
                if ((userStaiStateResult == 45.0) && (!barHeightSet))
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
                if ((userStaiStateResult < 80) && (!barHeightSet))
                {
                    mainActivity.runOnUiThread {
                        var newModViewHeight : Float = (userStaiStateResult.toFloat() - 44) / 36 * moderateProgressViewHeight
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
                if ((userStaiStateResult == 80.0) && (!barHeightSet))
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
                delay(4000)
                lowResultView.alpha = 0f
                mildResultView.alpha = 0f
                moderateResultView.alpha = 0f
                //re set the height for each resultView
                lowResultView.updateLayoutParams {
                    height = lowProgressViewHeight
                }

                mildResultView.updateLayoutParams {
                    height = mildProgressViewHeight
                }

                moderateResultView.updateLayoutParams {
                    height = moderateProgressViewHeight
                }

            }
        1 ->
        {
            highlightBPiTextView(2)
            if (userStaiTraitResult < 38)
            {
                mainActivity.runOnUiThread {
                    var newLowViewHeight = userStaiTraitResult.toFloat() - 20 * lowProgressViewHeight
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
            if ((userStaiTraitResult == 37.0) && (!barHeightSet))
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
            if ((userStaiTraitResult < 45) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newMildViewHeight : Float = (userStaiTraitResult.toFloat() -38) / 7 * mildProgressViewHeight
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
            if ((userStaiTraitResult == 45.0) && (!barHeightSet))
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
            if ((userStaiTraitResult < 80) && (!barHeightSet))
            {
                mainActivity.runOnUiThread {
                    var newModViewHeight : Float = (userStaiTraitResult.toFloat() - 44) / 36 * moderateProgressViewHeight
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
            if ((userStaiTraitResult == 80.0) && (!barHeightSet))
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
            delay(4000)
            lowResultView.alpha = 0f
            mildResultView.alpha = 0f
            moderateResultView.alpha = 0f
            //re set the height for each resultView
            lowResultView.updateLayoutParams {
                height = lowProgressViewHeight
            }

            mildResultView.updateLayoutParams {
                height = mildProgressViewHeight
            }

            moderateResultView.updateLayoutParams {
                height = moderateProgressViewHeight
            }

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
                pssTestScore.setTypeface(pisTestScore.typeface , Typeface.BOLD)
                pisTestScore.setTypeface(null , Typeface.NORMAL)
            }

            }
            2 ->
            {
                mainActivity.runOnUiThread {
                    totalPISScore.setTypeface(totalPISScore.getTypeface(), android.graphics.Typeface.BOLD)
                    totalPSSScore.setTypeface(null, android.graphics.Typeface.NORMAL)
                    pisTestScore.setTypeface(pisTestScore.typeface , Typeface.BOLD)
                    pssTestScore.setTypeface(null , Typeface.NORMAL)
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
            baiTestSummaryB.text = mainActivity.resources.getString(R.string.BAIRESULT1B)
            baiTestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            baiTestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
        }
        if ((testSum >=22) && (testSum <= 35))
        {
            baiTestSummary.text = mainActivity.resources.getString(R.string.BAIRESULT2)
            baiTestSummaryB.text = mainActivity.resources.getString(R.string.BAIRESULT2B)
            baiTestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            baiTestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
        }
        if (testSum >=36)
        {
            baiTestSummary.text = mainActivity.resources.getString(R.string.BAIRESULT3)
            baiTestSummaryB.text = mainActivity.resources.getString(R.string.BAIRESULT3B)
            baiTestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            baiTestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyRelLayout.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyRelLayout.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
        }
    }

    fun setGasTestSummary(testSum : Int)
    {
        if ((testSum >=0) && (testSum <= 9))
        {
            GAStestSummary.text = mainActivity.resources.getString(R.string.gas_low_anxiety_a)
            GAStestSummaryB.text = mainActivity.resources.getString(R.string.gas_low_anxiety_b)
            GAStestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            GAStestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
    //        lowAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            lowAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
//            var viewLength = lowAnxietyResultView.width
//            var newViewLength = (viewLength * (testSum / 9).toFloat()).toFloat()
            lowAnxietyResultView
            lowAnxietyResultView
            mildAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            //request layouts

        }
        if ((testSum >=10) && (testSum <= 14))
        {
            GAStestSummary.text = mainActivity.resources.getString(R.string.gas_mild_anxiety_a)
            GAStestSummaryB.text = mainActivity.resources.getString(R.string.gas_mild_anxiety_b)
            GAStestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            GAStestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
   //         mildAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            mildAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            moderateAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)

            //request layouts

        }
        if ((testSum >=15) && (testSum <= 19))
        {
            GAStestSummary.text = mainActivity.resources.getString(R.string.gas_moderate_anxiety_a)
            GAStestSummaryB.text = mainActivity.resources.getString(R.string.gas_moderate_anxiety_b)
            GAStestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            GAStestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            mildAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
   //         moderateAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            moderateAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)
            severeAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)


        }
        if ((testSum >=20) && (testSum <= 30))
        {
            GAStestSummary.text = mainActivity.resources.getString(R.string.gas_severe_anxiety_a)
            GAStestSummaryB.text = mainActivity.resources.getString(R.string.gas_severe_anxiety_b)
            GAStestSummary.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            GAStestSummaryB.apply {
                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            lowAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            mildAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            moderateAnxietyResultView.setBackgroundResource(R.drawable.white_mdille__border_rel_layout)
            severeAnxietyResultView.setBackgroundResource(R.drawable.blue_middle_border_rel_layout)

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
        fun newInstance(param1: Double, param2: Double , param3: Int , param4 : Double?, param5 : ArrayList<Int>?) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, param1)
                    putDouble(ARG_PARAM2 , param2)
                    if (param4 != null)
                        putDouble(ARG_PARAM4 , param4!!)
                    putInt(ARG_PARAM3 , param3)
                    if (param5 != null)
                        putSerializable(ARG_PARAM5 , param5)
                }
            }
    }
}
package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Path
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.RealmDB.Test
import com.example.cvdriskestimator.databinding.FragmentResultTimelineBinding
import io.realm.Realm
import io.realm.RealmResults


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ResultTimelineFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mainActivity : MainActivity
    private lateinit var realm : Realm
    private lateinit var bindingFragmentTimelineFragment : FragmentResultTimelineBinding
    private lateinit var canvas: android.graphics.Canvas
    private lateinit var paint: Paint
    private var scrollViewXCoord : Float = 0f
    private var scrollViewYCoord : Float = 0f
    var scrollViewWidth : Int = 0
    var scrollViewHeight : Int = 0
    private lateinit var  tests : RealmResults<Test>
    private var timeLineViewsCreated : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingFragmentTimelineFragment = FragmentResultTimelineBinding.inflate(inflater , container, false)
        return bindingFragmentTimelineFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        param1 = this.arguments!!.getString("patientId")!!
        param2 = this.arguments!!.getString("testName")

        canvas = android.graphics.Canvas()

        paint = Paint()

        //initialize the paint object
        paint.apply {
            isAntiAlias = true
            color = mainActivity.getColor(R.color.black)
            style = Paint.Style.STROKE
        }

        //use the draw methods of cnavs to paint the timeline ui
        tests = getAllTests()

        bindingFragmentTimelineFragment.timelineScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    bindingFragmentTimelineFragment.timelineScrollView.post {

                        val point = IntArray(2)
                        bindingFragmentTimelineFragment.timelineScrollView.getLocationOnScreen(point)
                        scrollViewXCoord = point[0].toFloat()
                        scrollViewYCoord = point[1].toFloat()


                        bindingFragmentTimelineFragment.timelineConLayout.doOnLayout {
                            scrollViewWidth = it.measuredWidth
                            scrollViewHeight = it.measuredHeight
                        }

//                        if (!timeLineViewsCreated)
//                        {
//                            createTimeLineViewsProgrammatically()
//                            timeLineViewsCreated = true
//                        }

                        drawVerticalChartOnImgV(getAllTests())

//                        //draw the vertical axis line
//                        canvas!!.drawLine(scrollViewXCoord + (scrollViewHeight / 20f) ,scrollViewYCoord + (scrollViewWidth / 2f) , scrollViewXCoord + (scrollViewHeight - (scrollViewHeight / 20f)) ,scrollViewYCoord +  (scrollViewWidth / 2f) , paint)
//
//                        var lineHeight = (scrollViewHeight - ((scrollViewHeight / 20f) *2))
//                        var linePieces = (lineHeight / tests.size -1)
//
//                        //draw small horizontal lines in between the vertical line
//                        for (i in 0..tests.size -1)
//                        {
//                            for (i in 0..10)
//                            {
//                                canvas!!.drawLine(scrollViewXCoord + (scrollViewHeight / 20f) + (linePieces * i) + (linePieces / i),scrollViewYCoord +  ((scrollViewWidth  / 2f) - scrollViewWidth/20) , scrollViewHeight + (scrollViewHeight / 20f) + (linePieces * i) + (linePieces / i), scrollViewWidth + ((scrollViewWidth  / 2f) + scrollViewWidth/20), paint)
//                            }
//                            canvas!!.drawLine(scrollViewXCoord + (scrollViewHeight / 20f) + (linePieces * i) ,scrollViewYCoord + ((scrollViewWidth  / 2f) - scrollViewWidth/18) , scrollViewHeight + (scrollViewHeight / 20f) + (linePieces * (i + 1)), scrollViewWidth + ((scrollViewWidth  / 2f) + scrollViewWidth/18), paint)
//                        }
                    }
                }
            }
        )
    }

    private fun createTimeLineViewsProgrammatically()
    {
        var mainVerticalView = RelativeLayout(mainActivity.applicationContext)
        var relLayoutParams  = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT , RelativeLayout.LayoutParams.WRAP_CONTENT)
        mainVerticalView.layoutParams = relLayoutParams
        bindingFragmentTimelineFragment.timelineScrollView.findViewById<RelativeLayout>(R.id.scrollViewRelLayout).addView(mainVerticalView)
        mainVerticalView.x = scrollViewXCoord + scrollViewWidth / 2
        mainVerticalView.y = scrollViewYCoord  + scrollViewHeight / 100
        var mainVerticalViewHeight = (tests.size * scrollViewHeight / 20f)
        mainVerticalView.layoutParams.height = mainVerticalViewHeight.toInt()
        var mainVerticalViewWidth = scrollViewWidth / 40
        mainVerticalView.layoutParams.width = mainVerticalViewWidth
        mainVerticalView.setBackgroundColor(mainActivity.getColor(R.color.black))
        //add vertical views programmatically
        var arrayOfViewsIds = arrayListOf<Int>()

        var mainVerticalViewSegment = (mainVerticalView.layoutParams.height / tests.size)

        var firstDummyViewId : Int = 0

        for (i in 0..tests.size -1)
        {
            var dummyView = RelativeLayout(mainActivity.applicationContext)

            if (i == 0)
            {
                val lp = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT
                )
                dummyView.id = View.generateViewId()
                firstDummyViewId = dummyView.id
                mainVerticalView.layoutParams = lp
                mainVerticalView.addView(dummyView)
                //set the view dimens and position
                dummyView.x = mainVerticalView.x
                dummyView.y = (mainVerticalView.y + mainVerticalViewSegment * i)
                dummyView.layoutParams.width = mainVerticalViewWidth * 2
                dummyView.layoutParams.height = 5
                dummyView.setBackgroundColor(mainActivity.getColor(R.color.black))
                mainVerticalView.invalidate()
            }
            else
            {
                dummyView.id = View.generateViewId()
                val lp = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                //set the view dimens and position

                lp.addRule(RelativeLayout.BELOW, firstDummyViewId)
                mainVerticalView.layoutParams = lp
                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                dummyView.layoutParams = params
                dummyView.y = (mainVerticalView.y + mainVerticalViewSegment * i)
                dummyView.layoutParams.width = mainVerticalViewWidth * 2
                dummyView.layoutParams.height = 5
                dummyView.setBackgroundColor(mainActivity.getColor(R.color.black))


                mainVerticalView.addView(dummyView , lp)
                firstDummyViewId = dummyView.id
                mainVerticalView.invalidate()
            }

            //add the view to the parent view group
            //create set of smaller ruler-like line views
//            for (k in 1..10)
//            {
//                var smallDymmyView = RelativeLayout(mainActivity.applicationContext)
//                mainVerticalView.addView(smallDymmyView)
//                //set the view dimens and position
//                smallDymmyView.x = mainVerticalView.x
//                smallDymmyView.y = (mainVerticalView.y + (mainVerticalViewSegment * k) + (k * mainVerticalViewSegment / 10))
//                smallDymmyView.layoutParams.width = (mainVerticalViewWidth * 1.2).toInt()
//                smallDymmyView.layoutParams.height = 3
//                smallDymmyView.setBackgroundColor(mainActivity.getColor(R.color.black))
//                mainVerticalView.invalidate()
//            }
        }
    }

    private fun getAllTests() : RealmResults<Test>
    {
        realm = Realm.getDefaultInstance()
        //get all the tests related with the specific test and patient
        var tests : RealmResults<Test>? = null
        when (param2)
        {
            "CardioVascularDisease" ->
            {
                tests = realm.where(Test::class.java).isNotNull("SSB") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "DIABETES" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientPAM") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Major Depression Index" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientMDIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Beck Anxiety Index" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientBAIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Mediterranean Diet Test" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientMDSQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Brief Pain Inventory" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientBPIQ1") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
            "Geriatric Depression Scale" ->
            {
                tests = realm.where(Test::class.java).isNotNull("patientGDSQ6") .equalTo("patientId" , param1).equalTo("testName" , param2).findAll()
            }
        }
        return tests!!
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    //desing lines on imageview
    private fun drawVerticalChartOnImgV(allTests : RealmResults<Test>)
    {
        //create bitmap with delcared dimensions
        var bitmap = Bitmap.createBitmap(scrollViewWidth , scrollViewHeight , Bitmap.Config.ARGB_8888)

        //storing the canvas on the bitmap
        var canvas = Canvas(bitmap)

        //initialize the paint object
        paint.apply {
            isAntiAlias = true
            color = mainActivity.getColor(R.color.black)
            style = Paint.Style.STROKE
        }

        //draw the vertical axis line
        canvas!!.drawLine((scrollViewWidth / 2f) ,(scrollViewHeight / 20f) ,  (scrollViewWidth / 2f) , (scrollViewHeight - (scrollViewHeight / 20f))  , paint)

        var lineHeight = (scrollViewHeight - ((scrollViewHeight / 20f) *2))
        var linePieces = (lineHeight / allTests.size -1)

        //draw small horizontal lines in between the vertical line
        for (i in 0..allTests.size -1)
        {
            for (k in 0..10)
            {
                canvas!!.drawLine(((scrollViewWidth  / 2f) - scrollViewWidth/20) , (scrollViewHeight / 20f) + (linePieces * i) + ((linePieces * (k / 10))) , ((scrollViewWidth  / 2f) + scrollViewWidth/20) , (scrollViewHeight / 20f) + (linePieces * i) + ((linePieces * (k / 10))), paint)
            }
            canvas!!.drawLine(((scrollViewWidth  / 2f) - scrollViewWidth/18) , (scrollViewHeight / 20f) + (linePieces * i)  , ((scrollViewWidth  / 2f) + scrollViewWidth/18) , (scrollViewHeight / 20f) + (linePieces * (i)), paint)
        }

        bindingFragmentTimelineFragment.timeLineImgV.setImageBitmap(bitmap)

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultTimelineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    inner class myCustomCanvasView(allTests : RealmResults<Test>) : View(mainActivity.applicationContext)
    {
        val paint = Paint()
        val path = Path()
        var viewWidth : Int = 0
        var viewHeight : Int = 0
        var latests : RealmResults<Test>

//        override fun onDraw(canvas: android.graphics.Canvas?) {
//            super.onDraw(canvas)
//
//            viewWidth  = this.layoutParams.width
//            viewHeight = this.layoutParams.height
//
//            //initialize the paint object
//            paint.apply {
//                isAntiAlias = true
//                color = mainActivity.getColor(R.color.black)
//                style = Paint.Style.STROKE
//            }
//
//            //draw the vertical axis line
//            canvas!!.drawLine((viewHeight / 20f) , (viewWidth / 2f) , (viewHeight - (viewHeight / 20f)) , (viewWidth / 2f) , paint)
//
//            var lineHeight = (viewHeight - ((viewHeight / 20f) *2))
//            var linePieces = (lineHeight / latests.size -1)
//
//            //draw small horizontal lines in between the vertical line
//            for (i in 0..latests.size -1)
//            {
//                for (k in 0..10)
//                {
//                    canvas!!.drawLine( ((viewWidth  / 2f) - viewWidth/20) ,(viewHeight / 20f) + (linePieces * i) + (linePieces / k), ((viewWidth  / 2f) + viewWidth/20)  ,(viewHeight / 20f) + (linePieces * i) + (linePieces / k), paint)
//                }
//                canvas!!.drawLine((viewHeight / 20f) + (linePieces * i) , ((viewWidth  / 2f) - viewWidth/18) , (viewHeight / 20f) + (linePieces * (i + 1)), ((viewWidth  / 2f) + viewWidth/18), paint)
//            }
//
//        }

        init {
            latests = allTests
        }

    }

}
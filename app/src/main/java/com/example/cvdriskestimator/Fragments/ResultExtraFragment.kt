package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R
import com.example.cvdriskestimator.databinding.FragmentResultPdqTestBinding
import com.example.cvdriskestimator.databinding.FragmentResultPdqTestBindingImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val PARAM1 : String = "pdqTestResults"


class ResultExtraFragment : Fragment() {


    private lateinit var bindingImpl: FragmentResultPdqTestBinding
    private  var testResults = IntArray(8)
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingImpl = FragmentResultPdqTestBindingImpl.inflate(inflater , container, false)
        return bindingImpl.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testResults = this.arguments?.getIntArray(PARAM1)!!
        displayResults()
    }

    private fun displayResults()
    {
        var overallTestResult : Int = 0
        for (i in 0..testResults.size-1)
        {
            overallTestResult += testResults.get(i)
        }
        overallTestResult /= 8
        bindingImpl.totalScoreDescTxtV.text = String.format(mainActivity.resources.getString(R.string.pdq_test_score , overallTestResult.toString())) + " %"
        GlobalScope.launch(Dispatchers.Main) {
            highlightResultViews(1 , testResults.get(0))
            highlightResultViews(2, testResults.get(1))
            highlightResultViews(3 , testResults.get(2))
            highlightResultViews(4 , testResults.get(3))
            highlightResultViews(5, testResults.get(4))
            highlightResultViews(6 , testResults.get(5))
            highlightResultViews(7 , testResults.get(6))
            highlightResultViews(8 , testResults.get(7))
        }

    }

    private suspend fun highlightResultViews(index : Int , score : Int)
    {
            when(index)
            {
                1 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat1lv1view.setBackgroundColor(mainActivity.resources.getColor(R.color.green_1))
                        bindingImpl.catLowerTxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat1lv1view2.setBackgroundColor(mainActivity.resources.getColor(R.color.green_2))
                        bindingImpl.catlowerMidtxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat1lv1view3.setBackgroundColor(mainActivity.resources.getColor(R.color.green_3))
                        bindingImpl.catMidTxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat1lv1view4.setBackgroundColor(mainActivity.resources.getColor(R.color.green_4))
                        bindingImpl.catMidUpperTxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat1lv1view5.setBackgroundColor(mainActivity.resources.getColor(R.color.green_5))
                        bindingImpl.catUpperTxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                2 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat2lv1view6.setBackgroundColor(mainActivity.resources.getColor(R.color.orange_1))
                        bindingImpl.catLowerTxtV2.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat2lv1view7.setBackgroundColor(mainActivity.resources.getColor(R.color.orange_2))
                        bindingImpl.catlowerMidtxtV2.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat2lv1view8.setBackgroundColor(mainActivity.resources.getColor(R.color.orange_3))
                        bindingImpl.catMidTxtV2.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat2lv1view9.setBackgroundColor(mainActivity.resources.getColor(R.color.orange_4))
                        bindingImpl.catMidUpperTxtV2.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat2lv1view10.setBackgroundColor(mainActivity.resources.getColor(R.color.orange_5))
                        bindingImpl.catUpperTxtV.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                3 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat3lv1view11.setBackgroundColor(mainActivity.resources.getColor(R.color.blue_1))
                        bindingImpl.catLowerTxtV3.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat3lv1view12.setBackgroundColor(mainActivity.resources.getColor(R.color.blue_2))
                        bindingImpl.catlowerMidtxtV3.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat3lv1view13.setBackgroundColor(mainActivity.resources.getColor(R.color.blue_3))
                        bindingImpl.catMidTxtV3.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat3lv1view14.setBackgroundColor(mainActivity.resources.getColor(R.color.blue_4))
                        bindingImpl.catMidUpperTxtV3.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat3lv1view15.setBackgroundColor(mainActivity.resources.getColor(R.color.blue_5))
                        bindingImpl.catUpperTxtV3.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                4 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat4lv1view16.setBackgroundColor(mainActivity.resources.getColor(R.color.brown_5))
                        bindingImpl.catLowerTxtV4.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat4lv1view17.setBackgroundColor(mainActivity.resources.getColor(R.color.brown_4))
                        bindingImpl.catlowerMidtxtV4.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat4lv1view18.setBackgroundColor(mainActivity.resources.getColor(R.color.brown_3))
                        bindingImpl.catMidTxtV4.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat4Lv1view19.setBackgroundColor(mainActivity.resources.getColor(R.color.brown_2))
                        bindingImpl.catMidUpperTxtV4.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat4lv1view20.setBackgroundColor(mainActivity.resources.getColor(R.color.brown_1))
                        bindingImpl.catUpperTxtV4.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                5 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat5lv1view21.setBackgroundColor(mainActivity.resources.getColor(R.color.gray_5))
                        bindingImpl.catLowerTxtV5.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat5lv1view22.setBackgroundColor(mainActivity.resources.getColor(R.color.gray_4))
                        bindingImpl.catlowerMidtxtV5.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat5lv1view23.setBackgroundColor(mainActivity.resources.getColor(R.color.gray_3))
                        bindingImpl.catMidTxtV5.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat5Lv1view24.setBackgroundColor(mainActivity.resources.getColor(R.color.gray_2))
                        bindingImpl.catMidUpperTxtV5.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat5lv1view25.setBackgroundColor(mainActivity.resources.getColor(R.color.gray_1))
                        bindingImpl.catUpperTxtV5.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                6 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat6v1view1.setBackgroundColor(mainActivity.resources.getColor(R.color.red_5))
                        bindingImpl.catLowerTxtV6.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat6lv1view2 .setBackgroundColor(mainActivity.resources.getColor(R.color.red_4))
                        bindingImpl.catlowerMidtxtV6.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat6lv1view3.setBackgroundColor(mainActivity.resources.getColor(R.color.red_3))
                        bindingImpl.catMidTxtV6.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat6Lv1view4.setBackgroundColor(mainActivity.resources.getColor(R.color.red_2))
                        bindingImpl.catMidUpperTxtV6.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat6lv1view5.setBackgroundColor(mainActivity.resources.getColor(R.color.red_1))
                        bindingImpl.catUpperTxtV6.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                7 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat7v1view1.setBackgroundColor(mainActivity.resources.getColor(R.color.purple_5))
                        bindingImpl.catLowerTxtV7.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat7lv1view2.setBackgroundColor(mainActivity.resources.getColor(R.color.purple_4))
                        bindingImpl.catlowerMidtxtV7.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat7lv1view3.setBackgroundColor(mainActivity.resources.getColor(R.color.purple_3))
                        bindingImpl.catlowerMidtxtV7.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat7Lv1view4.setBackgroundColor(mainActivity.resources.getColor(R.color.purple_2))
                        bindingImpl.catlowerMidtxtV7.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat7lv1view5.setBackgroundColor(mainActivity.resources.getColor(R.color.purple_1))
                        bindingImpl.catlowerMidtxtV7.visibility = View.VISIBLE
                        delay(200)
                    }
                }

                8 ->
                {
                    if (score >= 0)
                    {
                        bindingImpl.cat8v1view1.setBackgroundColor(mainActivity.resources.getColor(R.color.teal_1))
                        bindingImpl.catLowerTxtV8.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 20)
                    {
                        bindingImpl.cat8lv1view2.setBackgroundColor(mainActivity.resources.getColor(R.color.teal_2))
                        bindingImpl.catlowerMidtxtV8.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 40)
                    {
                        bindingImpl.cat8lv1view3.setBackgroundColor(mainActivity.resources.getColor(R.color.teal_3))
                        bindingImpl.catlowerMidtxtV8.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 60)
                    {
                        bindingImpl.cat8Lv1view4.setBackgroundColor(mainActivity.resources.getColor(R.color.teal_4))
                        bindingImpl.catlowerMidtxtV8.visibility = View.VISIBLE
                        delay(200)
                    }
                    if (score > 80)
                    {
                        bindingImpl.cat8lv1view5.setBackgroundColor(mainActivity.resources.getColor(R.color.teal_5))
                        bindingImpl.catlowerMidtxtV8.visibility = View.VISIBLE
                        delay(200)
                    }
                }

            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        @JvmStatic
        fun newInstance(results : IntArray) =
            ResultExtraFragment().apply {
                arguments = Bundle().apply {
                    putIntArray(PARAM1 , results)
                }
            }

    }
}
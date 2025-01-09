package com.example.cvdriskestimator.MedicalTestAlgorithms

import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R

class BAITestEstimator(var activity: MainActivity) {

    private var testOutcome : String = ""
    private var mainActivity = activity


    fun calculateBAIndex(q1 : Int , q2 : Int, q3 : Int, q4 : Int , q5 : Int, q6 : Int, q7 : Int , q8 : Int , q9 : Int, q10 : Int,
                          q11 : Int, q12 : Int, q13 : Int, q14 : Int, q15 : Int, q16 : Int, q17 : Int, q18 : Int, q19 : Int, q20 : Int, q21 : Int) : Int
    {
        var testSum : Int = 0
        testSum  = q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9 + q10 + q11 + q12 + q13 + q14 + q15 + q16 + q17 + q18 + q19 + q20 + q21

        return testSum
    }

}
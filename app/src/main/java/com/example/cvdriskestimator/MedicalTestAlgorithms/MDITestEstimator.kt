package com.example.cvdriskestimator.MedicalTestAlgorithms

class MDITestEstimator {

    fun calculateMDI(mdiq1 : Int, mdiq2 : Int, mdiq3 : Int,  mdiq4 : Int, mdiq5 : Int, mdiq6 : Int , mdiq7 : Int
                     , mdiq8 : Int , mdiq9 : Int , mdiq10 : Int , mdiq11 : Int , mdiq12 : Int , mdiq13 : Int) : String
    {
        var resultSum : Int = 0
        var diagnosis : String = ""
        resultSum = mdiq1 + mdiq2 + mdiq3 + mdiq4 + mdiq5 + mdiq6 + mdiq7 + maxAB(mdiq8 , mdiq9) + maxAB(mdiq10 , mdiq11) + maxAB(mdiq12 , mdiq13)

        if (resultSum < 20)
        {
            diagnosis = "No Depression" + resultSum
        }
        if ((resultSum >= 20 ) && (resultSum <= 24))
        {
            diagnosis = "Mild depression" + resultSum
        }
        if ((resultSum >= 25 ) && (resultSum <= 29))
        {
            diagnosis = "Moderate derpession" + resultSum
        }
        if (resultSum > 30)
        {
            diagnosis = "Severe depression" + resultSum
        }

        return diagnosis
    }

    fun maxAB(a : Int, b : Int) : Int
    {
        if (a > b)
            return a
        else
            return b
    }
}
package com.example.cvdriskestimator.MedicalTestAlgorithms

class MDITestEstimator {

    fun calculateMDI(mdiq1 : Int, mdiq2 : Int, mdiq3 : Int,  mdiq4 : Int, mdiq5 : Int, mdiq6 : Int , mdiq7 : Int
                     , mdiq8 : Int , mdiq9 : Int , mdiq10 : Int , mdiq11 : Int , mdiq12 : Int , mdiq13 : Int) : Int
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

        return resultSum
    }

    fun calculateBDI(allPatientSelections : ArrayList<Int?>) : Int
    {
        var resultSum : Int  =0
        for (i in 0..allPatientSelections.size -1)
        {
            resultSum += allPatientSelections.get(i)!!
        }
        return resultSum
    }

    fun calculateGAS(allPatientSelections : ArrayList<Int?>) : ArrayList<Int>
    {
        var resultSum = ArrayList<Int>(3)

        var answer1 = allPatientSelections.get(0)!! + allPatientSelections.get(1)!! + allPatientSelections.get(2)!! + allPatientSelections.get(7)!! + allPatientSelections.get(8)!! + allPatientSelections.get(17)!! + allPatientSelections.get(20)!! + allPatientSelections.get(21)!! + allPatientSelections.get(22)!!

        var answer2 = allPatientSelections.get(3)!! + allPatientSelections.get(4)!! + allPatientSelections.get(11)!! + allPatientSelections.get(15)!! + allPatientSelections.get(17)!! + allPatientSelections.get(18)!! + allPatientSelections.get(23)!! + allPatientSelections.get(24)!!

        var answer3 = allPatientSelections.get(5)!! + allPatientSelections.get(6)!! + allPatientSelections.get(9)!! + allPatientSelections.get(10)!! + allPatientSelections.get(12)!! + allPatientSelections.get(13)!! + allPatientSelections.get(14)!! + allPatientSelections.get(19)!!

        resultSum.add(answer1)
        resultSum.add(answer2)
        resultSum.add(answer3)

        return resultSum
    }

    fun calculateSIDASScore(allPatientSelections: ArrayList<Int?>) : Int
    {
        var result : Int = 0
        result = allPatientSelections.get(0)!! + (10 - allPatientSelections.get(1)!!) + allPatientSelections.get(2)!! + allPatientSelections.get(3)!! + allPatientSelections.get(4)!!
        return result
    }
    fun maxAB(a : Int, b : Int) : Int
    {
        if (a > b)
            return a
        else
            return b
    }
}
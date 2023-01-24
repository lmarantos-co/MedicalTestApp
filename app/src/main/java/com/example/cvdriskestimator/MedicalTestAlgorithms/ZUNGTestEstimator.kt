package com.example.cvdriskestimator.MedicalTestAlgorithms

class ZUNGTestEstimator(allPatientSelections : ArrayList<Int?>) {

    private  var patientSelections = IntArray(20)

    init {
        for (i in 0..allPatientSelections.size -1)
        {
            patientSelections.set(i , allPatientSelections.get(i)!!)
        }
    }

    fun zungTestEstimator() : Int
    {
        var testScore : Int = 0
        for (answer in patientSelections)
        {
            testScore += answer
        }
        return testScore
    }
}
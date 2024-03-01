package com.example.cvdriskestimator.MedicalTestAlgorithms

class OPQOLTestEstimator(allPatientSelections : ArrayList<Int>) {

    private  var patientSelections = IntArray(35)

    init {
        for (i in 0..allPatientSelections.size -1)
        {
            patientSelections.set(i , allPatientSelections.get(i)!!)
        }
    }

    fun opqolTestEstimator() : Int
    {
        var testScore : Int = 0
        for (answer in patientSelections)
        {
            testScore += answer
        }
        return testScore
    }
}
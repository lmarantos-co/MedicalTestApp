package com.example.cvdriskestimator.MedicalTestAlgorithms

class HammitlonTestEstimator(allPatientSelections : ArrayList<Int?>) {

    private  var patientSelections = IntArray(17)

    init {
        for (i in 0..allPatientSelections.size -1)
        {
            patientSelections.set(i , allPatientSelections.get(i)!!)
        }
    }

    fun hamTestEstimator() : Int
    {
        var testScore : Int = 0
        for (answer in patientSelections)
        {
            testScore += answer
        }
        return testScore
    }
}
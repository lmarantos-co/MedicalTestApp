package com.example.cvdriskestimator.MedicalTestAlgorithms

class BPITestEstimator(allPatientValues : ArrayList<Int?>) {

    private var patientValues = ArrayList<Int?>()

    init{
        patientValues = allPatientValues
    }

    fun calculatePainScores() : ArrayList<Float>
    {
        var PSS = ((patientValues[0]!! + patientValues[1]!! + patientValues[2]!! + patientValues[3]!!) / 4).toFloat()
        var PIS = ((patientValues[5]!! + patientValues[6]!! + patientValues[7]!! + patientValues[8]!! + patientValues[9]!! + patientValues[10]!! + patientValues[11]!!) / 5).toFloat()

        var resultValues = arrayListOf<Float>(PSS , PIS)
        return resultValues
    }

}
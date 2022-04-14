package com.example.cvdriskestimator.MedicalTestAlgorithms

class BPITestEstimator(allPatientValues : ArrayList<Int?>) {

    private var patientValues = ArrayList<Int?>()

    init{
        patientValues = allPatientValues
    }

    fun calculatePainScores() : ArrayList<Float>
    {
        var PSS : Float = ((patientValues[0]!!.toFloat() + patientValues[1]!!.toFloat() + patientValues[2]!!.toFloat() + patientValues[3]!!.toFloat()) / 4f)
//        var PIS = ((patientValues[5]!! + patientValues[6]!! + patientValues[7]!! + patientValues[8]!! + patientValues[9]!! + patientValues[10]!! + patientValues[11]!!) / 7).toFloat()
        var PIS = 8

        var resultValues = arrayListOf<Float>(PSS , PIS.toFloat())
        return resultValues
    }

}
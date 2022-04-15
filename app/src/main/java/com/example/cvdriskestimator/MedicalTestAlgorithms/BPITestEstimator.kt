package com.example.cvdriskestimator.MedicalTestAlgorithms

class BPITestEstimator(allPatientValues : ArrayList<Int?>) {

    private var patientValues = ArrayList<Int?>()

    init{
        patientValues = allPatientValues
    }

    fun calculatePainScores() : ArrayList<Float>
    {
        var PSS : Float = ((patientValues[0]!!.toFloat() + patientValues[1]!!.toFloat() + patientValues[2]!!.toFloat() + patientValues[3]!!.toFloat()) / 4f)
        var PIS : Float = ((patientValues[6]!!.toFloat() + patientValues[7]!!.toFloat() + patientValues[8]!!.toFloat() + patientValues[9]!!.toFloat() + patientValues[10]!!.toFloat() + patientValues[11]!!.toFloat() + patientValues[12]!!.toFloat()) / 7).toFloat()

        var resultValues = arrayListOf<Float>(PSS , PIS.toFloat())
        return resultValues
    }

}
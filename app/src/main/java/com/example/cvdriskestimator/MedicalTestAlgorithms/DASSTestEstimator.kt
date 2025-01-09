package com.example.cvdriskestimator.MedicalTestAlgorithms

import java.util.ArrayList

class DASSTestEstimator(allPatientSelections: ArrayList<Int?>) {

    private var answers = IntArray(21)

    init {
        for (i in 0..allPatientSelections.size - 1)
        {
            answers[i] = allPatientSelections.get(i)!!
        }
    }

    fun DASSScorEstimator() : Triple<Int , Int , Int>
    {
        var anxietyScore : Int = 0
        var stressScore : Int = 0
        var depressionScore : Int = 0

        anxietyScore = answers[1] + answers[3] + answers[6] + answers[8] + answers[14]
        +answers[18] + answers[19]

        stressScore = answers[0] + answers[5] + answers[7] + answers[10] + answers[11] + answers[13] + answers[17]

        depressionScore = answers[2] + answers[4] + answers[9] + answers[12] + answers[15] + answers[16] + answers[20]

        var tripleAnswer = Triple<Int , Int , Int>(0  ,0 , 0)
        tripleAnswer = tripleAnswer.copy(first =anxietyScore , second = stressScore , third = depressionScore )

        return  tripleAnswer
    }

}
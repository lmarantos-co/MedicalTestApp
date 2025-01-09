package com.example.cvdriskestimator.MedicalTestAlgorithms

class StaiTestEstimator(answers : java.util.ArrayList<Int?>) {

    private var allPatientSelections : java.util.ArrayList<Int?>

    init {
        allPatientSelections = answers
    }

    fun calculateStateAndTraitScores() : Pair<Int , Int>
    {
        var answerPair = Pair(0 , 0)

        var stateScore : Int = 0
        var traitScore : Int = 0

        for (i in 0..19)
        {
            stateScore += allPatientSelections.get(i)!!
        }

        for (i in 20..39)
        {
            traitScore += allPatientSelections.get(i)!!
        }

        answerPair = answerPair.copy(first = stateScore , second = traitScore)

        return answerPair
    }

}
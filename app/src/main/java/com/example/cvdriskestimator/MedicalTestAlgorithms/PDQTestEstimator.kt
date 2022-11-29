package com.example.cvdriskestimator.MedicalTestAlgorithms

class PDQTestEstimator(allPatientAnswers: ArrayList<Int?>) {

    private var pdqAllPatientAnswers = IntArray(8)

    init {
        for (i in 0..allPatientAnswers.size)
        {
            pdqAllPatientAnswers[0] = allPatientAnswers.get(i)!!
        }

    }

    fun calculateAllCategoriesPercentages() : IntArray
    {
        var allAnswersPercentage = IntArray(8)
        var firstCategoryPercentage : Int = 0
        var secondCategoryPercentage : Int = 0
        var thirdCategoryPercentage : Int = 0
        var fourthCategoryPercentage : Int = 0
        var fifthCategoryPercentage : Int = 0
        var sixthCategoryPercentage : Int = 0
        var seventhCategoryPercentage : Int = 0
        var eightCategoryPercentage : Int = 0
        for (i in 0..9)
        {
            firstCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        firstCategoryPercentage /= 10

        for (i in 11..16)
        {
            secondCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        secondCategoryPercentage /= 6

        for (i in 17..22)
        {
            thirdCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        thirdCategoryPercentage /= 6

        for (i in 23..26)
        {
            fourthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        fourthCategoryPercentage /= 4

        for (i in 27..29)
        {
            fifthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        fifthCategoryPercentage /= 3

        for (i in 30..33)
        {
            sixthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        sixthCategoryPercentage /= 4

        for (i in 34..36)
        {
            seventhCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        seventhCategoryPercentage /= 3

        for (i in 37..39)
        {
            eightCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        eightCategoryPercentage /= 3

        allAnswersPercentage[0] = (firstCategoryPercentage)
        allAnswersPercentage[1] = (secondCategoryPercentage)
        allAnswersPercentage[2] = (thirdCategoryPercentage)
        allAnswersPercentage[3] = (fourthCategoryPercentage)
        allAnswersPercentage[4] = (fifthCategoryPercentage)
        allAnswersPercentage[5] = (sixthCategoryPercentage)
        allAnswersPercentage[6] = (seventhCategoryPercentage)
        allAnswersPercentage[7] = (eightCategoryPercentage)
        return allAnswersPercentage
    }

}
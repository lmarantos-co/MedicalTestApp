package com.example.cvdriskestimator.MedicalTestAlgorithms

class PDQTestEstimator(allPatientAnswers: ArrayList<Int?>) {

    private var pdqAllPatientAnswers = arrayListOf<Int?>()

    init {
        pdqAllPatientAnswers = allPatientAnswers
    }

    fun calculateAllCategoriesPercentages() : ArrayList<Int>
    {
        var allAnswersPercentage = arrayListOf<Int>()
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
        secondCategoryPercentage /= 10

        for (i in 17..22)
        {
            thirdCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        thirdCategoryPercentage /= 10

        for (i in 23..26)
        {
            fourthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        fourthCategoryPercentage /= 10

        for (i in 27..29)
        {
            fifthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        fifthCategoryPercentage /= 10

        for (i in 30..33)
        {
            sixthCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        sixthCategoryPercentage /= 10

        for (i in 34..36)
        {
            seventhCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        seventhCategoryPercentage /= 10

        for (i in 37..39)
        {
            eightCategoryPercentage += (pdqAllPatientAnswers.get(i)!! + 1 * 20)
        }
        eightCategoryPercentage /= 10

        allAnswersPercentage.add(firstCategoryPercentage)
        allAnswersPercentage.add(secondCategoryPercentage)
        allAnswersPercentage.add(thirdCategoryPercentage)
        allAnswersPercentage.add(fourthCategoryPercentage)
        allAnswersPercentage.add(fifthCategoryPercentage)
        allAnswersPercentage.add(sixthCategoryPercentage)
        allAnswersPercentage.add(seventhCategoryPercentage)
        allAnswersPercentage.add(eightCategoryPercentage)
        return allAnswersPercentage
    }

}
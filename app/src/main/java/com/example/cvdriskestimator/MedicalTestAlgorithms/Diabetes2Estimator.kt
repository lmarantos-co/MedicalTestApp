package com.example.cvdriskestimator.MedicalTestAlgorithms

import java.lang.Math.exp

class Diabetes2Estimator {


    fun calculateDiabetes2Risk(sex : String , pam : String, Steroids : String , age : String , BMI : String , familyStatus : String , SmokingStatus : String) : Double
    {

        var calculation = 0.0
        val beta : Double = -6.322
        var sexPoints : Double
        if (sex.equals("MALE"))
            sexPoints = 0.0
        else
            sexPoints = 1.0
        sexPoints *= -0.879

        var pamPoints : Double
        if (pam.equals("YES"))
            pamPoints = 1.0
        else
            pamPoints = 0.0
        pamPoints *= 1.222

        var SteroidsPoints : Double
        if (Steroids.equals("YES")) {
            SteroidsPoints = 1.0
        }
        else {
            SteroidsPoints = 0.0
        }
        SteroidsPoints *= 2.191

        val agePoints: Double = age.toDouble() * 0.063

        var BMIPoints : Double = 0.0
        if (BMI.toDouble() < 25.0)
            BMIPoints = 0.0 * 0.0
        if (BMI.toDouble() >= 25.0 && BMI.toDouble() < 27.5)
            BMIPoints = 0.699 * 1.0
        if (BMI.toDouble() >= 27.5 && BMI.toDouble() < 30.0)
            BMIPoints = 1.97 * 1.0
        if (BMI.toDouble() > 30.0)
            BMIPoints = 2.518 * 1.0

        var siblingsPoints = 0.0
        if (familyStatus == "No Diabetes")
            siblingsPoints = 0.0
        if (familyStatus == "Parent Or Sibling With Diabetes")
            siblingsPoints = 1.0 * 0.728
        if (familyStatus.equals("Parent And Sibling With Diabetes"))
            siblingsPoints = 1.0 * 0.753

        var smokingpoints : Double = 0.0
        if (SmokingStatus == "Never")
            smokingpoints = 0.0
        if (SmokingStatus == "Former")
            smokingpoints = 1.0 * -0.218
        if (SmokingStatus == "Current")
            smokingpoints = 1.0 * 0.855

        val sum = beta + sexPoints + pamPoints + agePoints + SteroidsPoints + BMIPoints + siblingsPoints + smokingpoints
        calculation = 1 / (1 + kotlin.math.exp(-sum))

        return calculation

    }

}
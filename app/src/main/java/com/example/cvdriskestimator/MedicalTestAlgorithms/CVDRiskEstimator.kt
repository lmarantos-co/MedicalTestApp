package com.example.cvdriskestimator.MedicalTestAlgorithms

class CVDRiskEstimator {

    fun calculateCVDRisk(sex : String, age : Int, SBP : Int , TotalChol : Int, HDLChol : Int , Smoker : String, Treatment : String) : Int {
        //Calculate the 10-year Framingham Risk Score
        var TotalPoints : Int = 0
        var cvdRiskPerc : Int = 0
        if (sex == "FEMALE")
        {
            //points related with age
            if ((age>=20) && (age<=34))
            {
                TotalPoints -= 7;
            }
            if ((age>=35) && (age<=39))
            {
                TotalPoints -= 3;
            }
            if ((age>=40) && (age<=44))
            {
                TotalPoints += 0;
            }
            if ((age>=45) && (age<=49))
            {
                TotalPoints += 3;
            }
            if ((age>=50) && (age<=54))
            {
                TotalPoints += 6;
            }
            if ((age>=55) && (age<=59))
            {
                TotalPoints += 8;
            }
            if ((age>=60) && (age<=64))
            {
                TotalPoints += 10;
            }
            if ((age>=65) && (age<=69))
            {
                TotalPoints += 12;
            }
            if ((age>=70) && (age<=74))
            {
                TotalPoints += 14;
            }
            if ((age>=75) && (age<=79))
            {
                TotalPoints += 16;
            }
            //points reltead with TotalChol and Smoking
            if ((age>=20)&&(age<=39))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 4;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 8;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 11;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 13;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 9;
                }
            }
            if ((age>=40)&&(age<=49))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 3;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 6;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 8;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 10;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 7;
                }
            }
            if ((age>=50)&&(age<=59))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 2;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 4;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 5;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 7;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 4;
                }
            }
            if ((age>=60)&&(age<=69))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 1;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 2;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 3;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 4;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 2;
                }
            }
            if ((age>=70)&&(age<=79))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 1;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 1;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 2;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 2;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 1;
                }
            }
            //points related with HDL Cholesterol
            if (HDLChol>=60)
            {
                TotalPoints -= 1;
            }
            if ((HDLChol >= 50) && (HDLChol <=59))
            {
                TotalPoints += 0;
            }
            if ((HDLChol >= 40) && (HDLChol <=49))
            {
                TotalPoints += 1;
            }
            if (HDLChol <= 40)
            {
                TotalPoints += 2;
            }
            //points related with Systolic Blood Pressure
            if (Treatment=="No")
            {
                if (SBP < 120)
                {
                    TotalPoints += 0;
                }
                if ((SBP >= 120) && (SBP <= 129))
                {
                    TotalPoints += 1;
                }
                if ((SBP >= 130) && (SBP <= 139))
                {
                    TotalPoints += 2;
                }
                if ((SBP >= 140) && (SBP <= 159))
                {
                    TotalPoints += 3;
                }
                if (SBP >= 160)
                {
                    TotalPoints += 4;
                }
            }
            if (Treatment=="Yes")
            {
                if (SBP < 120)
                {
                    TotalPoints += 0;
                }
                if ((SBP >= 120) && (SBP <= 129))
                {
                    TotalPoints += 3;
                }
                if ((SBP >= 130) && (SBP <= 139))
                {
                    TotalPoints += 4;
                }
                if ((SBP >= 140) && (SBP <= 159))
                {
                    TotalPoints += 5;
                }
                if (SBP >= 160)
                {
                    TotalPoints += 6;
                }
            }
        }


        if (sex == "MALE")
        {
            //points related with age
            if ((age>=20) && (age<=34))
            {
                TotalPoints -= 9;
            }
            if ((age>=35) && (age<=39))
            {
                TotalPoints -= 4;
            }
            if ((age>=40) && (age<=44))
            {
                TotalPoints += 0;
            }
            if ((age>=45) && (age<=49))
            {
                TotalPoints += 3;
            }
            if ((age>=50) && (age<=54))
            {
                TotalPoints += 6;
            }
            if ((age>=55) && (age<=59))
            {
                TotalPoints += 8;
            }
            if ((age>=60) && (age<=64))
            {
                TotalPoints += 10;
            }
            if ((age>=65) && (age<=69))
            {
                TotalPoints += 11;
            }
            if ((age>=70) && (age<=74))
            {
                TotalPoints += 12;
            }
            if ((age>=75) && (age<=79))
            {
                TotalPoints += 13;

            }
            //points related with Total CHol and Smoking
            if ((age>=20)&&(age<=39))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 4;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 7;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 9;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 11;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 8;
                }
            }
            if ((age>=40)&&(age<=49))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 3;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 5;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 6;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 8;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 5;
                }
            }
            if ((age>=50)&&(age<=59))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 2;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 3;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 4;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 5;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 3;
                }
            }
            if ((age>=60)&&(age<=69))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 1;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 1;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 2;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 3;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 1;
                }
            }
            if ((age>=70)&&(age<=79))
            {
                if (TotalChol < 160)
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 160) && (TotalChol <= 199))
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 200) && (TotalChol <= 239))
                {
                    TotalPoints += 0;
                }
                if ((TotalChol >= 240) && (TotalChol <= 279))
                {
                    TotalPoints += 1;
                }
                if (TotalChol >= 280)
                {
                    TotalPoints += 1;
                }
                if (Smoker=="Current")
                {
                    TotalPoints += 1;
                }
                //points related with HDL Cholestelor
                if (HDLChol>=60)
                {
                    TotalPoints -= 1;
                }
                if ((HDLChol >= 50) && (HDLChol <=59))
                {
                    TotalPoints += 0;
                }
                if ((HDLChol >= 40) && (HDLChol <=49))
                {
                    TotalPoints += 1;
                }
                if (HDLChol <= 40)
                {
                    TotalPoints += 2;
                }
                //points related with Systolic Blood Pressure
                if (Treatment=="No")
                {
                    if (SBP < 120)
                    {
                        TotalPoints += 0;
                    }
                    if ((SBP >= 120) && (SBP <= 129))
                    {
                        TotalPoints += 0;
                    }
                    if ((SBP >= 130) && (SBP <= 139))
                    {
                        TotalPoints += 1;
                    }
                    if ((SBP >= 140) && (SBP <= 159))
                    {
                        TotalPoints += 1;
                    }
                    if (SBP >= 160)
                    {
                        TotalPoints += 2;
                    }
                }
                if (Treatment=="Yes")
                {
                    if (SBP < 120)
                    {
                        TotalPoints += 0;
                    }
                    if ((SBP >= 120) && (SBP <= 129))
                    {
                        TotalPoints += 1;
                    }
                    if ((SBP >= 130) && (SBP <= 139))
                    {
                        TotalPoints += 2;
                    }
                    if ((SBP >= 140) && (SBP <= 159))
                    {
                        TotalPoints += 2;
                    }
                    if (SBP >= 160)
                    {
                        TotalPoints += 3;
                    }
                }
            }
        }

        if(sex == "FEMALE")
        {
            if (TotalPoints<9)
            {
                cvdRiskPerc = 0;
            }
            if ((TotalPoints>=9)&&(TotalPoints<=12))
            {
                cvdRiskPerc = 1;
            }
            if ((TotalPoints>=13)&&(TotalPoints<=14))
            {
                cvdRiskPerc = 2;
            }
            if (TotalPoints==15)
            {
                cvdRiskPerc = 3;
            }
            if (TotalPoints==16)
            {
                cvdRiskPerc = 4;
            }
            if (TotalPoints==17)
            {
                cvdRiskPerc = 5;
            }
            if (TotalPoints==18)
            {
                cvdRiskPerc = 6;
            }
            if (TotalPoints==19)
            {
                cvdRiskPerc = 8;
            }
            if (TotalPoints==20)
            {
                cvdRiskPerc = 11;
            }
            if (TotalPoints==21)
            {
                cvdRiskPerc = 14;
            }
            if (TotalPoints==22)
            {
                cvdRiskPerc = 17;
            }
            if (TotalPoints==23)
            {
                cvdRiskPerc = 22;
            }
            if (TotalPoints==24)
            {
                cvdRiskPerc = 27;
            }
            if (TotalPoints>=25)
            {
                cvdRiskPerc = 30;
            }
        }
        if(sex == "MALE")
        {
            if (TotalPoints==0)
            {
                cvdRiskPerc = 0;
            }
            if ((TotalPoints>=1)&&(TotalPoints<=4))
            {
                cvdRiskPerc = 1;
            }
            if ((TotalPoints>=5)&&(TotalPoints<=6))
            {
                cvdRiskPerc = 2;
            }
            if (TotalPoints==7)
            {
                cvdRiskPerc = 3;
            }
            if (TotalPoints==8)
            {
                cvdRiskPerc = 4;
            }
            if (TotalPoints==9)
            {
                cvdRiskPerc = 5;
            }
            if (TotalPoints==10)
            {
                cvdRiskPerc = 6;
            }
            if (TotalPoints==11)
            {
                cvdRiskPerc = 8;
            }
            if (TotalPoints==12)
            {
                cvdRiskPerc = 10;
            }
            if (TotalPoints==13)
            {
                cvdRiskPerc = 12;
            }
            if (TotalPoints==14)
            {
                cvdRiskPerc = 16;
            }
            if (TotalPoints==15)
            {
                cvdRiskPerc = 20;
            }
            if (TotalPoints==16)
            {
                cvdRiskPerc = 25;
            }
            if (TotalPoints>=17)
            {
                cvdRiskPerc = 30;
            }
        }
        return cvdRiskPerc
    }
}
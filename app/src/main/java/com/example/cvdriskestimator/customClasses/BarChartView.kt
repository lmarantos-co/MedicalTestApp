package com.example.cvdriskestimator.customClasses

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class BarChartView(context: Context, patientSelections : ArrayList<Int>) : View(context)
{
    lateinit var answers : ArrayList<Int>
    init {
        answers = patientSelections
    }
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        var barHeight = height / 15
        var barWidthFull = width / 1.2
        var spacesBetweenBars = height / 18
        var offsetFromTheTop : Int = 50
        var barStart = width / 15
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.isAntiAlias = true
        var question1Score = answers.get(0) + answers.get(1) + answers.get(2) + answers.get(3)
        var question2Score = answers.get(4) + answers.get(5) + answers.get(6) + answers.get(7)
        var question3Score = answers.get(8) + answers.get(9) + answers.get(10) + answers.get(11) + answers.get(12)
        var question4Score = answers.get(13) + answers.get(14) + answers.get(15) + answers.get(16)
        var question5Score = answers.get(17) + answers.get(18) + answers.get(19) + answers.get(20)
        var question6Score = answers.get(21) + answers.get(22) + answers.get(23) + answers.get(24)
        var question7Score = answers.get(25) + answers.get(26) + answers.get(27) + answers.get(28)
        var question8Score = answers.get(29) + answers.get(30) + answers.get(31) + answers.get(32)+ answers.get(33) + answers.get(34)
        val colors = arrayListOf(Color.RED ,  Color.GREEN ,Color.BLUE , Color.CYAN , Color.MAGENTA , Color.YELLOW , Color.DKGRAY , Color.LTGRAY)
        var listOfScores = arrayListOf<Int>(question1Score , question2Score , question3Score , question4Score , question5Score , question6Score , question7Score , question8Score)
        var listOfTitles = arrayListOf<String>("Life Overall" , "Health" , "Social Relationships"  , "Independence" , "Home and Neighborhood" , "Psychological well-being" , "Financial Situation" , "Free-time and Activities")
        var greenColorValue = 0
        var redColorValue = 256
        var allColors = arrayListOf<Int>(Color.argb(255 , 255, 0, 0), Color.argb(255, 255 , 33, 0), Color.argb(255 , 255, 50, 0), Color.argb(255  ,255, 62, 0), Color.argb(255 , 255, 72, 0), Color.argb(255 , 255, 81, 0), Color.argb(255  ,255, 89, 0), Color.argb(255 ,255, 97, 0), Color.argb(255 ,255, 103, 0), Color.argb(255 , 255, 110, 0), Color.argb(255 , 255, 116, 0), Color.argb(255 , 254, 123, 0), Color.argb(255 , 253, 129, 0), Color.argb(255 , 251, 136, 0), Color.argb(255 , 247, 145, 0), Color.argb(255 , 242, 153, 0), Color.argb(255 , 238, 160, 0), Color.argb(255 , 233, 167, 0), Color.argb(255 , 228, 175, 0), Color.argb(255 , 221, 182, 0), Color.argb(255 , 212, 190, 0), Color.argb(255 , 206, 196, 0), Color.argb(255 , 200, 203, 0), Color.argb(255 , 191, 210, 0), Color.argb(255 , 180, 217, 0), Color.argb(255 , 167, 224, 0), Color.argb(255 ,150, 231, 0), Color.argb(255 , 127, 239, 0), Color.argb(255 ,93, 247, 0), Color.argb(255 , 0, 255, 0))
//        for (i in 0..29)
//        {
//            var color = Color.argb(255 , redColorValue - ( i * 8) , greenColorValue + ( i * 8) , 0)
//            allColors.add(color)
//        }
        for (i in 0..7)
        {
            var widthBarRatio : Float = 0f
            if ((i != 2) && (i != 7))
            {
                widthBarRatio = (listOfScores.get(i).toFloat() / 20 * barWidthFull.toFloat())

            }
            if (i == 2)
            {
                widthBarRatio = (listOfScores.get(i).toFloat() / 25 * barWidthFull.toFloat())
            }
            if (i == 7)
            {
                widthBarRatio = (listOfScores.get(i).toFloat() / 30 * barWidthFull.toFloat())

            }
            var specificColor = allColors.get((widthBarRatio / barWidthFull * 30).toInt())
            paint.color = specificColor
            paint.style = Paint.Style.FILL // Fill mode
            canvas!!.drawRect(barStart.toFloat() , (i * (barHeight + spacesBetweenBars) + offsetFromTheTop).toFloat() , widthBarRatio.toFloat() , (i * (barHeight + spacesBetweenBars)).toFloat() + (barHeight + offsetFromTheTop).toFloat() , paint)
            paint.color = Color.BLACK // Black color for the border
            paint.style = Paint.Style.STROKE // Stroke mode for border
            paint.strokeWidth = 5f // Adjust the border width as needed
            canvas!!.drawRect(barStart.toFloat() , (i * (barHeight + spacesBetweenBars) + offsetFromTheTop).toFloat()  , barWidthFull.toFloat() ,(i * (barHeight + spacesBetweenBars)).toFloat() + (barHeight.toFloat() + offsetFromTheTop), paint)
            paint.color = Color.WHITE
            paint.textSize = 30f
            paint.isAntiAlias = true
            // Set the letter spacing (in pixels)
            val letterSpacingPx = 0.6f // Adjust this value as needed
            paint.letterSpacing = letterSpacingPx
            if (i == 0)
            {
                canvas!!.drawText(listOfTitles.get(i) ,barStart.toFloat() + 10, (i * (barHeight + spacesBetweenBars)).toFloat() + offsetFromTheTop /2 , paint)
            }
            else
            {
                canvas!!.drawText(listOfTitles.get(i) ,barStart.toFloat() + 10, (i * (barHeight + spacesBetweenBars) + offsetFromTheTop).toFloat()  , paint)
            }
        }

    }
}
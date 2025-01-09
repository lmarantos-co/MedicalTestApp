package com.example.cvdriskestimator.customClasses

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class verticalBarChartView(context: Context, patientSelections : ArrayList<Int>) : View(context)
{
    lateinit var answers : ArrayList<Int>
    init {
        answers = patientSelections
    }
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        var barHeight = height / 7
        var barWidthFull = width / 1.2
        var spacesBetweenBars = height / 9
        var offsetFromTheTop : Int = 50
        var barStart = width / 10
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.isAntiAlias = true
        val colors = arrayListOf(Color.RED ,  Color.GREEN ,Color.BLUE)
        var totalScore= answers.get(0) + answers.get(1) + answers.get(2)
        var answer1 = answers.get(0)
        var answer2 = answers.get(1)
        var answer3 = answers.get(2)
        var listOfTitles = arrayListOf<String>("Somatic" , "Cognitive" , "Affective"  , "Independence")
//        for (i in 0..29)
//        {
//            var color = Color.argb(255 , redColorValue - ( i * 8) , greenColorValue + ( i * 8) , 0)
//            allColors.add(color)
//        }
        for (i in 0..2)
        {
            var widthBarRatio : Float = 0f

            if (i == 0)
                widthBarRatio = ((answer1.toFloat() / 27).toFloat() * barWidthFull.toFloat())

            if (i == 1)
                widthBarRatio = ((answer2.toFloat() / 24).toFloat() * barWidthFull.toFloat())

            if (i == 2)
                widthBarRatio = ((answer3.toFloat() / 24).toFloat() * barWidthFull.toFloat())

            paint.color = colors.get(i)
            paint.style = Paint.Style.FILL // Fill mode
            canvas!!.drawRect(barStart.toFloat() , (i * (barHeight + spacesBetweenBars) + offsetFromTheTop).toFloat() , widthBarRatio.toFloat(), (i * (barHeight + spacesBetweenBars)).toFloat() + (barHeight + offsetFromTheTop).toFloat() , paint)
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
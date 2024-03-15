package com.example.cvdriskestimator.customClasses


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class sidasBarChartView(context: Context, testScore :Int) : View(context)
{
    var answer : Int
    init {
        answer = testScore
    }
    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        var barHeight = height / 3
        var barWidthFull = width / 1.2
        var spacesBetweenBars = height / 9
        var offsetFromTheTop : Int = height / 2
        var barStart = width / 10
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.isAntiAlias = true
        val colors = arrayListOf(Color.GREEN ,  Color.YELLOW ,Color.RED)

        var listOfTitles = arrayListOf<String>("Non Suicidal" , "Cognitive" , "Affective"  , "Independence")
//        for (i in 0..29)
//        {
//            var color = Color.argb(255 , redColorValue - ( i * 8) , greenColorValue + ( i * 8) , 0)
//            allColors.add(color)
//        }

        var widthBarRatio : Float = 0f


        if (answer == 0)
        {
            widthBarRatio = 0f
        }
        else
        {
            widthBarRatio = (answer.toFloat() / 50).toFloat()

        }

        if (answer == 0)
        {
            paint.color = Color.TRANSPARENT
        }
        if ((answer >= 1) && (answer <= 9))
        {
            paint.color = colors.get(0)
        }
        if ((answer >= 10) && (answer <= 20))
        {
            paint.color = colors.get(1)
        }
        if ((answer >= 21) && (answer <= 50))
        {
            paint.color = colors.get(2)
        }
        paint.style = Paint.Style.FILL // Fill mode
        canvas!!.drawRect(barStart.toFloat() , offsetFromTheTop.toFloat() , widthBarRatio.toFloat() * barWidthFull.toFloat(), (offsetFromTheTop + barHeight).toFloat() ,  paint)
        paint.color = Color.BLACK // Black color for the border
        paint.style = Paint.Style.STROKE // Stroke mode for border
        paint.strokeWidth = 5f // Adjust the border width as needed
        canvas!!.drawRect(barStart.toFloat() , offsetFromTheTop.toFloat() , barWidthFull.toFloat(), (offsetFromTheTop + barHeight).toFloat() ,  paint)


    }
}
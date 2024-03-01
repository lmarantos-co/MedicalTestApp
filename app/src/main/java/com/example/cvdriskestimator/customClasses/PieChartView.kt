package com.example.cvdriskestimator.customClasses

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View

class PieChartView(context: Context , angle : Float) : View(context) {

    private val paint = Paint()
    private var resultAngle : Float = 0f

    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        resultAngle = angle
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(width, height) / 3f

        val startAngle = 0f
        val sweepAngle = 360f / 3 // Divide circle into 3 equal parts

        val labels = arrayOf("Low Quality of Life", "Moderate Quality of Life", "High Quality Of Life")
        val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE) // Colors for each segment
        val transColor = Color.TRANSPARENT

        val path = Path()
        path.moveTo(centerX, centerY) // Move to center

        // Draw each segment with different color
        for (i in 0 until 3) {
            paint.style = Paint.Style.FILL // Fill mode
            paint.color = colors[i] // Set color for the segment
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                startAngle + i * sweepAngle, sweepAngle, true , paint)
//            path.arcTo(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
//                startAngle + i * sweepAngle, sweepAngle, true)
//            path.close()
//            canvas.drawPath(path, paint)
        }

        // Draw the transparent circle with black border
        paint.color = Color.TRANSPARENT // Transparent fill
        paint.style = Paint.Style.FILL // Fill mode
        canvas.drawCircle(centerX, centerY, radius, paint)

        paint.color = Color.BLACK // Black color for the border
        paint.style = Paint.Style.STROKE // Stroke mode for border
        paint.strokeWidth = 5f // Adjust the border width as needed
        canvas.drawCircle(centerX, centerY, radius, paint)
        // Draw lines to divide circle into three equal parts


        //draw a segment of transparent color between the result angle and he end of the circle
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            resultAngle,
            (360 - resultAngle),
            true,
            paint
        )

        canvas.drawPath(path, paint)

        // Draw lines from the center to divide the circle into 3 equal parts
        paint.color = Color.BLACK
        for (i in 0 until 3) {
            val angle = startAngle + i * sweepAngle
            val endX = centerX + radius * Math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val endY = centerY + radius * Math.sin(Math.toRadians(angle.toDouble())).toFloat()
            canvas.drawLine(centerX, centerY, endX, endY, paint)
        }

        //draw the text on each segment
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL // Set to fill mode for drawing segments
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER // Center the text
        paint.color = Color.BLACK
        for (i in 0 until 3)
        {
            // Draw the text at the center of the segment
            val x = centerX + radius * Math.cos(Math.toRadians((startAngle + i * sweepAngle + sweepAngle / 2).toDouble()))
            val y = centerY + radius * Math.sin(Math.toRadians((startAngle + i * sweepAngle + sweepAngle / 2).toDouble()))
            canvas.drawText(labels[i], x.toFloat(), y.toFloat(), paint)
        }


    }
}
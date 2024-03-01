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

        val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE) // Colors for each segment
        val transColor = Color.TRANSPARENT
        val path = Path()
        path.moveTo(centerX, centerY) // Move to center

        // Draw each segment with different color
        for (i in 0 until 3) {
            paint.color = colors[i] // Set color for the segment
            path.arcTo(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                startAngle + i * sweepAngle, sweepAngle, true)
        }

        //draw a segment of transparent color between the result angle and he end of the circle
        paint.color = Color.TRANSPARENT
        path.arcTo(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
            resultAngle, 360f, true)

        canvas.drawPath(path, paint)

        paint.color = Color.BLACK
        //draw the circle of the pie chart
        canvas.drawCircle(centerX, centerY, radius, paint)
        // Draw lines to divide circle into three equal parts
        for (i in 0 until 3) {
            val angle = Math.toRadians((i * 120).toDouble()) // Divide 360 degrees into 3 equal parts
            val startX = centerX + radius * Math.cos(angle).toFloat()
            val startY = centerY + radius * Math.sin(angle).toFloat()
            val endX = centerX + radius * Math.cos(angle + Math.PI).toFloat()
            val endY = centerY + radius * Math.sin(angle + Math.PI).toFloat()
            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }
}
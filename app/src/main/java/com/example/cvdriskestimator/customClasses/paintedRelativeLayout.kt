package com.example.cvdriskestimator.customClasses

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout

class CustomRelativeLayout @JvmOverloads constructor(
    context: Context,
    rectTestRatio : Float,
    paintColor : Int
) : RelativeLayout(context) {

    private val paint = Paint()
    private var widthRatio : Float = 0f
    private var viewWidth : Float = 0f
    private var pColor : Int = Color.WHITE

    init {
        // Initialize paint attributes
        paint.style = Paint.Style.FILL // Fills the rectangle without border
        widthRatio = rectTestRatio
        pColor = paintColor
        paint.color = pColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Get the dimensions of the RelativeLayout
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()
        val paintedWidth = width * widthRatio
        // Draw the rectangle
        canvas.drawRect(0f, 0f, paintedWidth, height, paint)

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawRect(paintedWidth, 0f, width, height, paint)

        //draw a black border in the rectangle
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        canvas.drawRect(0f, 0f, width, height, paint)
    }
}
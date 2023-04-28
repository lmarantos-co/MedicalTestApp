package com.example.cvdriskestimator.customClasses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.cvdriskestimator.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

class HintEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val hintPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.SQUARE
    }

    private val underlinePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.SQUARE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Measure the hint text to get its exact width
        val hintWidth = paint.measureText(hint.toString())

        // Add padding to the width to ensure the hint text is fully visible
        val width = (hintWidth + paddingRight + paddingLeft).toInt()

        // Use the exact width for the EditText's layout width
        setMeasuredDimension(width, measuredHeight)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw underline
        underlinePaint.color = currentHintTextColor
        val underlineStart = paddingStart
        val underlineEnd = width - paddingEnd
        val underlineTop = height - paddingBottom - underlineThickness
        val underlineBottom = height - paddingBottom
        canvas.drawRect(underlineStart.toFloat(), underlineTop.toFloat(), underlineEnd.toFloat(), underlineBottom.toFloat(), underlinePaint)

        // Draw hint text
        hintPaint.color = currentHintTextColor
        hintPaint.textSize = textSize
        hintPaint.textAlign = Paint.Align.LEFT
        val hintLayout = layout
        val hint = hint ?: return
        val hintTop = hintLayout.getLineTop(0).toFloat()
        val hintBottom = hintLayout.getLineBottom(0).toFloat()
        val hintWidth = paint.measureText(hint.toString())
        val hintX = paddingStart.toFloat()
        val hintY = hintTop - hintTextMargin
        canvas.drawText(hint.toString(), hintX + hintWidth, hintY, hintPaint)
    }

    companion object {
        private const val underlineThickness = 4f
        private const val hintTextMargin = 8f
    }
}
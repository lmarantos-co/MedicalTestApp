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

@SuppressLint("ResourceAsColor")
class HintEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val underlinePaint = Paint()
    private var isHintUnderlined = false

    init {
        // Set the underline color and width
        underlinePaint.color = R.color.black
        underlinePaint.strokeWidth = 1f
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Draw the underline below the hint text if it hasn't been drawn already
        if (!isHintUnderlined && hint != null) {
            val hintLayout = layout
            val hintStart = hintLayout?.getLineStart(0) ?: 0
            val hintEnd = hintLayout?.getLineEnd(0) ?: 0
            val hintWidth = paint.measureText(hint.toString(), hintStart, hintEnd)

            canvas?.drawLine(
                paddingStart.toFloat(),
                height - paddingBottom.toFloat(),
                paddingStart.toFloat() + hintWidth,
                height - paddingBottom.toFloat(),
                underlinePaint
            )

            isHintUnderlined = true
        }

        // Draw the underline below the user-entered text
        canvas?.drawLine(
            paddingStart.toFloat(),
            height - paddingBottom.toFloat(),
            width - paddingEnd.toFloat(),
            height - paddingBottom.toFloat(),
            underlinePaint
        )
    }
}
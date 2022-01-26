package com.example.cvdriskestimator.CustomClasses

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import kotlin.properties.Delegates

class customDerpesionProgressView : View {

    private var paint: Paint? = null

    private var l by Delegates.notNull<Float>()
    private var t by Delegates.notNull<Float>()
    private var r by Delegates.notNull<Float>()
    private var b by Delegates.notNull<Float>()
    private var viewWidth by Delegates.notNull<Float>()
    private var viewHeight by Delegates.notNull<Float>()


    constructor(context: Context , l: Float, t: Float, r: Float, b: Float, paint: Paint?) : super(context) {
        this.b = b;
        this.l = l;
        this.r = r;
        this.t = t;
        this.paint = paint;
        viewWidth = r - l
        viewHeight = b - t
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //set the width and height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(viewWidth.toInt() , viewHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(l, t, r, b, paint!!)
    }
}
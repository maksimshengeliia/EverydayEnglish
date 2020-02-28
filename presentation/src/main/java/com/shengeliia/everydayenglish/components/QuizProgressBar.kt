package com.shengeliia.everydayenglish.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.utils.paint


class QuizProgressBar(context: Context?) : View(context) {
    private var data: MutableList<Boolean> = mutableListOf()
    var barsCount: Int = 0
    var barHeight = 0
    private var barWidth = 0
    private var space = 0
    private var spaceBetweenBars = 0
    private val path = Path()
    private val paint = paint {
        flags = Paint.ANTI_ALIAS_FLAG
        color = Color.GRAY
    }


    override fun onDraw(canvas: Canvas) {
        path.reset()
        val y = height / 2f - barHeight / 2f
        for (i in 0 until barsCount) {
            path.addRoundRect(i * space + spaceBetweenBars.toFloat(), y, i * space + spaceBetweenBars.toFloat() + barWidth.toFloat(), y + barHeight, 5f, 5f, Path.Direction.CCW)
            if (i < data.size) {
                paint.color = if (data[i]) Color.GREEN else Color.RED
            } else {
                paint.color = Color.GRAY
            }
            canvas.drawPath(path, paint)
            path.reset()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val w = MeasureSpec.getSize(widthMeasureSpec) + paddingLeft + paddingRight
        val desiredHeight = barHeight + paddingTop + paddingBottom

        val h = resolveSize(desiredHeight, heightMeasureSpec)
        if (barsCount != 0) {
            space = w / barsCount
            barWidth = space - space / 4
            spaceBetweenBars = space / 4 / 2
        }
        setMeasuredDimension(w, h)
    }

    fun setProgress(data: List<Boolean>) {
        this.data = data.toMutableList()
        invalidate()
    }

    fun addProgress(progress: Boolean) {
        animateProgress()
        this.data.add(progress)
        invalidate()
    }

    private fun animateProgress() {

    }
}
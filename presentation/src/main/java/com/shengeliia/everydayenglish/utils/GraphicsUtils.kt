package com.shengeliia.everydayenglish.utils

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import androidx.annotation.Dimension

fun paint(p: Paint.() -> Unit) = Paint().apply { p() }

fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Int {
    val r = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        r.displayMetrics
    ).toInt()
}

fun spToPx(context: Context, @Dimension(unit = Dimension.SP) sp: Int): Float {
    val r = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        r.displayMetrics
    )
}
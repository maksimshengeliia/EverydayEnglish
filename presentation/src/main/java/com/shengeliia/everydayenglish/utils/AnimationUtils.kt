package com.shengeliia.everydayenglish.utils

import android.view.View
import android.view.animation.AccelerateInterpolator

fun View.swipeLeft() {
    animate()
        .translationX(-100f)
        .setDuration(200)
        .alpha(0f)
        .setInterpolator(AccelerateInterpolator())
}

fun View.swipeRight() {
    animate()
        .translationX(100f)
        .setDuration(200)
        .alpha(0f)
        .setInterpolator(AccelerateInterpolator())
}
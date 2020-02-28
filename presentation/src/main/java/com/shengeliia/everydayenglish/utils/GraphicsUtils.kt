package com.shengeliia.everydayenglish.utils

import android.graphics.Paint

fun paint(p: Paint.() -> Unit) = Paint().apply { p() }
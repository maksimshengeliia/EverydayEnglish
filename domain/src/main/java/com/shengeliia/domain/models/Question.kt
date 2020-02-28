package com.shengeliia.domain.models

data class Question(
    val id: Int,
    val questionText: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val rightAnswer: Int,
    val hint: String? = null,
    val testId: Int,
    var isPassed: Boolean? = null
)

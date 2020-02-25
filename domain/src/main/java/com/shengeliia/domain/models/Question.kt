package com.shengeliia.domain.models

data class Question(
    val id: Int,
    val question: String,
    val answer1: Int,
    val answer2: Int,
    val answer3: Int,
    val answerRight: Int,
    val hint: String? = null,
    val testId: Int
)

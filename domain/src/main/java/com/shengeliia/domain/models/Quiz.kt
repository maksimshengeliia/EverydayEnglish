package com.shengeliia.domain.models

import com.shengeliia.domain.QuizState

data class Quiz(
    val id: Int,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val rightAnswer: Int,
    val testId: Int,
    var state: QuizState = QuizState.NEW
)

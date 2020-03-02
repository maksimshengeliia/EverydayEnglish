package com.shengeliia.domain

import com.shengeliia.domain.models.Quiz
import com.shengeliia.domain.models.Test

interface Repository {
    fun getQuizzesByTest(test: Test): List<Quiz>
    fun getTestById(testId: Int): Test

    fun updateQuizzes(quizzes: List<Quiz>)
    fun insertQuizzes(quizzes: List<Quiz>)
    fun getQuizzes(): List<Quiz>

    fun updateTests(tests: List<Test>)
    fun insertTests(tests: List<Test>)
    fun getTests(): List<Test>

    fun makeCallForUpdates(): List<Test>
}
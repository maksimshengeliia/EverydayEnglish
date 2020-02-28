package com.shengeliia.domain

import com.shengeliia.domain.models.Question

interface Repository {
    fun getQuestionsByTestId(testId: Int): Array<Question>
    fun updateQuestion(q: Question)
}
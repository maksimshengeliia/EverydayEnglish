package com.shengeliia.domain

import com.shengeliia.domain.models.Question

interface Repository {
    fun getQuestions(idTest: Int): Array<Question>
}
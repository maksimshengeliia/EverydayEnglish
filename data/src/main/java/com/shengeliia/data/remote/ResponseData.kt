package com.shengeliia.data.remote

import com.shengeliia.domain.models.Quiz
import com.shengeliia.domain.models.Test

data class ResponseData(val tests: List<Test>, val quizzes: List<Quiz>)
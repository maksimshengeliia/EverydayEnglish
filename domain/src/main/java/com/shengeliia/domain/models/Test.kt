package com.shengeliia.domain.models

import com.shengeliia.domain.TestsState

data class Test(
    val id: Int,
    val name: String,
    val count: Int,
    var quizzesSolved: Int = 0,
    var state: TestsState = TestsState.NEW
)
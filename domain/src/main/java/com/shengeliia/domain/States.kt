package com.shengeliia.domain

import com.google.gson.annotations.SerializedName

enum class TestsState(val value: Int) {
    @SerializedName("NEW")
    NEW(3),
    @SerializedName("STARTED")
    STARTED(1),
    @SerializedName("FINISHED")
    FINISHED(2)
}

fun getEnumTestState(state: Int) = when(state) {
    1 -> TestsState.STARTED
    2 -> TestsState.FINISHED
    else -> TestsState.NEW
}

enum class QuizState(val value: Int) {
    @SerializedName("NEW")
    NEW(3),
    @SerializedName("PASSED")
    PASSED(1),
    @SerializedName("FAILED")
    FAILED(2)
}

fun getEnumQuizState(state: Int) = when(state) {
    1 -> QuizState.PASSED
    2 -> QuizState.FAILED
    else -> QuizState.NEW
}
package com.shengeliia.domain

import com.shengeliia.domain.models.Test

enum class State {
    ALL, NEW, STARTED
}

fun validateData(data: List<Test>, state: State): List<Test> = when(state) {
    State.ALL ->  validateAll(data)
    State.NEW ->  validateNew(data)
    State.STARTED ->  validateStarted(data)
}

private fun validateAll(data: List<Test>): List<Test> {
    return data
}

private fun validateNew(data: List<Test>): List<Test> {
    return data
}

private fun validateStarted(data: List<Test>): List<Test> {
    return data
}
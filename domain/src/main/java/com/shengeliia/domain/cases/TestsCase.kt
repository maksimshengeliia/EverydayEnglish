package com.shengeliia.domain.cases

import com.shengeliia.domain.models.Test

class TestsCase {
    enum class State {
        ALL, NEW, STARTED, CLOSED
    }

    fun validateData(data: List<Test>, state: State): List<Test> = when(state) {
        State.ALL -> validateAll(
            data
        )
        State.NEW -> validateNew(
            data
        )
        State.STARTED -> validateStarted(
            data
        )
        State.CLOSED -> validateFinished(
            data
        )
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

    private fun validateFinished(data: List<Test>): List<Test> {
        return data
    }
}
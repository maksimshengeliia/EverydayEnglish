package com.shengeliia.domain.cases

import com.shengeliia.domain.Repository
import com.shengeliia.domain.models.Test

class TestsCase(private val repository: Repository) {

    fun getRemoteTests(): List<Test> {
        return repository.makeCallForUpdates()
    }

    fun getLocalTests(): List<Test> {
        return repository.getTests()
    }
}
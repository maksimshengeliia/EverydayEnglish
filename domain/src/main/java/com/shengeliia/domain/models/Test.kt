package com.shengeliia.domain.models

data class Test(
    val id: Int,
    val name: String,
    val count: Int,
    var solved: Int = 0
)
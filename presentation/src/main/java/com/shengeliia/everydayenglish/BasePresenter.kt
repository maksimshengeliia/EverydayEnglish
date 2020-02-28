package com.shengeliia.everydayenglish

interface BasePresenter<T> {
    fun register(view: T)
    fun unregister()
}
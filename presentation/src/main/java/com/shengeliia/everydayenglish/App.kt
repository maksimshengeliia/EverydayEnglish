package com.shengeliia.everydayenglish

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        APP_INSTANCE = this
    }

    companion object {
        var APP_INSTANCE: Context? = null
        private set
    }
}
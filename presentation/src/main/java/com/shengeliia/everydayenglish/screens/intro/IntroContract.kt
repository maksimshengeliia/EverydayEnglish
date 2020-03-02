package com.shengeliia.everydayenglish.screens.intro

import android.content.Context
import com.shengeliia.everydayenglish.BasePresenter

interface IntroContract {
    interface PresenterMVP : BasePresenter<ViewMVP> {
        fun onUsernameEntered(context: Context, name: String)
        fun onLevelChosen(context: Context, level: String)
        fun onUserEnter(text: String)
    }
    interface ViewMVP {
        fun showNameLayout()
        fun showLevelLayout()
        fun showLaunchActivity()
        fun showNameValidationOk()
        fun showNameValidationError()
    }
}
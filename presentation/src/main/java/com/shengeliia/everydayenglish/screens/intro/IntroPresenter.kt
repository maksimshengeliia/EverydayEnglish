package com.shengeliia.everydayenglish.screens.intro

import android.content.Context
import com.shengeliia.data.local.PreferencesManager

class IntroPresenter : IntroContract.PresenterMVP {
    private var view: IntroContract.ViewMVP? = null

    override fun onUsernameEntered(context: Context, name: String) {
        PreferencesManager.saveUsername(context, name)
        view?.showLaunchActivity()
    }

    override fun onLevelChosen(context: Context, level: String) {
        PreferencesManager.saveUserLanguageLevel(context, level)
        view?.showNameLayout()
    }

    override fun onUserEnter(text: String) {
        if (text.isNotBlank() && text.length < 10) {
            view?.showNameValidationOk()
        } else {
            view?.showNameValidationError()
        }
    }


    override fun register(view: IntroContract.ViewMVP) {
        this.view = view
        view.showLevelLayout()
    }

    override fun unregister() {
        if (view != null) {
            view = null
        }
    }

}
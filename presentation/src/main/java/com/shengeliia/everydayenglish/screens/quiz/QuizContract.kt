package com.shengeliia.everydayenglish.screens.quiz

import com.shengeliia.domain.models.Quiz
import com.shengeliia.everydayenglish.BasePresenter

interface QuizContract {
    interface PresenterMVP: BasePresenter<ViewMVP> {
        fun onUserAnswer(answer: Int)
        fun onRequestQuestion()
        fun onExit()
        fun onRestartTest()
    }

    interface ViewMVP {
        fun showPassedLayout()
        fun showFailedLayout()
        fun showMainLayout()
        fun initProgress(currentProgress: List<Boolean>, size: Int)
        fun updateQuestion(quiz: Quiz)
        fun updateProgress(currentProgress: List<Boolean>)
        fun showLaunchActivity()
    }
}
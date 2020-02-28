package com.shengeliia.everydayenglish.screens.quiz

import com.shengeliia.domain.models.Question
import com.shengeliia.everydayenglish.BasePresenter

interface QuizContract {
    interface PresenterMVP: BasePresenter<ViewMVP> {
        fun onUserAnswer(answer: Int)
        fun onRequestQuestion()
    }

    interface ViewMVP {
        fun showPassedAnimation()
        fun showFailedAnimation()
        fun initLayout(question: Question, currentProgress: List<Boolean>, size: Int)
        fun updateQuestion(question: Question)
        fun updateProgress(isGuessed: Boolean)
    }
}
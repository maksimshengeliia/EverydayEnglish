package com.shengeliia.everydayenglish.screens.quiz

import com.shengeliia.data.DataRepository
import com.shengeliia.domain.cases.QuizCase

class QuizPresenter(idTest: Int) : QuizContract.PresenterMVP {
    private var view: QuizContract.ViewMVP? = null
    private val quizCase = QuizCase(DataRepository(), idTest)

    override fun onUserAnswer(answer: Int) {
        view?.updateProgress(quizCase.validateAnswer(answer))
    }

    override fun onRequestQuestion() {
        val q = quizCase.nextQuestion()
        if (q != null) {
            view?.updateQuestion(q)
        } else {
            setEndTestLayout(quizCase.isTestPassed())
        }
    }

    private fun setEndTestLayout(result: Boolean) =
        if (result) view?.showPassedAnimation() else view?.showFailedAnimation()

    override fun register(view: QuizContract.ViewMVP) {
        this.view = view
        init()
    }

    private fun init() {
        val q = quizCase.currentQuestion()
        val data = quizCase.currentProgress()
        val size = quizCase.questionsCount()
        view?.initLayout(q, data, size)
    }

    override fun unregister() {
        if (view != null) {
            view = null
        }
    }
}
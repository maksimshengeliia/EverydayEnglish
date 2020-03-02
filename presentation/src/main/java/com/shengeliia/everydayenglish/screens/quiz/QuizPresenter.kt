package com.shengeliia.everydayenglish.screens.quiz

import com.shengeliia.data.DataRepository
import com.shengeliia.domain.cases.QuizCase
import com.shengeliia.domain.models.Quiz

class QuizPresenter(testId: Int) : QuizContract.PresenterMVP, QuizCase.QuizListener {
    private var view: QuizContract.ViewMVP? = null
    private val quizCase = QuizCase(DataRepository(), this, testId)

    override fun onUserAnswer(answer: Int) {
        quizCase.validateAnswer(answer)
    }

    override fun onRequestQuestion() {
        quizCase.sendNewQuestion()
    }

    override fun onExit() {
        view?.showLaunchActivity()
    }

    override fun onRestartTest() {
        view?.showMainLayout()
        quizCase.startTestAgain()
    }

    override fun register(view: QuizContract.ViewMVP) {
        this.view = view
        view.showMainLayout()
        quizCase.init()
    }

    override fun unregister() {
        if (view != null) {
            view = null
        }
    }

    override fun onInitQuiz(list: List<Boolean>, questionsCount: Int) {
        view?.initProgress(list, questionsCount)
    }

    override fun onNextQuestion(quiz: Quiz) {
        view?.updateQuestion(quiz)
    }

    override fun onUpdateResult(list: List<Boolean>) {
       view?.updateProgress(list)
    }

    override fun onEndQuiz(isTestPassed: Boolean) {
        if (isTestPassed) view?.showPassedLayout() else view?.showFailedLayout()
    }
}
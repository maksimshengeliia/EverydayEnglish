package com.shengeliia.domain

import com.shengeliia.domain.models.Question

class TestGame(private val repository: Repository, private val onQuestionChangeListener: OnQuestionChangeListener) {

    private val PASS_PERCENT = 0.75f
    private var pointer = 0
    private var points = mutableListOf<Boolean>()
    private var questions = emptyArray<Question>()

    fun initTest(idTest: Int) {
        questions = repository.getQuestions(idTest)
    }

    fun onAnswer(question: Question, answer: Int) {
        if (question.answerRight == answer) {
            points.add(true)
        } else {
            points.add(false)
        }

        if (pointer >= questions.size) {
            onQuestionChangeListener.onEndTest(calculateAnswers())
        } else {
            onQuestionChangeListener.onNextQuestion(questions[++pointer])
        }
    }

    /**
    *   @return тест пройден или нет
    * */
    private fun calculateAnswers() : Boolean {
        var right = 0
        points.forEach {
            if (it) right++
        }

        val percent: Float = right.toFloat() / points.size.toFloat()
        return percent >= PASS_PERCENT
    }

    interface OnQuestionChangeListener {
        /**
        * @param passed тест пройден или нет
        * */
        fun onEndTest(passed: Boolean)

        fun onNextQuestion(question: Question)
    }
}
package com.shengeliia.domain.cases

import com.shengeliia.domain.Repository
import com.shengeliia.domain.models.Question

class QuizCase(private val repository: Repository, testId: Int) {

    private val PASS_PERCENT = 0.75f
    private var pointer = 0
    private var questions = emptyArray<Question>()

    init {
        questions = repository.getQuestionsByTestId(testId)
        questions.forEach {
            if (it.isPassed != null) {
                pointer++
            } else {
                return@forEach
            }
        }
    }

    fun currentProgress(): List<Boolean> {
        return questions.mapNotNull {
            it.isPassed
        }
    }

    fun questionsCount() = questions.size

    fun validateAnswer(userAnswer: Int): Boolean {
        val q = questions[pointer]
        q.isPassed = q.rightAnswer == userAnswer
        return q.rightAnswer == userAnswer
    }

    fun currentQuestion() = questions[pointer]

    fun nextQuestion(): Question? {
        pointer++
        return if (pointer < questions.size) {
            questions[pointer]
        } else {
            null
        }
    }

    /**
    *   @return тест пройден или нет
    * */
    fun isTestPassed() : Boolean {
        var right = 0
        questions.forEach {
            if (it.isPassed!!) right++
        }

        val percent: Float = right.toFloat() / questions.size.toFloat()
        return percent >= PASS_PERCENT
    }
}
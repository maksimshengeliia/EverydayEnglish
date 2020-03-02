package com.shengeliia.domain.cases

import com.shengeliia.domain.QuizState
import com.shengeliia.domain.Repository
import com.shengeliia.domain.TestsState
import com.shengeliia.domain.models.Quiz
import com.shengeliia.domain.models.Test

class QuizCase(private val repository: Repository, private val quizListener: QuizListener, testId: Int) {

    private val PASS_PERCENT = 0.75f
    private val test: Test = repository.getTestById(testId)
    private val quizzes: List<Quiz> = repository.getQuizzesByTest(test).sortedBy { it.id }
    private var curr = -1

    fun init() {
        if (test.state == TestsState.NEW) {
            // обновить состояние теста
            test.state = TestsState.STARTED
            repository.updateTests(listOf(test))
        }
        quizListener.onInitQuiz(getTestResult(), quizzes.size)
        sendNewQuestion()
    }

    fun validateAnswer(userAnswer: Int) {
        val q = quizzes[curr]
        // сохранить новое значение
        q.state = if (userAnswer == q.rightAnswer) QuizState.PASSED else QuizState.FAILED
        repository.updateQuizzes(listOf(q))
        test.quizzesSolved++
        repository.updateTests(listOf(test))
        // обновить UI
        quizListener.onUpdateResult(getTestResult())
    }

    fun sendNewQuestion() {
        curr++
        if (curr < quizzes.size) {
            if (quizzes[curr].state != QuizState.NEW) {
                // если на этот вопрос уже есть ответ, взять следующий
                sendNewQuestion()
            } else {
                // Отправить новый вопрос
                quizListener.onNextQuestion(quizzes[curr])
            }
        } else {
            // Все вопросы в данном тесте окончены

            // Обновить состояние теста
            test.state = TestsState.FINISHED
            repository.updateTests(listOf(test))
            // Отправить результат
            quizListener.onEndQuiz(isTestPassed())
        }
    }

    private fun getTestResult(): List<Boolean> {
        return quizzes.filter {
            it.state != QuizState.NEW
        }.map {
            it.state == QuizState.PASSED
        }
    }

    fun startTestAgain() {
        curr = -1
        // обновить состояние вопросов
        quizzes.forEach {
            it.state = QuizState.NEW
        }
        repository.updateQuizzes(quizzes)

        // обновить состояние тестов
        test.quizzesSolved = 0
        test.state = TestsState.STARTED
        repository.updateTests(listOf(test))

        init()
    }

    /**
    *   @return Boolean: тест пройден или нет
    * */
    private fun isTestPassed() : Boolean {
        var right = 0
        val result = getTestResult()
        result.forEach {
            if (it) right++
        }
        val percent: Float = right.toFloat() / quizzes.size.toFloat()
        return percent >= PASS_PERCENT
    }

    interface QuizListener {
        fun onInitQuiz(list: List<Boolean>, questionsCount: Int)
        fun onNextQuestion(quiz: Quiz)
        fun onUpdateResult(list: List<Boolean>)
        fun onEndQuiz(isTestPassed: Boolean)
    }
}
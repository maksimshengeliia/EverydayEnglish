package com.shengeliia.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.shengeliia.domain.getEnumQuizState
import com.shengeliia.domain.getEnumTestState
import com.shengeliia.domain.models.Quiz
import com.shengeliia.domain.models.Test

object LocalDatabase {
    private var helper: DatabaseHelper? = null

    fun init(context: Context) {
        helper = DatabaseHelper(context)
    }

    fun writableDatabase(): SQLiteDatabase = helper!!.writableDatabase
    fun readableDatabase(): SQLiteDatabase = helper!!.readableDatabase

    fun getTestListFromCursor(cursor: Cursor?): List<Test> {
        val list = mutableListOf<Test>()
        cursor?.use {
            val idCol = it.getColumnIndex(TESTS_ID)
            val nameCol = it.getColumnIndex(TESTS_NAME)
            val countCol = it.getColumnIndex(TESTS_COUNT)
            val quizzesSolvedCol = it.getColumnIndex(TESTS_QUIZZES_SOLVED)
            val stateCol = it.getColumnIndex(TESTS_STATE)

            while (it.moveToNext()) {
                val id = it.getInt(idCol)
                val name = it.getString(nameCol)
                val count = it.getInt(countCol)
                val quizzesSolved = it.getInt(quizzesSolvedCol)
                val state = getEnumTestState(it.getInt(stateCol))
                val test = Test(id, name, count, quizzesSolved, state)
                list.add(test)
            }
        }
        return list
    }

    fun getQuizzesListFromCursor(cursor: Cursor?): List<Quiz> {
        val list = mutableListOf<Quiz>()
        cursor?.use {
            val idCol = it.getColumnIndex(QUIZ_ID)
            val questionCol = it.getColumnIndex(QUIZ_QUESTION)
            val answer1Col = it.getColumnIndex(QUIZ_ANSWER_1)
            val answer2Col = it.getColumnIndex(QUIZ_ANSWER_2)
            val answer3Col = it.getColumnIndex(QUIZ_ANSWER_3)
            val answerRightCol = it.getColumnIndex(QUIZ_ANSWER_RIGHT)
            val testIdCol = it.getColumnIndex(QUIZ_TEST_ID)
            val stateCol = it.getColumnIndex(QUIZ_STATE)

            while (it.moveToNext()) {
                val id = it.getInt(idCol)
                val question = it.getString(questionCol)
                val a1 = it.getString(answer1Col)
                val a2 = it.getString(answer2Col)
                val a3 = it.getString(answer3Col)
                val aRight = it.getInt(answerRightCol)
                val testId = it.getInt(testIdCol)
                val state = getEnumQuizState(it.getInt(stateCol))
                val quiz = Quiz(id, question, a1, a2, a3, aRight, testId, state)
                list.add(quiz)
            }
        }
        return list
    }

    fun setTestContentValues(test: Test): ContentValues {
        val cv = ContentValues()
        cv.put(TESTS_ID, test.id)
        cv.put(TESTS_NAME, test.name)
        cv.put(TESTS_COUNT, test.count)
        cv.put(TESTS_QUIZZES_SOLVED, test.quizzesSolved)
        cv.put(TESTS_STATE, test.state.value)
        return cv
    }

    fun setQuizContentValues(quiz: Quiz): ContentValues {
        val cv = ContentValues()
        cv.put(QUIZ_TEST_ID, quiz.id)
        cv.put(QUIZ_QUESTION, quiz.question)
        cv.put(QUIZ_ANSWER_1, quiz.answer1)
        cv.put(QUIZ_ANSWER_2, quiz.answer2)
        cv.put(QUIZ_ANSWER_3, quiz.answer3)
        cv.put(QUIZ_ANSWER_RIGHT, quiz.rightAnswer)
        cv.put(QUIZ_TEST_ID, quiz.testId)
        cv.put(QUIZ_STATE, quiz.state.value)
        return cv
    }

    const val TABLE_TESTS = "tests"
    const val TABLE_QUIZZES = "questions"

    const val QUIZ_ID = "id"
    const val QUIZ_QUESTION = "question"
    const val QUIZ_ANSWER_1 = "answer1"
    const val QUIZ_ANSWER_2 = "answer2"
    const val QUIZ_ANSWER_3 = "answer3"
    const val QUIZ_ANSWER_RIGHT = "rightAnswer"
    const val QUIZ_TEST_ID = "testId"
    const val QUIZ_STATE = "isPassed"

    const val TESTS_ID = "id"
    const val TESTS_NAME = "name"
    const val TESTS_COUNT = "count"
    const val TESTS_QUIZZES_SOLVED = "quizzesSolved"
    const val TESTS_STATE = "state"

    const val CREATE_TESTS_TABLE = "create table $TABLE_TESTS(" +
            "$TESTS_ID integer primary key asc," +
            "$TESTS_NAME text," +
            "$TESTS_COUNT integer," +
            "$TESTS_QUIZZES_SOLVED integer," +
            "$TESTS_STATE integer)"

    const val CREATE_QUIZ_TABLE = "create table $TABLE_QUIZZES(" +
            "$QUIZ_ID integer primary key asc," +
            "$QUIZ_QUESTION text," +
            "$QUIZ_ANSWER_1 text," +
            "$QUIZ_ANSWER_2 text," +
            "$QUIZ_ANSWER_3 text," +
            "$QUIZ_ANSWER_RIGHT integer, " +
            "$QUIZ_TEST_ID integer," +
            "$QUIZ_STATE integer, " +
            "foreign key ($QUIZ_TEST_ID)" +
            "references $TABLE_TESTS ($TESTS_ID) )"

    const val DELETE_TESTS_TABLE = "drop table if exists $TABLE_TESTS"
    const val DELETE_QUIZ_TABLE = "drop table if exists $TABLE_QUIZZES"
}
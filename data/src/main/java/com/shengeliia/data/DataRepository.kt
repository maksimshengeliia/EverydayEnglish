package com.shengeliia.data

import android.util.Log
import com.shengeliia.data.local.LocalDatabase
import com.shengeliia.data.local.LocalDatabase.QUIZ_TEST_ID
import com.shengeliia.data.local.LocalDatabase.TABLE_QUIZZES
import com.shengeliia.data.local.LocalDatabase.TABLE_TESTS
import com.shengeliia.data.local.LocalDatabase.TESTS_ID
import com.shengeliia.data.local.LocalDatabase.getQuizzesListFromCursor
import com.shengeliia.data.local.LocalDatabase.getTestListFromCursor
import com.shengeliia.data.local.LocalDatabase.setQuizContentValues
import com.shengeliia.data.local.LocalDatabase.setTestContentValues
import com.shengeliia.data.remote.RemoteManager
import com.shengeliia.data.remote.RemoteManager.LAST_ID_PARAMETER
import com.shengeliia.data.remote.RemoteManager.TOKEN_PARAMETER
import com.shengeliia.domain.Repository
import com.shengeliia.domain.models.Quiz
import com.shengeliia.domain.models.Test

class DataRepository : Repository {
    override fun getQuizzesByTest(test: Test): List<Quiz> {
        val db = LocalDatabase.readableDatabase()
        val cursor = db.rawQuery("select * from $TABLE_QUIZZES where $QUIZ_TEST_ID = ?", arrayOf(test.id.toString()))
        val quizzes = getQuizzesListFromCursor(cursor)
        db.close()
        return quizzes
    }

    override fun getTestById(testId: Int): Test {
        val db = LocalDatabase.readableDatabase()
        val cursor = db.rawQuery("select * from $TABLE_TESTS where $TESTS_ID = ?", arrayOf(testId.toString()))
        val tests = getTestListFromCursor(cursor)
        db.close()
        return tests.first()
    }

    override fun updateQuizzes(quizzes: List<Quiz>) {
        val db = LocalDatabase.writableDatabase()
        quizzes.forEach {
            db.update(TABLE_QUIZZES, setQuizContentValues(it), "id = ?", arrayOf(it.id.toString()))
        }
        db.close()
    }

    override fun insertQuizzes(quizzes: List<Quiz>) {
        val db = LocalDatabase.writableDatabase()
        quizzes.forEach {
            db.insert(TABLE_QUIZZES, null, setQuizContentValues(it))
        }
        db.close()
    }

    override fun getQuizzes(): List<Quiz> {
        val db = LocalDatabase.readableDatabase()
        val cursor = db.rawQuery("select * from $TABLE_QUIZZES", null)
        val quizzes = getQuizzesListFromCursor(cursor)
        db.close()
        return quizzes
    }

    override fun updateTests(tests: List<Test>) {
        val db = LocalDatabase.writableDatabase()
        tests.forEach {
            db.update(TABLE_TESTS, setTestContentValues(it), "id = ?", arrayOf(it.id.toString()))
        }
        db.close()
    }

    override fun insertTests(tests: List<Test>) {
        val db = LocalDatabase.writableDatabase()
        tests.forEach {
            db.insert(TABLE_TESTS, null, setTestContentValues(it))
        }
        db.close()
    }

    override fun getTests(): List<Test> {
        val db = LocalDatabase.readableDatabase()
        val cursor = db.rawQuery("select * from $TABLE_TESTS", null)
        val tests = getTestListFromCursor(cursor)
        db.close()
        return tests
    }

    override fun makeCallForUpdates(): List<Test> {
        val db = LocalDatabase.writableDatabase()

        // найти последний id теста в локальной бд
        var lastTestID = 0
        val cursor = db.rawQuery("select $TESTS_ID from $TABLE_TESTS order by $TESTS_ID desc limit 1", null)
        cursor?.use {
            val testIdCol = it.getColumnIndex(TESTS_ID)
            if (it.count > 0) {
                it.moveToFirst()
                lastTestID = it.getInt(testIdCol)
            }
        }

        Log.d("super", lastTestID.toString())

        // сделать запрос к серверу и проверить есть ли новые тесты
        val api = RemoteManager.getClient()
        val call= api.getUpdates(
            hashMapOf(
                TOKEN_PARAMETER to RemoteManager.TOKEN_VALUE,
                LAST_ID_PARAMETER to lastTestID.toString()
            )
        )
        val response = call.execute().body()!!
        val tests = response.tests
        val quizzes = response.quizzes

        if (tests.isNotEmpty()) {
            insertTests(tests)
            insertQuizzes(quizzes)
        }

        db.close()
        // вернуть все тесты из бд
        return getTests()
    }
}
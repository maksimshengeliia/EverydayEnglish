package com.shengeliia.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shengeliia.data.local.LocalDatabase.CREATE_QUIZ_TABLE
import com.shengeliia.data.local.LocalDatabase.CREATE_TESTS_TABLE
import com.shengeliia.data.local.LocalDatabase.DELETE_QUIZ_TABLE
import com.shengeliia.data.local.LocalDatabase.DELETE_TESTS_TABLE

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        createTable(CREATE_TESTS_TABLE, db)
        createTable(CREATE_QUIZ_TABLE, db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        deleteTable(DELETE_TESTS_TABLE, db)
        deleteTable(DELETE_QUIZ_TABLE, db)
        onCreate(db)
    }

    private fun createTable(query: String, db: SQLiteDatabase) {
        db.execSQL(query)
    }

    private fun deleteTable(query: String, db: SQLiteDatabase) {
        db.execSQL(query)
    }

    companion object {
        const val DATABASE_NAME = "everyday_english.db"
        const val DATABASE_VERSION = 1
    }
}
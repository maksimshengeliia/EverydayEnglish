package com.shengeliia.data.local

import android.content.Context

object PreferencesManager {

    private const val PREFERENCE_FILE = "everyday_english"
    const val USERNAME = "username"
    const val LANGUAGE_LEVEL = "lang_level"
    const val LEVEL_BEGINNER = "beginner"
    const val LEVEL_ADVANCED = "advanced"

    fun checkUserIsLogin(context: Context): Boolean {
        val sh = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        return sh.contains(USERNAME)
    }

    fun getUsername(context: Context): String {
        val sh = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        return sh.getString(USERNAME, "")!!
    }

    fun saveUsername(context: Context, username: String) {
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit()
            .putString(USERNAME, username)
            .apply()
    }

    fun saveUserLanguageLevel(context: Context, level: String) {
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit()
            .putString(LANGUAGE_LEVEL, level)
            .apply()
    }

    fun getLevel(context: Context): String {
        val sh = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        return sh.getString(LANGUAGE_LEVEL, "")!!
    }
}
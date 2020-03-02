package com.shengeliia.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteManager {
    const val SERVER_URL = "https://maksimshengeliia.azurewebsites.net/englisheveryday/"
    const val TOKEN_VALUE = "12321"
    const val TOKEN_PARAMETER = "token"
    const val LAST_ID_PARAMETER = "last_id"

    // id_question	text	answer_1	answer_2	answer_3	answer_right	hint	test_id
    // id_test	name_test	count_test

    fun getClient(): ServerApi {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ServerApi::class.java)
    }
}
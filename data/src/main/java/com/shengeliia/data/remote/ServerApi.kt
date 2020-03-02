package com.shengeliia.data.remote

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServerApi {
    @POST("get_updates.php")
    @FormUrlEncoded
    fun getUpdates(@FieldMap lastTestId: Map<String, String>): Call<ResponseData>
}
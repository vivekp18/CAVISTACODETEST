package com.example.cavistacodetest.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface{

    @GET("search/1")
    fun getImages(
        @Header("Authorization") Authentication: String,
        @Query("q") q: String
    ): Call<JsonObject>


//    @FormUrlEncoded
//    @POST("checkLogin")
//    fun login(
//        @Field("mobile_no") mobile_no: String,
//        @Field("device_type") device_type: String,
//        @Field("device_token") device_token: String
//    ): Call<JsonObject>
}


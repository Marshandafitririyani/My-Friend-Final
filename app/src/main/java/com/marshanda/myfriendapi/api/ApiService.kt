package com.marshanda.myfriendapi.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): String

    @FormUrlEncoded
    @POST("api/register")
    suspend fun register(
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ): String

    @FormUrlEncoded
    @POST("api/update-profile")
    suspend fun updateProfil(
        @Field("name") name: String?,
        @Field("school") school: String?,
//        @Field("phone") phone: String?,
        @Field("description") description: String?,
    ): String

    @GET("api/get-list-friends?users_id=2")
    suspend fun listFriend(): String

    @FormUrlEncoded
    @POST("api/like")
    suspend fun like(
        @Field("users_id") id: String,
        @Field("user_id_i_like") id_like: String
    ): String

    @GET("api/get-list-friends")
    suspend fun listFriend234(
        @Query("users_id") userId: String
    ): String
}
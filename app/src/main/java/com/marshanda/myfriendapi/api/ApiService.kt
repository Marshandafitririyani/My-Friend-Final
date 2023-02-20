package com.marshanda.myfriendapi.api

import okhttp3.MultipartBody
import retrofit2.http.*

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
        @Field("id_user") id_user: Int?,
        @Field("name") name: String?,
        @Field("school") school: String?,
        @Field("description") description: String?,
    ): String

    @Multipart
    @POST("api/update-profile")
    suspend fun updateProfileWithPhoto(
        @Query("id_user") id_user: Int?,
        @Query("name") name: String?,
        @Query("school") school: String?,
        @Query("description") description: String?,
        @Part photo: MultipartBody.Part?
    ): String

    @GET("api/get-list-friends?users_id=2")
    suspend fun listFriend(): String

    @FormUrlEncoded
    @POST("api/like")
    suspend fun like(
        @Field("users_id") id:  Int?,
        @Field("user_id_i_like") id_like: Int?,
    ): String

    @GET("api/get-list-friends")
    suspend fun listFriend234(
        @Query("users_id") userId: Int?,
    ): String
}
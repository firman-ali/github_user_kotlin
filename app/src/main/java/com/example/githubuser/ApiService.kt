package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUserList(
        @Query("q") q : String,
        @Header("Authorization") token: String
    ): Call<SearchResponse>

    @GET("users/{user}")
    fun getUser(
        @Path("user") user: String,
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("users/{user}/followers")
    fun getUserFollowers(
        @Path("user") user: String,
        @Header("Authorization") token: String
    ): Call<List<ConnectionResponseItem>>

    @GET("users/{user}/following")
    fun getUserFollowing(
        @Path("user") user: String,
        @Header("Authorization") token: String
    ): Call<List<ConnectionResponseItem>>
}
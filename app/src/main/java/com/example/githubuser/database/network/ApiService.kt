package com.example.githubuser.database.network

import com.example.githubuser.BuildConfig
import com.example.githubuser.database.response.ConnectionResponseItem
import com.example.githubuser.database.response.SearchResponse
import com.example.githubuser.database.response.UserResponse
import com.example.githubuser.database.response.GetResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("search/users")
    fun getUserList(
        @Query("q") q : String
    ): Call<SearchResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}")
    fun getUser(
        @Path("user") user: String
    ): Call<UserResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}/followers")
    fun getUserFollowers(
        @Path("user") user: String
    ): Call<List<ConnectionResponseItem>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}/following")
    fun getUserFollowing(
        @Path("user") user: String
    ): Call<List<ConnectionResponseItem>>

    @GET("users")
    fun getUserHome(): Call<List<GetResponse>>
}
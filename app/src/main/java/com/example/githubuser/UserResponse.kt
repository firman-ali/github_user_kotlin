package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@field:SerializedName("email")
	val email: Any? = null,
)
package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("score")
	val score: Double,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("node_id")
	val nodeId: String
)

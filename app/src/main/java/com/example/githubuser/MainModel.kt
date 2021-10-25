package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel : ViewModel() {
    companion object{
        const val TOKEN = "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"
        const val TAG = "ViewModel"
    }

    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _listReview = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _userFollowers = MutableLiveData<List<ConnectionResponseItem>>()
    val userFollowers: LiveData<List<ConnectionResponseItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<ConnectionResponseItem>>()
    val userFollowing: LiveData<List<ConnectionResponseItem>> = _userFollowing

    fun getUser(userId: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(userId,TOKEN)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findUser(queryText : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserList(queryText, TOKEN)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.items
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })
    }

    fun getFollowers(user : String){
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserFollowers(user,TOKEN)
        client.enqueue(object : Callback<List<ConnectionResponseItem>> {
            override fun onResponse(call: Call<List<ConnectionResponseItem>>, response: Response<List<ConnectionResponseItem>>) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ConnectionResponseItem>>, t: Throwable) {
                _isLoadingDetail.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(user : String){
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserFollowing(user,TOKEN)
        client.enqueue(object : Callback<List<ConnectionResponseItem>> {
            override fun onResponse(call: Call<List<ConnectionResponseItem>>, response: Response<List<ConnectionResponseItem>>) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ConnectionResponseItem>>, t: Throwable) {
                _isLoadingDetail.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
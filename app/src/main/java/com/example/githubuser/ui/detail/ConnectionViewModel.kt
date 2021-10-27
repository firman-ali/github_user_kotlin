package com.example.githubuser.ui.detail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.network.ApiConfig
import com.example.githubuser.database.response.ConnectionResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConnectionViewModel: ViewModel() {
    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _isLoadingNetwork = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoadingNetwork

    private val _userFollowers = MutableLiveData<List<ConnectionResponseItem>>()
    val userFollowers: LiveData<List<ConnectionResponseItem>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<ConnectionResponseItem>>()
    val userFollowing: LiveData<List<ConnectionResponseItem>> = _userFollowing

    private val _getDataStatus = MutableLiveData<Int>()
    val getDataStatus : LiveData<Int> = _getDataStatus

    fun getFollowers(user : String){
        _isLoadingNetwork.value = true
        val client = ApiConfig.getApiService().getUserFollowers(user)
        client.enqueue(object : Callback<List<ConnectionResponseItem>> {
            override fun onResponse(call: Call<List<ConnectionResponseItem>>, response: Response<List<ConnectionResponseItem>>) {
                _isLoadingNetwork.value = false
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                    if (response.body()?.isEmpty()!!) _getDataStatus.value = View.VISIBLE
                    else _getDataStatus.value = View.GONE
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ConnectionResponseItem>>, t: Throwable) {
                _isLoadingNetwork.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(user : String){
        _isLoadingNetwork.value = true
        val client = ApiConfig.getApiService().getUserFollowing(user)
        client.enqueue(object : Callback<List<ConnectionResponseItem>> {
            override fun onResponse(call: Call<List<ConnectionResponseItem>>, response: Response<List<ConnectionResponseItem>>) {
                _isLoadingNetwork.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                    if (response.body()?.isEmpty()!!) _getDataStatus.value = View.VISIBLE
                    else _getDataStatus.value = View.GONE
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ConnectionResponseItem>>, t: Throwable) {
                _isLoadingNetwork.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        const val TAG = "ConnectionViewModel"
    }
}
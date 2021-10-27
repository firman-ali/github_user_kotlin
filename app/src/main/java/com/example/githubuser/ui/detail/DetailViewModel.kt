package com.example.githubuser.ui.detail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.network.ApiConfig
import com.example.githubuser.database.response.ConnectionResponseItem
import com.example.githubuser.database.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoadingDetail

    fun getUser(userId: String){
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUser(userId)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoadingDetail.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    _error.value = true
                    Log.e(TAG, "responseOnFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        const val TAG = "DetailViewModel"
    }
}
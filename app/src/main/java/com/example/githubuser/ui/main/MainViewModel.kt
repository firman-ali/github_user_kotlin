package com.example.githubuser.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.network.ApiConfig
import com.example.githubuser.database.response.ItemsItem
import com.example.githubuser.database.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _listReview = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getDataStatus = MutableLiveData<Int>()
    val getDataStatus : LiveData<Int> = _getDataStatus

    fun findUser(queryText : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserList(queryText)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.items
                    if (response.body()?.items?.isEmpty()!!) _getDataStatus.value = View.VISIBLE
                    else _getDataStatus.value = View.GONE
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

    companion object{
        const val TAG = "MainViewModel"
    }
}
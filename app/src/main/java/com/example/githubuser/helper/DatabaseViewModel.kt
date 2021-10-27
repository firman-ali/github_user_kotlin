package com.example.githubuser.helper

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.room.FavouriteUser
import com.example.githubuser.database.room.FavouriteUserRepository

class DatabaseViewModel(application: Application) : ViewModel() {
    private val favUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)

    fun findUserFav(id: Int, username:String): LiveData<FavouriteUser> = favUserRepository.findUserFav(id,username)

    fun getAllUserFav(): LiveData<List<FavouriteUser>> = favUserRepository.getAllUserFav()

    fun insert(userData: FavouriteUser) {
        favUserRepository.insert(userData)
    }

    fun delete(userData: FavouriteUser) {
        favUserRepository.delete(userData)
    }
}
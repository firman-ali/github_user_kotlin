package com.example.githubuser.database.room

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteUserRepository(application: Application) {
    private val mFavUserDao: FavouriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavouriteUserDatabase.getDatabase(application)
        mFavUserDao = db.favouriteUserDao()
    }

    fun findUserFav(id: Int, username:String): LiveData<FavouriteUser> = mFavUserDao.findUserFav(id,username)

    fun getAllUserFav(): LiveData<List<FavouriteUser>> = mFavUserDao.getAllUserFav()

    fun insert(userData: FavouriteUser) {
        executorService.execute { mFavUserDao.insert(userData) }
    }

    fun delete(userData: FavouriteUser) {
        executorService.execute { mFavUserDao.delete(userData) }
    }
}
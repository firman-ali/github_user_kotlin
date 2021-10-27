package com.example.githubuser.database.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavouriteUser)
    @Delete
    fun delete(favUser: FavouriteUser)
    @Query("SELECT * from favouriteuser")
    fun getAllUserFav(): LiveData<List<FavouriteUser>>
    @Query("SELECT * from favouriteuser WHERE login LIKE :username AND id LIKE :id")
    fun findUserFav(id: Int , username: String): LiveData<FavouriteUser>
}
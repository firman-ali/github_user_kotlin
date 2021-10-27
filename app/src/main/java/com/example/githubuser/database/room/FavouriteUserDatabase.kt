package com.example.githubuser.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteUser::class], version = 1)
abstract class FavouriteUserDatabase: RoomDatabase() {
    abstract fun favouriteUserDao(): FavouriteUserDao
    companion object {
        @Volatile
        private var INSTANCE: FavouriteUserDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavouriteUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavouriteUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavouriteUserDatabase::class.java, "fav_user_database")
                        .build()
                }
            }
            return INSTANCE as FavouriteUserDatabase
        }
    }
}
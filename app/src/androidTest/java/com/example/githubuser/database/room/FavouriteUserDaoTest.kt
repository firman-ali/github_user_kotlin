package com.example.githubuser.database.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavouriteUserDaoTest {

    @get:Rule
    var instantTaskEksekutorRule = InstantTaskExecutorRule()

    private lateinit var database: FavouriteUserDatabase
    private lateinit var dao: FavouriteUserDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), FavouriteUserDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favouriteUserDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertUser() = runBlocking {
        // Test input random data
        val favouriteUser = FavouriteUser(0, "UserName", "TesUrl", "https:/tes","Test Date")
        dao.insert(favouriteUser)

        val allUser = dao.getAllUserFav().getOrAwaitValue()

        assert(allUser.contains(favouriteUser))
    }
}
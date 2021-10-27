package com.example.githubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.database.network.ApiConfig
import com.example.githubuser.database.response.GetResponse
import com.example.githubuser.database.response.ItemsItem
import com.example.githubuser.database.response.SearchResponse
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.helper.ListUserAdapter
import com.example.githubuser.helper.User
import com.example.githubuser.ui.detail.DetailUserActivity
import com.example.githubuser.ui.favorite.FavouriteActivity
import com.example.githubuser.ui.setting.SettingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val model by viewModels<MainViewModel>()

    private lateinit var data: List<GetResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        model.isLoading.observe(this, {
            showLoading(it)
        })

        model.listUser.observe(this, { userList ->
            showRvData(userList)
        })

        model.getDataStatus.observe(this, { status ->
            showTvNotFound(status)
        })

        model.isError.observe(this, {
            showError()
        })

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                model.findUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
//        binding.rvUserListMain.layoutManager = LinearLayoutManager(this)
//        getDataUser()
    }

//    fun getDataUser(){
//        val client = ApiConfig.getApiService().getUserHome()
//        client.enqueue(object : Callback<List<GetResponse>> {
//            override fun onResponse(call: Call<List<GetResponse>>, response: Response<List<GetResponse>>) {
//                if (response.isSuccessful) {
//                    data = response.body()!!
//                    val userData = ArrayList<User>()
//                    for (i in data){
//                        userData.add(User(i.login,i.htmlUrl,i.avatarUrl))
//                    }
//
//                    val adapter = ListUserAdapter(userData)
//                    binding.rvUserListMain.adapter = adapter
//
//                } else {
//                    Log.e(MainViewModel.TAG, "responseOnFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<List<GetResponse>>, t: Throwable) {
//                Log.e(MainViewModel.TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    private fun showTvNotFound(status: Int) {
        binding.tvUserNotFound.visibility = status
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.menu2).isVisible = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                SettingFragment.newInstance().show(supportFragmentManager, SettingFragment.TAG)
                true
            }
            R.id.menu2 -> {
                val moveIntent = Intent(this, FavouriteActivity::class.java)
                startActivity(moveIntent)
                true
            }
            else -> true
        }
    }

    private fun showRvData(userQuery: List<ItemsItem>) {
        if (this.applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUserListMain.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUserListMain.layoutManager = LinearLayoutManager(this)
        }

        val listUser = ArrayList<User>()
        for (query in userQuery) {
            val user = User(query.login,query.htmlUrl,query.avatarUrl)
            listUser.add(user)
        }

        val adapter = ListUserAdapter(listUser)
        binding.rvUserListMain.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(userId: String) {
                moveSelectedUser(userId)
            }
        })
    }

    private fun showError(){
        Toast.makeText(this, R.string.massage, Toast.LENGTH_SHORT).show()
    }

    private fun moveSelectedUser(id: String) {
        val moveIntent = Intent(this, DetailUserActivity::class.java).putExtra(
            DetailUserActivity.USER_ID, id)
        startActivity(moveIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
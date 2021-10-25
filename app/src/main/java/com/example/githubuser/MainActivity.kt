package com.example.githubuser

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model by viewModels<MainModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)

        model.isLoading.observe(this, {
            showLoading(it)
        })

        model.listUser.observe(this, { userList ->
            showRvData(userList)
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
    }

    private fun showRvData(userQuery: List<ItemsItem>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUserList.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUserList.layoutManager = LinearLayoutManager(this)
        }

        val listUser = ArrayList<User>()
        for (query in userQuery) {
            val user = User(query.login,query.url,query.avatarUrl)
            listUser.add(user)
        }

        val adapter = UserAdapter(listUser)
        binding.rvUserList.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(userId: String) {
                moveSelectedUser(userId)
            }
        })
    }

    private fun showError(){
        Toast.makeText(this,DetailUserActivity.MASSAGE,Toast.LENGTH_SHORT).show()
    }

    private fun moveSelectedUser(id: String) {
        val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java).putExtra(DetailUserActivity.USER_ID, id)
        startActivity(moveIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.mainProgressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}
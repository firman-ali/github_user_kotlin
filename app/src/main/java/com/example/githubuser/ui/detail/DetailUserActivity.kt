package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.*
import com.example.githubuser.database.response.UserResponse
import com.example.githubuser.database.room.FavouriteUser
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.helper.DatabaseViewModel
import com.example.githubuser.helper.DateHelper
import com.example.githubuser.helper.RoomViewModelFactory
import com.example.githubuser.ui.setting.SettingFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!
    private val model by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.detail_title)
        val userId = intent.getStringExtra(USER_ID)

        if(savedInstanceState == null) model.getUser(userId!!)

        model.user.observe(this, { userData ->
            setViewPager(userData.login)
            setUserData(userData)
            binding.fabAddFavourite.setOnClickListener(this)
        })

        model.isLoading.observe(this, {
            showLoading(it)
        })

        model.isError.observe(this, {showError()})
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                SettingFragment.newInstance().show(supportFragmentManager, SettingFragment.TAG)
                true
            }
            else -> true
        }
    }

    private fun setViewPager(uid:String){
        UserConnectionFragment.userId = uid
        binding.viewPager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUserData(userData: UserResponse) {
        Glide.with(this)
            .load(userData.avatarUrl)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.text = userData.name ?: "-"
        binding.tvDetailUsername.text = userData.login
        binding.tvDetailLocation.text = userData.location ?: "-"
        binding.tvDetailCompany.text = userData.company ?: "-"
        binding.tvDetailFollowers.text = userData.followers.toString()
        binding.tvDetailFollowing.text = userData.following.toString()
        binding.tvDetailRepo.text = userData.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(){
        Toast.makeText(this, R.string.massage,Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        val userData = model.user.value!!
        val userModel = FavouriteUser()
        val factory = RoomViewModelFactory.getInstance(this.application)
        val mainViewModel = ViewModelProvider(this, factory).get(DatabaseViewModel::class.java)

        mainViewModel.findUserFav(userData.id, userData.login).observe(this,{ userFav ->
            if( userFav == null){
                userModel.let {user ->
                    user.id = userData.id
                    user.avatarUrl = userData.avatarUrl
                    user.htmlUrl = userData.htmlUrl
                    user.login = userData.login
                    user.addedAt = DateHelper.getCurrentDate()
                }
                mainViewModel.insert(userModel)
                showReminder(getString(R.string.added))
            }
            else if (userModel.id == null){
                showReminder(getString(R.string.been_added))
            }
        })
    }

    private fun showReminder(massage: String) {
        Toast.makeText(this,massage,Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val USER_ID = "user_id"
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
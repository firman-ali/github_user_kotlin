package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val model by viewModels<MainModel>()

    companion object {
        const val TITLE = "Detail User"
        const val USER_ID = "user_id"
        const val MASSAGE = "Maaf Data Tidak Berhasil Dimuat, Silahkan Coba Lagi"
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = TITLE
        val userId = intent.getStringExtra(USER_ID)

        if(savedInstanceState == null) model.getUser(userId!!)

        model.user.observe(this, { userData ->
            setViewPager(userData.login)
            setUserData(userData)
        })

        model.isLoading.observe(this, {
            showLoading(it)
        })

        model.isError.observe(this, {showError()})
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
        binding.tvDetailName.text = userData.name
        binding.tvDetailUsername.text = userData.login
        binding.tvDetailLocation.text = userData.location
        binding.tvDetailCompany.text = userData.company
        binding.tvDetailFollowers.text = userData.followers.toString()
        binding.tvDetailFollowing.text = userData.following.toString()
        binding.tvDetailRepo.text = userData.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(){
        Toast.makeText(this,MASSAGE,Toast.LENGTH_SHORT).show()
    }
}
package com.example.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.database.room.FavouriteUser
import com.example.githubuser.databinding.ActivityFavouriteBinding
import com.example.githubuser.helper.DatabaseViewModel
import com.example.githubuser.helper.RoomViewModelFactory
import com.example.githubuser.ui.detail.DetailUserActivity
import com.example.githubuser.ui.setting.SettingFragment

class FavouriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListFavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.favourite_user)

        showLoading(true)

        val favoriteViewModel = obtainViewModel(this)
        favoriteViewModel.getAllUserFav().observe(this, { cashFlowList ->
            if(cashFlowList != null) {
                adapter.setListUserFav(cashFlowList)
            }
            showLoading(false)

            if(cashFlowList.isEmpty()) binding.tvUserNotFound.visibility = View.VISIBLE
            else binding.tvUserNotFound.visibility = View.GONE
        })

        adapter = ListFavouriteAdapter()
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        binding.rvUserList.setHasFixedSize(true)
        binding.rvUserList.adapter = adapter

        adapter.setOnItemClickCallback(object : ListFavouriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: FavouriteUser) {
                moveSelectedUser(user)
            }
        })
        adapter.setOnButtonClickCallback(object : ListFavouriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: FavouriteUser) {
                showAlertDialog(favoriteViewModel,user)
            }

        })
    }

    private fun showAlertDialog(model: DatabaseViewModel, user: FavouriteUser) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(getString(R.string.delete_title))
            setMessage(getString(R.string.delete_alert))
            setCancelable(false)
            setPositiveButton(R.string.yes) { _, _ ->
                model.delete(user)
                Toast.makeText(context,R.string.delete_massage,Toast.LENGTH_SHORT).show()
                finish()
                startActivity(intent)
            }
            setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFavourite.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: FragmentActivity): DatabaseViewModel {
        val factory = RoomViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DatabaseViewModel::class.java)
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

    private fun moveSelectedUser(id: FavouriteUser) {
        val moveIntent = Intent(this, DetailUserActivity::class.java).putExtra(
            DetailUserActivity.USER_ID, id.login)
        startActivity(moveIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
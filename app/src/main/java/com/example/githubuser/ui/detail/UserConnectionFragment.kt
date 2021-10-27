package com.example.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.helper.User
import com.example.githubuser.database.response.ConnectionResponseItem
import com.example.githubuser.helper.ListUserAdapter
import com.example.githubuser.databinding.FragmentUserConnectionBinding

class UserConnectionFragment : Fragment() {

    private var _binding: FragmentUserConnectionBinding? = null
    private val binding get() = _binding!!
    private val model by viewModels<ConnectionViewModel>()

    companion object{
        private const val ARG_SECTION_TYPE = "section_position"
        lateinit var userId: String
        fun newInstance(position: Int) =
            UserConnectionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_TYPE, position)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserConnectionBinding.inflate(inflater, container, false)

        binding.rvUserConnection.layoutManager = LinearLayoutManager(context)

        if(savedInstanceState == null) {
            when(arguments?.getInt(ARG_SECTION_TYPE)){
                0 -> {
                    model.getFollowers(userId)
                }
                1 -> {
                    model.getFollowing(userId)
                }
            }
        }

        model.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        model.userFollowing.observe(viewLifecycleOwner, { userQuery ->
            showRvFollow(userQuery)
        })

        model.userFollowers.observe(viewLifecycleOwner, { userQuery ->
            showRvFollow(userQuery)
        })

        model.getDataStatus.observe(viewLifecycleOwner, { status ->
            showTvNotFound(status)
        })

        model.isError.observe(viewLifecycleOwner, {
            showError()
        })

        return binding.root
    }

    private fun showRvFollow(userQuery:List<ConnectionResponseItem>){
        val listUser = ArrayList<User>()
        for (query in userQuery){
            val userData = User(query.login,query.htmlUrl,query.avatarUrl)
            listUser.add(userData)
        }

        when(arguments?.getInt(ARG_SECTION_TYPE)){
            0 -> {
                binding.tvUserNetwork.text = getString(R.string.user_status_network_follower)
            }
            1 -> {
                binding.tvUserNetwork.text = getString(R.string.user_status_network_following)
            }
        }

        val adapter = ListUserAdapter(listUser)
        binding.rvUserConnection.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(userId: String) {
                moveSelectedUser(userId)
            }
        })
    }

    private fun showTvNotFound(status: Int) {
        binding.tvUserNetwork.visibility = status
    }

    private fun moveSelectedUser(id: String) {
        val moveIntent = Intent(activity, DetailUserActivity::class.java).putExtra(
            DetailUserActivity.USER_ID, id)
        startActivity(moveIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUserConnection.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(){
        Toast.makeText(activity, R.string.massage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
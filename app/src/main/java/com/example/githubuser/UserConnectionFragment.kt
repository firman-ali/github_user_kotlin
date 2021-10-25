package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentUserConnectionBinding

class UserConnectionFragment : Fragment() {

    private lateinit var binding: FragmentUserConnectionBinding
    private val model by viewModels<MainModel>()

    companion object{
        private const val ARG_SECTION_TYPE = "section_type"
        lateinit var userId: String
        fun newInstance(conn: String) =
            UserConnectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_TYPE, conn)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserConnectionBinding.inflate(inflater, container, false)

        binding.rvUserConnection.layoutManager = LinearLayoutManager(context)

        if(savedInstanceState == null) {
            when(arguments?.getString(ARG_SECTION_TYPE)){
                "${R.string.tab_text_1}" -> {
                    model.getFollowers(userId)
                }
                "${R.string.tab_text_2}" -> {
                    model.getFollowing(userId)
                }
            }
        }

        model.isLoadingDetail.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        model.userFollowing.observe(viewLifecycleOwner, { userQuery ->
            showRvFollow(userQuery)
        })

        model.userFollowers.observe(viewLifecycleOwner, { userQuery ->
            showRvFollow(userQuery)
        })

        model.isError.observe(viewLifecycleOwner, {
            showError()
        })

        return binding.root
    }

    private fun showRvFollow(userQuery:List<ConnectionResponseItem>){
        val listUser = ArrayList<User>()
        for (query in userQuery){
            val userData = User(query.login,query.url,query.avatarUrl)
            listUser.add(userData)
        }

        val adapter = UserAdapter(listUser)
        binding.rvUserConnection.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(userId: String) {
                moveSelectedUser(userId)
            }
        })
    }

    private fun moveSelectedUser(id: String) {
        val moveIntent = Intent(activity, DetailUserActivity::class.java).putExtra(DetailUserActivity.USER_ID, id)
        startActivity(moveIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUserConnection.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(){
        Toast.makeText(activity, DetailUserActivity.MASSAGE, Toast.LENGTH_SHORT).show()
    }
}
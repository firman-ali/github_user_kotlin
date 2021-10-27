package com.example.githubuser.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.room.FavouriteUser
import com.example.githubuser.databinding.ItemRowFavUserBinding
import com.example.githubuser.helper.ListFavDiffCallback

class ListFavouriteAdapter: RecyclerView.Adapter<ListFavouriteAdapter.ViewHolder>(){
    private val listUser = ArrayList<FavouriteUser>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onButtonClickCallback: OnItemClickCallback

    fun setListUserFav(listCashFlow: List<FavouriteUser>) {
        val diffCallback = ListFavDiffCallback(this.listUser, listCashFlow)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listCashFlow)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowFavUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar = listUser[position].avatarUrl
        val username = listUser[position].login
        val gitLink = listUser[position].htmlUrl

        Glide.with(holder.itemView.context)
            .load(avatar)
            .into(holder.binding.ivUserImage)
        holder.binding.tvUserLink.text = gitLink
        holder.binding.tvUserName.text = username
        holder.binding.ibDelete.setOnClickListener {onButtonClickCallback.onItemClicked(listUser[position])}
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ViewHolder(var binding: ItemRowFavUserBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnButtonClickCallback(onButtonClickCallback: OnItemClickCallback) {
        this.onButtonClickCallback = onButtonClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: FavouriteUser)
    }

}
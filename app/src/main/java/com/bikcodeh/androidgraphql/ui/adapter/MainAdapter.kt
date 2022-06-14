package com.bikcodeh.androidgraphql.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bikcodeh.androidgraphql.databinding.ItemUserBinding
import com.bikcodeh.androidgraphql.ui.viewholder.MainViewHolder
import com.bikcodeh.domain.model.User

class MainAdapter(
    private val onClickUser: (user: User) -> Unit
) : ListAdapter<User, MainViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding, onClickUser)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


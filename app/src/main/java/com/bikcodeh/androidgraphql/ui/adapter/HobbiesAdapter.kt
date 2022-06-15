package com.bikcodeh.androidgraphql.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bikcodeh.androidgraphql.databinding.ItemHobbyBinding
import com.bikcodeh.androidgraphql.ui.viewholder.HobbiesViewHolder
import com.bikcodeh.domain.model.Hobby

class HobbiesAdapter : ListAdapter<Hobby, HobbiesViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Hobby>() {
        override fun areItemsTheSame(oldItem: Hobby, newItem: Hobby): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hobby, newItem: Hobby): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbiesViewHolder {
        val binding = ItemHobbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HobbiesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package com.bikcodeh.androidgraphql.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bikcodeh.androidgraphql.databinding.ItemHobbyBinding
import com.bikcodeh.domain.model.Hobby

class HobbiesViewHolder(private val binding: ItemHobbyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(hobby: Hobby) {
        binding.tvHobby.text = hobby.title
    }
}
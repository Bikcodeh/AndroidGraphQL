package com.bikcodeh.androidgraphql.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bikcodeh.androidgraphql.databinding.ItemPostBinding
import com.bikcodeh.domain.model.Post

class MainViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.tvTitlePost.text = post.comment
    }
}
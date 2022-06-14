package com.bikcodeh.androidgraphql.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bikcodeh.androidgraphql.databinding.ItemUserBinding
import com.bikcodeh.domain.model.User

class MainViewHolder(private val binding: ItemUserBinding, private val onClickUser: (user: User) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        with(binding) {
            tvName.text = user.name
            tvAge.text = user.age.toString()
            tvDescription.text = user.profession

            this.root.setOnClickListener {
                onClickUser(user)
            }
        }
    }
}
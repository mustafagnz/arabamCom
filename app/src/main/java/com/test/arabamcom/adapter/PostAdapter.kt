package com.test.arabamcom.adapter

import PostViewHolder
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.arabamcom.PostModelDiffCallback
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel

class PostAdapter(private val onItemClick: (PostModel) -> Unit) : ListAdapter<PostModel, PostViewHolder>(
    PostModelDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return PostViewHolder(view)
    }

    fun getPosts(title: String): List<PostModel> {
        return currentList
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bindView(post)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(post)

        }
    }
}





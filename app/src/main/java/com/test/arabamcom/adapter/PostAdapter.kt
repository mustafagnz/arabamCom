package com.test.arabamcom.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel

// PostAdapter'ı ListAdapter'dan türetiyoruz ve PostModel sınıfını parametre olarak belirtiyoruz
class PostAdapter(postModelList: PostModel) : ListAdapter<PostModel, PostViewHolder>(PostModelDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}

// PostModelDiffCallback sınıfını ekliyoruz
class PostModelDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}

// PostViewHolder sınıfını güncelliyoruz
class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ilanBaslik: TextView = itemView.findViewById(R.id.ilanBaslik)
    private val ilanIl: TextView = itemView.findViewById(R.id.ilanIl)
    private val ilanIlce: TextView = itemView.findViewById(R.id.ilanIlce)
    private val ilanFiyat: TextView = itemView.findViewById(R.id.ilanFiyat)
    private val ilanResim: ImageView = itemView.findViewById(R.id.ilanResim)

    fun bindView(postModel: PostModel) {
        ilanBaslik.text = postModel.title
        ilanIl.text = (postModel.location.cityName + "/")
        ilanIlce.text = postModel.location.townName
        ilanFiyat.text = (postModel.price.toString() + "TL")

        Glide.with(itemView.context)
            .load(postModel.photos.firstOrNull())
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(ilanResim)
    }
}


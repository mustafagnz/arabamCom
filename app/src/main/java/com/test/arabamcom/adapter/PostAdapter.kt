package com.test.arabamcom.adapter

import android.util.Log
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.ui.AdvertDetailsFragment
import com.test.arabamcom.ui.MainViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel


// PostAdapter'ı ListAdapter'dan türetiyoruz ve PostModel sınıfını parametre olarak belirtiyoruz
class PostAdapter : ListAdapter<PostModel, PostViewHolder>(PostModelDiffCallback()) {

    private var postList: ArrayList<PostModel> = ArrayList()

    fun addPosts(posts: List<PostModel>) {
        postList.clear()
        postList.addAll(posts)
        notifyDataSetChanged()

        //printDataToConsole()
    }

    private fun printDataToConsole() {
        for (post in postList) {
            Log.d("PostAdapter", "Post ID: ${post.id}, Title: ${post.title}, City: ${post.location.cityName}, Town: ${post.location.townName}, Price: ${post.price}")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (postList.isNotEmpty()) {
            val post = postList[position]
            holder.bindView(post)

            holder.itemView.setOnClickListener {
                val activity = it.context as AppCompatActivity
                val advertDetailsFragment = AdvertDetailsFragment()

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, advertDetailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}

class PostModelDiffCallback : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}

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

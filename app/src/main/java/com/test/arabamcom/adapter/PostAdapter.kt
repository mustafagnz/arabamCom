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


// PostAdapter'ı ListAdapter'dan türetiyoruz ve PostModel sınıfını parametre olarak belirtiyoruz
class PostAdapter(private val onItemClick: (PostModel) -> Unit) : ListAdapter<PostModel, PostViewHolder>(PostModelDiffCallback()) {

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

        // PostModel.photos boş değilse ve en az bir fotoğraf varsa Glide kullan
        if (!postModel.photos.isNullOrEmpty()) {
            Glide.with(itemView.context)
                .load(postModel.photos.first())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(ilanResim)
        } else {
            // Eğer fotoğraf yoksa varsayılan bir resim kullanabilirsiniz
            ilanResim.setImageResource(R.drawable.ic_launcher_background)
        }
    }

}

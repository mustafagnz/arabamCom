import com.test.arabamcom.R


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.arabamcom.api.PostModel

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ilanBaslik: TextView = itemView.findViewById(R.id.cardPostTitle)
    private val ilanIl: TextView = itemView.findViewById(R.id.cardPostCity)
    private val ilanIlce: TextView = itemView.findViewById(R.id.cardPostRegion)
    private val ilanFiyat: TextView = itemView.findViewById(R.id.cardPostPrice)
    private val ilanResim: ImageView = itemView.findViewById(R.id.cardPostImage)

    fun bindView(postModel: PostModel) {
        ilanBaslik.text = postModel.title
        ilanIl.text = (postModel.location.cityName + "/")
        ilanIlce.text = postModel.location.townName
        ilanFiyat.text = (postModel.price.toString() + "TL")

        if (!postModel.photos.isNullOrEmpty()) {
            Glide.with(itemView.context)
                .load(postModel.photos)
                .error(R.drawable.ic_launcher_foreground)
                .into(ilanResim)
        } else {
            ilanResim.setImageResource(R.drawable.ic_launcher_background)
        }
    }

}
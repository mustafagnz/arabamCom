package com.test.arabamcom.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.test.arabamcom.R
import com.test.arabamcom.adapter.PostAdapter


class AdvertDetailsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.selectedPost.observe(viewLifecycleOwner){post ->
            Log.d("AdvertDetailsFragment", "Post observed: ${post.title}")

            view.findViewById<TextView>(R.id.textTitle)?.text = post.title
            view.findViewById<TextView>(R.id.textIlce)?.text = post.location.townName
            view.findViewById<TextView>(R.id.textIl)?.text = post.location.cityName
            view.findViewById<TextView>(R.id.textFiyat)?.text = "${post.price} TL"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_details, container, false)
    }
}


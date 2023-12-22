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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.test.arabamcom.R
import com.test.arabamcom.adapter.PostAdapter
import com.test.arabamcom.adapter.ViewPagerAdapter
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.ui.AdvertDetailTabBar.AdvertDescriptionFragment
import com.test.arabamcom.ui.AdvertDetailTabBar.AdvertInformationFragment


class AdvertDetailsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedPost = arguments?.getSerializable("selected_post") as? PostModel
        selectedPost?.let { post ->
            Log.d("AdvertDetailsFragment", "Post observed: ${post.title}")

            view.findViewById<TextView>(R.id.textTitle)?.text = post.title
            view.findViewById<TextView>(R.id.textIlce)?.text = "${post.location.townName}/"
            view.findViewById<TextView>(R.id.textIl)?.text = post.location.cityName
            view.findViewById<TextView>(R.id.textFiyat)?.text = "${post.price} TL"
        }

        val viewPager: ViewPager = view.findViewById(R.id.viewPagerTabs)
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)

        tabLayout.setupWithViewPager(viewPager)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(AdvertInformationFragment(), "İlan Bilgileri")
        adapter.addFragment(AdvertDescriptionFragment(), "Açıklama")

        viewPager.adapter = adapter

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_advert_details, container, false)
    }


}


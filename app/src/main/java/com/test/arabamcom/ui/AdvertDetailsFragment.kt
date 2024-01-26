package com.test.arabamcom.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.arabamcom.R
import com.test.arabamcom.adapter.ViewPagerAdapter
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.databinding.FragmentAdvertDetailsBinding
import com.test.arabamcom.ui.AdvertDetailTabBar.AdvertDescriptionFragment
import com.test.arabamcom.ui.AdvertDetailTabBar.AdvertInformationFragment

class AdvertDetailsFragment : Fragment() {

    private var _binding: FragmentAdvertDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdvertDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            // Geri gitme işlemi
            requireActivity().onBackPressed()
        }
        binding.shareButton.setOnClickListener {
            // Paylaşım işlemi
            shareAdvert()
        }

        val selectedPost = arguments?.getSerializable("selected_post") as? PostModel
        selectedPost?.let { post ->
            binding.advertDetailTitleName.text = post.title
            binding.advertDetailRegion.text = "${post.location.townName}/"
            binding.advertDetailCity.text = post.location.cityName
            binding.advertDetailPrice.text = "${post.price} TL"
        }

        val advertInformationFragment = AdvertInformationFragment.newInstance(selectedPost!!)
        val advertDescriptionFragment = AdvertDescriptionFragment.newInstance(selectedPost!!)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(advertInformationFragment, "İlan Bilgileri")
        adapter.addFragment(advertDescriptionFragment, "Açıklama")

        binding.viewPagerTabs.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPagerTabs)
    }

    private fun shareAdvert() {
        val selectedPost = arguments?.getSerializable("selected_post") as? PostModel
        val shareText = "${selectedPost?.title} - ${selectedPost?.location?.cityName}, ${selectedPost?.location?.townName} - ${selectedPost?.price} TL"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        startActivity(Intent.createChooser(shareIntent, "İlanı Paylaş"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.test.arabamcom.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.arabamcom.R
import com.test.arabamcom.adapter.PostAdapter
import com.test.arabamcom.api.PostModel

class AdvertsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adverts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        val postAdapter = PostAdapter { post ->
            mainViewModel.setSelectedPost(post)
            navigateToAdvertDetailsFragment(post)
        }
        recyclerView.adapter = postAdapter

        mainViewModel.post.observe(viewLifecycleOwner) { postModelList ->
            postAdapter.submitList(postModelList)
            Log.d(TAG, "onViewCreated: veri var")

            if (postModelList.isEmpty()) {

                Log.d(TAG, "onViewCreated: veri yok")
            }
        }


    }

    private fun navigateToAdvertDetailsFragment(post: PostModel) {
        val advertDetailsFragment = AdvertDetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable("selected_post", post)
        advertDetailsFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, advertDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

}




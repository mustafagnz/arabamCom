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

class AdvertsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adverts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        val postAdapter = PostAdapter { post ->
            mainViewModel.setSelectedPost(post)
            navigateToAdvertDetailsFragment()
        }
        recyclerView.adapter = postAdapter

        mainViewModel.post.observe(viewLifecycleOwner) { postModelList ->
            postAdapter.submitList(postModelList)
            Log.d(TAG, "onViewCreated: veri var")
            // Burada, postModelList'in boş olup olmadığını kontrol edebilirsiniz
            if (postModelList.isEmpty()) {
                // Veri yoksa, kullanıcıya bir bildirim göstermek veya gerekli işlemleri yapmak için burada kod ekleyebilirsiniz.
                Log.d(TAG, "onViewCreated: veri yok")
            }
        }


    }

    private fun navigateToAdvertDetailsFragment() {
        val advertDetailsFragment = AdvertDetailsFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, advertDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}




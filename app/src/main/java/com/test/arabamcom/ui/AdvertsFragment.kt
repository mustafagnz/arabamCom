package com.test.arabamcom.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
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
    private lateinit var toolbar: Toolbar
    private lateinit var spinner: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adverts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        toolbar = view.findViewById(R.id.toolbar)
        spinner = view.findViewById(R.id.spinner)

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

        // Fiyat sıralama seçenekleri
        val priceSortOptions = arrayOf("Sıralama","Artan Fiyat", "Azalan Fiyat", "Artan Yıl", "Azalan Yıl")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priceSortOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Seçilen sıralama seçeneğine göre RecyclerView'yi güncelleyin
                when (position) {
                    1 -> sortAscendingPrice()
                    2 -> sortDescendingPrice()
                    3 -> sortAscendingYear()
                    4 -> sortDescendingYear()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun sortAscendingPrice() {
        val sortedList = mainViewModel.post.value?.sortedBy { it.price }
        (recyclerView.adapter as PostAdapter).submitList(sortedList)
    }


    private fun sortDescendingPrice() {
        val sortedList = mainViewModel.post.value?.sortedByDescending { it.price }
        (recyclerView.adapter as PostAdapter).submitList(sortedList)
    }

    private fun sortAscendingYear(){
        val sortedList = mainViewModel.post.value?.sortedByDescending { it.date}
        (recyclerView.adapter as PostAdapter).submitList(sortedList)
    }

    private fun sortDescendingYear(){
        val sortedList = mainViewModel.post.value?.sortedByDescending { it.date }
        (recyclerView.adapter as PostAdapter).submitList(sortedList)
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




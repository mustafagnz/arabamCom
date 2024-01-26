package com.test.arabamcom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.arabamcom.R
import com.test.arabamcom.adapter.PostAdapter
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.databinding.FragmentAdvertsBinding

class AdvertsFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentAdvertsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdvertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
        observePostModelList()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val postAdapter = PostAdapter { post ->
            mainViewModel.setSelectedPost(post)
            navigateToAdvertDetailsFragment(post)
        }
        binding.recyclerView.adapter = postAdapter
    }

    private fun setupSpinner() {
        val priceSortOptions = arrayOf("Sıralama", "Artan Fiyat", "Azalan Fiyat", "Artan Yıl", "Azalan Yıl")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priceSortOptions).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
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

    private fun observePostModelList() {
        mainViewModel.post.observe(viewLifecycleOwner) { postModelList ->
            (binding.recyclerView.adapter as PostAdapter).submitList(postModelList)
        }
    }

    private fun sortAscendingPrice() {
        val sortedList = mainViewModel.post.value?.sortedBy { it.price }
        (binding.recyclerView.adapter as PostAdapter).submitList(sortedList)
    }

    private fun sortDescendingPrice() {
        val sortedList = mainViewModel.post.value?.sortedByDescending { it.price }
        (binding.recyclerView.adapter as PostAdapter).submitList(sortedList)
    }

    private fun sortAscendingYear() {
        val sortedList = mainViewModel.post.value?.sortedBy {
            it.properties.find { property -> property.name == "year" }?.value?.toIntOrNull() ?: Int.MAX_VALUE
        }
        (binding.recyclerView.adapter as PostAdapter).submitList(sortedList)
    }

    private fun sortDescendingYear() {
        val sortedList = mainViewModel.post.value?.sortedByDescending {
            it.properties.find { property -> property.name == "year" }?.value?.toIntOrNull() ?: Int.MIN_VALUE
        }
        (binding.recyclerView.adapter as PostAdapter).submitList(sortedList)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.test.arabamcom.ui.AdvertDetailTabBar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.databinding.FragmentAdvertDescriptionBinding

class AdvertDescriptionFragment : Fragment() {

    companion object {
        private const val ARG_POST_MODEL = "arg_post_model"

        fun newInstance(postModel: PostModel): AdvertDescriptionFragment {
            val fragment = AdvertDescriptionFragment()
            val args = Bundle()
            args.putSerializable(ARG_POST_MODEL, postModel)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentAdvertDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAdvertDescriptionBinding.inflate(inflater, container, false)
        val view = binding.root

        val postModel = arguments?.getSerializable(ARG_POST_MODEL) as? PostModel
        postModel?.let {
            binding.description.text = it.text
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.test.arabamcom.ui.AdvertDetailTabBar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext()
        val view = inflater.inflate(R.layout.fragment_advert_description, container, false)
        val postModel = arguments?.getSerializable(AdvertInformationFragment.ARG_POST_MODEL) as? PostModel
        Log.d("informationFragment", "postModel before let: $postModel")
        postModel?.let {
            Log.d("informationFragment", "postModel inside let: $it")
            view.findViewById<TextView>(R.id.description).text = it.text

        }

        Log.d("informationFragment", "postModel after let: $postModel")

        return view
    }


}
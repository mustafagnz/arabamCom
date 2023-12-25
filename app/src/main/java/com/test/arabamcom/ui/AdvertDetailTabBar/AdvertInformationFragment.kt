package com.test.arabamcom.ui.AdvertDetailTabBar

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel

class AdvertInformationFragment : Fragment() {

    companion object {
        const val ARG_POST_MODEL = "arg_post_model"

        fun newInstance(postModel: PostModel): AdvertInformationFragment {
            val fragment = AdvertInformationFragment()
            val args = Bundle()
            args.putSerializable(ARG_POST_MODEL, postModel)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_advert_information, container, false)
        val postModel = arguments?.getSerializable(ARG_POST_MODEL) as? PostModel

        Log.d("informationFragment", "postModel before let: $postModel")
        postModel?.let {
            Log.d("informationFragment", "postModel inside let: $it")
            view.findViewById<TextView>(R.id.textIlanNo).text = it.id.toString()
            view.findViewById<TextView>(R.id.textIlanTarihi).text = it.dateFormatted
            view.findViewById<TextView>(R.id.textMarka).text = it.category.name
            view.findViewById<TextView>(R.id.textModel).text = it.modelName
            view.findViewById<TextView>(R.id.textYil).text = it.properties.find { property -> property.name == "year" }.toString()
            view.findViewById<TextView>(R.id.textYil).text = it.properties.find { property -> property.name == "km" }.toString()
            view.findViewById<TextView>(R.id.textYil).text = it.properties.find { property -> property.name == "color" }.toString()


        }



        Log.d("informationFragment", "postModel after let: $postModel")

        return view
    }
}


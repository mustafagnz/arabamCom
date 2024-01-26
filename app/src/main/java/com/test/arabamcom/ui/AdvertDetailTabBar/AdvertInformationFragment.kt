package com.test.arabamcom.ui.AdvertDetailTabBar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.arabamcom.api.PostModel
import com.test.arabamcom.databinding.FragmentAdvertInformationBinding

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

    private var _binding: FragmentAdvertInformationBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdvertInformationBinding.inflate(inflater, container, false)
        val view = binding.root

        val postModel = arguments?.getSerializable(ARG_POST_MODEL) as? PostModel
        postModel?.let { model ->
            binding.advertNo.text = "İLAN NO: ${model.id}"
            binding.advertDate.text = "İLAN TARİHİ: ${model.dateFormatted}"
            binding.advertBrand.text = "MARKA: ${model.category.name}"
            binding.advertModel.text = "MODEL: ${model.modelName}"

            // map kullanıldı
            val propertiesMap = model.properties.associateBy { it.name }

            // Özellikleri map üzerinden almak
            binding.advertYear.text = "YIL: ${propertiesMap["year"]?.value ?: "Bilinmiyor"}"
            binding.advertKM.text = "KİLOMETRE: ${propertiesMap["km"]?.value ?: "Bilinmiyor"}"
            binding.advertColor.text = "RENK: ${propertiesMap["color"]?.value ?: "Bilinmiyor"}"
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

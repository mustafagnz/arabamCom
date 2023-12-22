package com.test.arabamcom.ui.AdvertDetailTabBar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.test.arabamcom.R
import com.test.arabamcom.api.PostModel

class AdvertInformationFragment : Fragment() {

    companion object {
        private const val ARG_POST_MODEL = "arg_post_model"

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
        // Fragment'ın bağlı olduğu Activity'nin Context'ini al
        val context = requireContext()

        // Fragment'ın layout dosyasını inflate et
        val view = inflater.inflate(R.layout.fragment_advert_information, container, false)

        // Arguments'ı kontrol et ve PostModel'i al
        val postModel = arguments?.getSerializable(ARG_POST_MODEL) as? PostModel

        // PostModel null değilse, ilgili TextView'lere bilgileri set et
        postModel?.let {
            view.findViewById<TextView>(R.id.textIlanNo).text = "İlan No: ${it.id}"
            view.findViewById<TextView>(R.id.textIlanTarihi).text = "İlan Tarihi: ${it.dateFormatted}"
            view.findViewById<TextView>(R.id.textMarka).text = "Marka: ${it.category.name}"
            view.findViewById<TextView>(R.id.textModel).text = "Model: ${it.modelName}"
            view.findViewById<TextView>(R.id.textYil).text = "Yıl: ${it.date}"
            view.findViewById<TextView>(R.id.textKilometre).text =
                "Kilometre: ${it.properties.find { prop -> prop.name == "Kilometre" }?.value ?: ""}"
            view.findViewById<TextView>(R.id.textRenk).text =
                "Renk: ${it.properties.find { prop -> prop.name == "Renk" }?.value ?: ""}"
        }

        // Oluşturulan view'i geri döndür
        return view
    }

}


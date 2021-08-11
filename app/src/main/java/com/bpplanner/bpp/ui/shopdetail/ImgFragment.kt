package com.bpplanner.bpp.ui.shopdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bpplanner.bpp.databinding.FragmentImgBinding
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bumptech.glide.Glide

class ImgFragment  : BaseFragment<FragmentImgBinding>() {
    private val img: String by lazy { requireArguments().getString(ARGUMENT_IMG)!! }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImgBinding {
        return FragmentImgBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            Glide.with(b.img)
                .load(img)
                .into(b.img)
        }
    }

    companion object {
        private const val ARGUMENT_IMG = "ARGUMENT_IMG"

        fun create(img: String): ImgFragment {
            val fragment = ImgFragment()

            fragment.arguments = Bundle().apply {
                putString(ARGUMENT_IMG, img)
            }
            return fragment
        }
    }
}
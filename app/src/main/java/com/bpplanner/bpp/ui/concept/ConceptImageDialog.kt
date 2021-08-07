package com.bpplanner.bpp.ui.concept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.DialogConceptBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.ui.common.base.BaseDialogFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity
import com.bumptech.glide.Glide

class ConceptImageDialog : BaseDialogFragment<DialogConceptBinding>() {
    private val data by lazy { requireArguments().getParcelable<ConceptData>(ARGUMENT_DATA)!! }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(ConceptViewModel::class.java)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogConceptBinding {
        return DialogConceptBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.let { b ->
            Glide.with(b.img)
                .load(data.img)
                .into(b.img)

            b.btnLike.isChecked = data.like
            b.name.text = data.shop.name
            b.layoutName.setOnClickListener {
                val intent = ShopDetailActivity.getStartIntent(requireContext(), data.shop.id)
                startActivity(intent)
            }
            b.btnLike.setOnCheckedChangeListener { view, b ->
                viewModel.setLikeConcept(data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            resources.getDimensionPixelSize(R.dimen.img_dialog_height)
        )
    }

    companion object {
        private const val ARGUMENT_DATA = "ARGUMENT_DATA"

        fun create(data: ConceptData): ConceptImageDialog {
            val dialog = ConceptImageDialog()
            dialog.arguments = Bundle().apply {
                putParcelable(ARGUMENT_DATA, data)
            }

            return dialog

        }
    }

}
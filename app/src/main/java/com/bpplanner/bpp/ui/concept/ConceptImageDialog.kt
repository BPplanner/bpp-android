package com.bpplanner.bpp.ui.concept

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.DialogConceptBinding
import com.bpplanner.bpp.ui.common.base.BaseDialogFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity
import com.bumptech.glide.Glide


class ConceptImageDialog : BaseDialogFragment<DialogConceptBinding>() {
    private val index by lazy { requireArguments().getInt(ARGUMENT_INDEX) }
    private val data by lazy { viewModel.getData(index) }
    private val viewModel by lazy {
        ViewModelProvider(requireParentFragment()).get(ConceptViewModel::class.java)
    }
    private var onDismissListener: ((Int) -> Unit)? = null

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
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
        )
    }

    fun setOnDismissListener(listener: ((Int) -> Unit)?) {
        onDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(index)
    }

    companion object {
        private const val ARGUMENT_INDEX = "ARGUMENT_INDEX"

        fun create(index: Int): ConceptImageDialog {
            val dialog = ConceptImageDialog()
            dialog.arguments = Bundle().apply {
                putInt(ARGUMENT_INDEX, index)
            }

            return dialog

        }
    }

}
package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ChipFilterBinding
import com.bpplanner.bpp.databinding.FragmentStudioFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

open class AddressFilterSheet : BottomSheetDialogFragment() {
    private var binding: FragmentStudioFilterBinding? = null
    private val viewModel by lazy { ViewModelProvider(requireParentFragment()).get(HomeViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudioFilterBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->

            for (item in viewModel.getFilterList()) {
                val chip = createChip(b.chipGroup, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            b.btn.setOnClickListener {
                val list = mutableListOf<String>()
                for (chip in b.chipGroup.children) {
                    chip as? Chip ?: continue
                    for (item in viewModel.getFilterList()) {
                        if (chip.tag == item.id) {
                            item.checked = chip.isChecked
                            break
                        }
                    }
                }
                viewModel.reset()
                dismiss()
            }

        }
    }


    private val typefaceSemiBold by lazy {
        ResourcesCompat.getFont(requireContext(), R.font.noto_sans_semi_bold)
    }
    private val typefaceRegular by lazy {
        ResourcesCompat.getFont(requireContext(), R.font.noto_sans_regular)
    }


    private fun createChip(parent: ViewGroup, text: String): Chip {
        val chip = ChipFilterBinding.inflate(layoutInflater).root
        chip.text = text
        parent.addView(chip)
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            chip.typeface = if (isChecked) typefaceSemiBold else typefaceRegular
//            chip.setTextAppearance(if (isChecked) R.style.Text_Emphasize else R.style.Text_Date)
//            chip.setTextColor(resources.getColor(if (isChecked) R.color.white else R.color.gray, null))
        }
        return chip
    }
}
package com.bpplanner.bpp.ui.concept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ChipFilterBinding
import com.bpplanner.bpp.databinding.FragmentConceptFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

open class ConceptFilterSheet : BottomSheetDialogFragment() {
    private var binding: FragmentConceptFilterBinding? = null
    private val viewModel by lazy { ViewModelProvider(requireParentFragment()).get(ConceptViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConceptFilterBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->
            val data = viewModel.conceptFilter
            for (item in data.numPerson) {
                val chip = createChip(b.chipGroupPerson, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            for (item in data.gender) {
                val chip = createChip(b.chipGroupGender, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            for (item in data.background) {
                val chip = createChip(b.chipGroupBackground, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            for (item in data.props) {
                val chip = createChip(b.chipGroupProps, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            for (item in data.cloth) {
                val chip = createChip(b.chipGroupCloth, item.value)
                chip.tag = item.id
                chip.isChecked = item.checked
            }

            b.btnReset.setOnClickListener {
                for (chip in b.chipGroupPerson.children) {
                    chip as? Chip ?: continue
                    chip.isChecked = false
                }

                for (chip in b.chipGroupGender.children) {
                    chip as? Chip ?: continue
                    chip.isChecked = false
                }

                for (chip in b.chipGroupBackground.children) {
                    chip as? Chip ?: continue
                    chip.isChecked = false
                }

                for (chip in b.chipGroupProps.children) {
                    chip as? Chip ?: continue
                    chip.isChecked = false
                }

                for (chip in b.chipGroupCloth.children) {
                    chip as? Chip ?: continue
                    chip.isChecked = false
                }
            }

            b.btn.setOnClickListener {
                for (chip in b.chipGroupPerson.children) {
                    chip as? Chip ?: continue

                    for (item in data.numPerson) {
                        if (chip.tag == item.id) {
                            item.checked = chip.isChecked
                            break
                        }
                    }
                }

                for (chip in b.chipGroupGender.children) {
                    chip as? Chip ?: continue

                    for (item in data.gender) {
                        if (chip.tag == item.id) {
                            item.checked = chip.isChecked
                            break
                        }
                    }
                }

                for (chip in b.chipGroupBackground.children) {
                    chip as? Chip ?: continue

                    for (item in data.background) {
                        if (chip.tag == item.id) {
                            item.checked = chip.isChecked
                            break
                        }
                    }
                }

                for (chip in b.chipGroupProps.children) {
                    chip as? Chip ?: continue

                    for (item in data.props) {
                        if (chip.tag == item.id) {
                            item.checked = chip.isChecked
                            break
                        }
                    }
                }

                for (chip in b.chipGroupCloth.children) {
                    chip as? Chip ?: continue

                    for (item in data.cloth) {
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
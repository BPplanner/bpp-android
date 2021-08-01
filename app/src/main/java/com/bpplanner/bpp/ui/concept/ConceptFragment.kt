package com.bpplanner.bpp.ui.concept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ChipFilterBinding
import com.bpplanner.bpp.databinding.FragmentConceptBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
import com.bpplanner.bpp.ui.common.SpacesItemDecoration
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.google.android.material.chip.Chip

class ConceptFragment : BaseFragment<FragmentConceptBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(ConceptViewModel::class.java)
    }
    private val adapter by lazy {
        ConceptListAdapter()
    }
    private val loadingAdapter by lazy {
        LoadingRecyclerViewAdapter(adapter).apply {
            setOnBindLoadingListener {
                viewModel.loadList()
            }
        }
    }
    private val filterSheet by lazy { ConceptFilterSheet() }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConceptBinding {
        return FragmentConceptBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->

            b.recyclerView.layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == adapter.itemCount && !viewModel.isFinishList()) 2
                        else 1
                    }
                }
            }

            b.recyclerView.addItemDecoration(
                SpacesItemDecoration(
                    resources.getDimension(R.dimen.item_space).toInt()
                )
            )
            b.recyclerView.adapter = loadingAdapter

            b.btnFilter.setOnClickListener {
                if (!filterSheet.isVisible)
                    filterSheet.show(childFragmentManager, null)
            }

            b.btnLike.setOnCheckedChangeListener { _, value ->
                viewModel.setLikeFilter(value)
            }

            adapter.setOnItemClick(object : ConceptListAdapter.OnItemClick {
                override fun onItemClick(position: Int, item: ConceptData) {

                }

                override fun onLikeClick(position: Int, item: ConceptData) {
                    viewModel.setLikeConcept(item)
                }

            })

            viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ApiStatus.Success -> {
                        adapter.setData(it.data)
                        loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
                        loadingAdapter.notifyDataSetChanged()
                    }
                    is ApiStatus.Error -> {
                        loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
                    }
                }
            })

            viewModel.conceptFilter.observe(viewLifecycleOwner, Observer {
                b.filterGroup.removeAllViews()

                for (item in it.numPerson) {
                    if (item.checked)
                        createChip(b.filterGroup, item.value)
                }

                for (item in it.gender) {
                    if (item.checked)
                        createChip(b.filterGroup, item.value)
                }

                for (item in it.background) {
                    if (item.checked)
                        createChip(b.filterGroup, item.value)
                }

                for (item in it.props) {
                    if (item.checked)
                        createChip(b.filterGroup, item.value)
                }

                for (item in it.cloth) {
                    if (item.checked)
                        createChip(b.filterGroup, item.value)
                }

                b.filterGroupScroll.scrollTo(0, 0)
            })
        }
    }

    private fun createChip(parent: ViewGroup, text: String): Chip {
        val chip = ChipFilterBinding.inflate(layoutInflater).root
        chip.text = text
        parent.addView(chip)
        chip.isCheckable = false
        chip.isFocusable = false
        return chip
    }

}
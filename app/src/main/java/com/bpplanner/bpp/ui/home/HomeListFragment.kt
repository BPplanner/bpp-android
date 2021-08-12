package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentHomeListBinding
import com.bpplanner.bpp.dto.ShopData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
import com.bpplanner.bpp.ui.common.SpacesItemDecoration
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity

class HomeListFragment  : BaseFragment<FragmentHomeListBinding>() {
    private val index by lazy { arguments?.getInt(ARGUMENT_INDEX) ?: 0 }
    private val viewModel by lazy {
        ViewModelProvider(this, HomeViewModel.Factory(index))
            .get(HomeViewModel::class.java)
    }
    private val adapter by lazy {
        HomeListAdapter()
    }
    private val loadingAdapter by lazy {
        LoadingRecyclerViewAdapter(adapter).apply {
            setOnBindLoadingListener {
                viewModel.loadList()
            }
        }
    }

    private val bottomSheetFilter by lazy { AddressFilterSheet() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeListBinding {
        return FragmentHomeListBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            loadingAdapter.itemCount - 1 -> {
                                if (viewModel.isFinishList()) 1
                                else 2
                            }
                            else -> 1
                        }
                    }
                }
            }
            b.recyclerView.addItemDecoration(
                SpacesItemDecoration(
                    resources.getDimension(R.dimen.item_concept_space_horizontal).toInt(),
                    resources.getDimension(R.dimen.item_concept_space_vertical).toInt(),
                    2
                )
            )
            b.recyclerView.adapter = loadingAdapter

            b.btnLike.setOnCheckedChangeListener { _
                                                   , isChecked ->
                viewModel.setLikeFilter(isChecked)
            }

            b.btnFilter.setOnClickListener {
                if (!bottomSheetFilter.isVisible)
                    bottomSheetFilter.show(childFragmentManager, null)
            }

        }

        adapter.setOnItemClick(object : HomeListAdapter.OnItemClick {

            override fun onItemClickListener(position: Int, data: ShopData) {
                val intent = ShopDetailActivity.getStartIntent(requireContext(), data.id)
                startActivity(intent)
            }

            override fun onLikeClickListener(position: Int, data: ShopData, value: Boolean) {
                viewModel.setLike(data, value)
            }

        })

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data!!)
                    loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
                    loadingAdapter.notifyDataSetChanged()
                }
                is ApiStatus.Error -> {
                    loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
                }
            }
        })
    }

    companion object {
        private const val ARGUMENT_INDEX = "ARGUMENT_INDEX"

        fun create(category: Int): HomeListFragment {
            val fragment = HomeListFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARGUMENT_INDEX, category)
            }
            return fragment
        }
    }
}
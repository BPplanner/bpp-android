package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
import com.bpplanner.bpp.ui.common.SpacesItemDecoration
import com.bpplanner.bpp.ui.common.base.BaseFragment

class HomeListFragment private constructor() : BaseFragment<RecyclerviewBinding>() {
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
    ): RecyclerviewBinding {
        return RecyclerviewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.recyclerview.layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0 -> 2
                            adapter.itemCount -> {
                                if (viewModel.isFinishList()) 1
                                else 2
                            }
                            else -> 1
                        }
                    }
                }
            }
            it.recyclerview.addItemDecoration(
                SpacesItemDecoration(
                    resources.getDimension(R.dimen.item_space).toInt()
                )
            )
            it.recyclerview.adapter = loadingAdapter
        }

        adapter.setOnHeaderItemClick(object : HomeListAdapter.OnHeaderItemClick {
            override fun onLikeClick(value: Boolean) {
                viewModel.setLikeFilter(value)
            }

            override fun onFilterClick() {
                bottomSheetFilter.show(childFragmentManager, null)
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
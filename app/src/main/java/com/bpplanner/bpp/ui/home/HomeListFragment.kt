package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
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

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): RecyclerviewBinding {
        return RecyclerviewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.recyclerview.layoutManager = LinearLayoutManager(context)
            it.recyclerview.adapter = loadingAdapter
        }

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                    loadingAdapter.setVisibleLoading(!viewModel.isFinishLoading())
                    loadingAdapter.notifyDataSetChanged()
                }
                is ApiStatus.Error -> {

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
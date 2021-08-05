package com.bpplanner.bpp.ui.shopdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentShopDetailConceptBinding
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
import com.bpplanner.bpp.ui.common.SpacesItemDecoration
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.concept.ConceptListAdapter

class PortfolioListFragment : BaseFragment<FragmentShopDetailConceptBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(ShopDetailViewModel::class.java)
    }
    private val adapter by lazy { ConceptListAdapter( false) }
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
    ): FragmentShopDetailConceptBinding {
        return FragmentShopDetailConceptBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager = GridLayoutManager(context, 3).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == adapter.itemCount && !viewModel.isFinishList()) 3
                        else 1
                    }
                }
            }

            b.recyclerView.addItemDecoration(
                SpacesItemDecoration(
                    resources.getDimension(R.dimen.item_detail_concept_space).toInt()
                )
            )
            b.recyclerView.adapter = loadingAdapter

            adapter.setOnItemClick(object: ConceptListAdapter.OnItemClick{
                override fun onItemClick(position: Int, item: ConceptData) {
                    val intent = ImgListActivity.getStartIntent(requireContext(), item.img)
                    startActivity(intent)
                }

                override fun onLikeClick(position: Int, item: ConceptData) {

                }
            })
        }


        viewModel.conceptListLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                    loadingAdapter.setVisibleLoading(!viewModel.isFinishList())
                    loadingAdapter.notifyDataSetChanged()
                }
            }
        })
    }

}
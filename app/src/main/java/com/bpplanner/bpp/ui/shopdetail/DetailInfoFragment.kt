package com.bpplanner.bpp.ui.shopdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.FragmentShopDetailInfoBinding
import com.bpplanner.bpp.databinding.ItemPartnershipBinding
import com.bpplanner.bpp.dto.PartnershipData
import com.bpplanner.bpp.dto.ShopDetailData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bumptech.glide.Glide

class DetailInfoFragment : BaseFragment<FragmentShopDetailInfoBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(ShopDetailViewModel::class.java)
    }

    private val adapter by lazy { PartnershipAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopDetailInfoBinding {
        return FragmentShopDetailInfoBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            b.recyclerView.adapter = adapter
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    bind(it.data)
                }
            }
        })
    }

    private fun bind(data: ShopDetailData) {
        val b = binding ?: return

        Glide.with(b.ivLocation)
            .load(data.mapImg)
            .into(b.ivLocation)
        b.tvLocation.text = data.address

        b.ivLocation.setOnClickListener {
            val intent = ImgListActivity.getStartIntent(requireContext(), data.mapImg)
            startActivity(intent)
        }

        if (data.priceImg == null) {
            b.tvPriceLabel.isVisible = false
            b.ivPrice.isVisible = false
        } else {
            b.tvPriceLabel.isVisible = true
            b.ivPrice.isVisible = true
            Glide.with(b.ivPrice)
                .load(data.priceImg)
                .into(b.ivPrice)
            b.ivPrice.setOnClickListener {
                val intent = ImgListActivity.getStartIntent(requireContext(), data.priceImg)
                startActivity(intent)
            }
        }

        if (data.partnershipList.isNullOrEmpty()){
            b.tvPartnershipLabel.isVisible = false
            b.recyclerView.isVisible = false
        }else{
            b.tvPartnershipLabel.isVisible = true
            b.recyclerView.isVisible = true
            adapter.setList(data.partnershipList)
        }

    }

    private inner class PartnershipAdapter :
        RecyclerView.Adapter<PartnershipAdapter.PartnershipViewHolder>() {
        private var list: List<PartnershipData>? = null
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PartnershipViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPartnershipBinding.inflate(inflater, parent, false)
            return PartnershipViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PartnershipViewHolder, position: Int) {
            val data = list!![position]

            Glide.with(holder.binding.img)
                .load(data.img)
                .into(holder.binding.img)
            holder.binding.name.text = data.name
        }

        override fun getItemCount(): Int {
            return list?.size ?: 0
        }

        fun setList(list: List<PartnershipData>?) {
            this.list = list
        }

        private inner class PartnershipViewHolder(val binding: ItemPartnershipBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    val data = list!![adapterPosition]
                    val intent = ShopDetailActivity.getStartIntent(requireContext(), data.id)
                    startActivity(intent)
                }
            }
        }
    }
}
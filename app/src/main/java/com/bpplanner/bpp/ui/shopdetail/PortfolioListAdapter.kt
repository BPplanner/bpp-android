package com.bpplanner.bpp.ui.shopdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemPortfolioBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bumptech.glide.Glide

class PortfolioListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ConceptData>? = null

    private var onItemClick: OnItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemPortfolioBinding.inflate(inflater, parent, false)
        return PortfolioViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TYPE_HEADER
        return ITEM_TYPE_SHOP
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PortfolioViewHolder -> holder.bind(getItem(position))
        }
    }

    fun setData(list: List<ConceptData>) {
        this.list = list
    }

    fun setOnItemClick(listener: OnItemClick) {
        onItemClick = listener
    }

    private fun getItem(position: Int): ConceptData {
        return list!![position]
    }

    interface OnItemClick {
        fun onItemClick(position: Int, item: ConceptData)
    }

    inner class PortfolioViewHolder(val binding: ItemPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                onItemClick?.onItemClick(position, getItem(position))
            }
        }

        fun bind(data: ConceptData) {
            Glide.with(binding.img)
                .load(data.img)
                .into(binding.img)
        }
    }

    companion object {
        const val ITEM_TYPE_SHOP = 0
        const val ITEM_TYPE_HEADER = 1
    }
}
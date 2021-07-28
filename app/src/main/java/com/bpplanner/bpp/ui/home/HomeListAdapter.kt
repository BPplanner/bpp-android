package com.bpplanner.bpp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemHomeHeaderBinding
import com.bpplanner.bpp.databinding.ItemHomeShopBinding
import com.bpplanner.bpp.dto.ShopList

class HomeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ShopList>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM_TYPE_HEADER -> {
                val binding = ItemHomeHeaderBinding.inflate(inflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }

        val binding = ItemHomeShopBinding.inflate(inflater, parent, false)
        return ShopViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TYPE_HEADER
        return ITEM_TYPE_SHOP
    }

    override fun getItemCount(): Int {
        if (list == null) return 0
        if (list!!.isEmpty()) return 1
        return list!!.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.binding.run {

            }
            is ShopViewHolder -> holder.binding.run {

            }
        }
    }

    fun setData(list: List<ShopList>) {
        this.list = list
    }

    private fun getItem(position: Int): ShopList {
        return list!![position]
    }


    inner class HeaderViewHolder(val binding: ItemHomeHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    inner class ShopViewHolder(val binding: ItemHomeShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    companion object {
        const val ITEM_TYPE_SHOP = 0
        const val ITEM_TYPE_HEADER = 1
    }
}
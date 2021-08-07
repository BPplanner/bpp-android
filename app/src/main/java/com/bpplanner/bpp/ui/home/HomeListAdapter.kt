package com.bpplanner.bpp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemHomeHeaderBinding
import com.bpplanner.bpp.databinding.ItemHomeShopBinding
import com.bpplanner.bpp.dto.ShopData
import com.bumptech.glide.Glide

class HomeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ShopData>? = null

    private var onItemClick: OnItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemHomeShopBinding.inflate(inflater, parent, false)
        return ShopViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ShopViewHolder -> holder.bind(getItem(position))
        }
    }

    fun setData(list: List<ShopData>) {
        this.list = list
    }

    fun setOnItemClick(listener: OnItemClick){
        onItemClick = listener
    }

    private fun getItem(position: Int): ShopData {
        return list!![position]
    }


    interface OnItemClick{
        fun onItemClickListener(position: Int, data: ShopData)
        fun onLikeClickListener(position: Int, data: ShopData, value: Boolean)
    }

    inner class ShopViewHolder(val binding: ItemHomeShopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.onItemClickListener(adapterPosition, getItem(adapterPosition))
            }
            binding.like.setOnClickListener {
                onItemClick?.onLikeClickListener(adapterPosition, getItem(adapterPosition), binding.like.isChecked)
            }
        }

        fun bind(data: ShopData){
            Glide.with(binding.img)
                .load(data.profileImg)
                .into(binding.img)

            binding.name.text = data.name
            binding.address.text = data.address
            binding.price.text = "${data.minPrice} ~"
            binding.like.isChecked = data.like
        }
    }

    companion object {
        const val ITEM_TYPE_SHOP = 0
    }
}
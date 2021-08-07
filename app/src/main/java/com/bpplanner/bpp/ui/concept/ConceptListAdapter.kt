package com.bpplanner.bpp.ui.concept

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemConceptBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bumptech.glide.Glide

class ConceptListAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ConceptData>? = null

    private var onItemClick: OnItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemConceptBinding.inflate(inflater, parent, false)
        return ConceptViewHolder(binding)
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
            is ConceptViewHolder -> holder.bind(getItem(position))
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
        fun onLikeClick(position: Int, item: ConceptData)
    }

    inner class ConceptViewHolder(val binding: ItemConceptBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.like.setOnClickListener {
                val position = adapterPosition
                onItemClick?.onLikeClick(position, getItem(position))
            }
            binding.root.setOnClickListener {
                val position = adapterPosition
                onItemClick?.onItemClick(position, getItem(position))
            }
        }

        fun bind(data: ConceptData) {
            Glide.with(binding.img)
                .load(data.img)
                .override(700, 700)
                .into(binding.img)

            binding.like.isChecked = data.like
        }
    }

    companion object {
        const val ITEM_TYPE_SHOP = 0
        const val ITEM_TYPE_HEADER = 1
    }
}
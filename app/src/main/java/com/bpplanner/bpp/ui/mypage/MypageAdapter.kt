package com.bpplanner.bpp.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ItemInquiringBinding
import com.bpplanner.bpp.databinding.ItemTitleBinding
import com.bpplanner.bpp.dto.ConceptData
import com.bpplanner.bpp.dto.InquiringList
import com.bpplanner.bpp.dto.MypageData
import com.bumptech.glide.Glide

class MypageAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: InquiringList? = null
    private var onItemClick: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM_TYPE_TITLE -> {
                val binding = ItemTitleBinding.inflate(inflater, parent, false)
                return TitleViewHolder(binding)
            }
        }

        val binding = ItemInquiringBinding.inflate(inflater, parent, false)
        return InquiringViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val data = this.data ?: return 0

        val cnt = if (data.studioList.isEmpty()) 0 else data.studioList.size + 1

        if (cnt > 0 && position == 0)
            return ITEM_TYPE_TITLE
        else if (data.beautyList.isNotEmpty() && position == cnt)
            return ITEM_TYPE_TITLE

        return ITEM_TYPE_INQUIRE
    }

    override fun getItemCount(): Int {
        val data = this.data ?: return 0

        var cnt = data.studioList.size
        if (cnt > 0) cnt++

        cnt += data.beautyList.size
        if (data.beautyList.isNotEmpty()) cnt++

        return cnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is TitleViewHolder -> {
                val context = holder.binding.root.context
                holder.bind(
                    if (position == 0) context.getString(R.string.all_studio)
                    else context.getString(R.string.all_makeup)
                )
            }
            is InquiringViewHolder -> holder.bind(getItem(position))
        }
    }

    fun setData(data: InquiringList) {
        this.data = data
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }


    private fun getItem(position: Int): MypageData {
        val data = this.data!!

        val cnt = if (data.studioList.isEmpty()) 0 else data.studioList.size + 1
        if (cnt > 0 && position < data.studioList.size + 1) {
            return data.studioList[position - 1]
        }

        return data.beautyList[position - cnt - 1]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: MypageData)

        fun onItemCancelClick(position: Int, item: MypageData)

        fun onItemConfirmClick(position: Int, item: MypageData)
    }

    inner class InquiringViewHolder(val binding: ItemInquiringBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.layoutName.setOnClickListener {
                onItemClick?.onItemClick(adapterPosition, getItem(adapterPosition))
            }

            binding.cancel.setOnClickListener {
                onItemClick?.onItemCancelClick(adapterPosition, getItem(adapterPosition))
            }

            binding.confirm.setOnClickListener {
                onItemClick?.onItemConfirmClick(adapterPosition, getItem(adapterPosition))
            }
        }

        fun bind(item: MypageData) {
            Glide.with(binding.logo)
                .load(item.shop.logo)
                .circleCrop()
                .into(binding.logo)

            binding.tvName.text = item.shop.name
        }
    }

    inner class TitleViewHolder(val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title.text = title
        }
    }

    companion object {
        const val ITEM_TYPE_TITLE = 0
        const val ITEM_TYPE_INQUIRE = 1
    }
}
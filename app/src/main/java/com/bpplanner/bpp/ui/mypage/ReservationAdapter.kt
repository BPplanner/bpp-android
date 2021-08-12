package com.bpplanner.bpp.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.*
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.dto.ReservationList
import com.bumptech.glide.Glide

class ReservationAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ReservationList? = null
    private var onItemClick: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM_TYPE_TITLE -> {
                val binding = ItemTitleBinding.inflate(inflater, parent, false)
                return TitleViewHolder(binding)
            }
            ITEM_TYPE_REMAIN -> {
                val binding = ItemRemainDayBinding.inflate(inflater, parent, false)
                return RemainDayViewHolder(binding)
            }
            ITEM_TYPE_RESERVATION -> {
                val binding = ItemReservationConfirmBinding.inflate(inflater, parent, false)
                return ReservationViewHolder(binding)
            }
            ITEM_TYPE_EXPIRE -> {
                val binding = ItemReservationExpireBinding.inflate(inflater, parent, false)
                return ExpireViewHolder(binding)
            }
            else -> throw Exception()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = this.data ?: return 0

        if (position == 0 && data.remainDay != null) return ITEM_TYPE_REMAIN

        val cnt = (if (data.remainDay == null) 0 else 1) + data.reservationList.size
        if (position < cnt)
            return ITEM_TYPE_RESERVATION
        else if (position == cnt)
            return ITEM_TYPE_TITLE

        return ITEM_TYPE_EXPIRE
    }

    override fun getItemCount(): Int {
        val data = this.data ?: return 0

        var cnt = if (data.remainDay == null) 0 else 1
        cnt += data.reservationList.size

        if (data.expireList.isNotEmpty()) {
            cnt += data.expireList.size + 1
        }

        return cnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is TitleViewHolder -> holder.bind("이전 예약 내역")
            is RemainDayViewHolder -> holder.bind(data!!.remainDay!!)
            is ReservationViewHolder -> holder.bind(getItem(position))
            is ExpireViewHolder -> holder.bind(getItem(position))
        }
    }

    fun setData(data: ReservationList) {
        this.data = data
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }


    private fun getItem(position: Int): MypageData {
        val data = this.data!!

        var mPosition = position - if (data.remainDay == null) 0 else 1
        if (mPosition < data.reservationList.size) {
            return data.reservationList[mPosition]
        }

        mPosition -= data.reservationList.size + 1

        return data.expireList[mPosition]
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: MypageData)

        fun onItemInquireClick(position: Int, item: MypageData)
    }

    inner class ReservationViewHolder(val binding: ItemReservationConfirmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.layoutContent.setOnClickListener {
                onItemClick?.onItemClick(adapterPosition, getItem(adapterPosition))
            }

            binding.inquire.setOnClickListener {
                onItemClick?.onItemInquireClick(adapterPosition, getItem(adapterPosition))
            }
        }

        fun bind(item: MypageData) {
            val context = binding.root.context

            Glide.with(binding.logo)
                .load(item.shop.logo)
                .circleCrop()
                .into(binding.logo)

            binding.tvType.text =
                if (item.shop.type == 0) context.getString(R.string.all_studio) else context.getString(R.string.all_makeup)
            binding.tvName.text = item.shop.name
            binding.tvDate.text = item.reservedDate
        }
    }

    inner class ExpireViewHolder(val binding: ItemReservationExpireBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick?.onItemClick(adapterPosition, getItem(adapterPosition))
            }
        }

        fun bind(item: MypageData) {
            Glide.with(binding.logo)
                .load(item.shop.logo)
                .circleCrop()
                .into(binding.logo)

            binding.tvName.text = item.shop.name
            binding.tvDate.text = item.reservedDate
        }
    }

    inner class TitleViewHolder(val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title.text = title
        }
    }

    inner class RemainDayViewHolder(val binding: ItemRemainDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Int) {
            binding.day.text = day.toString()
        }
    }


    companion object {
        const val ITEM_TYPE_TITLE = 0
        const val ITEM_TYPE_REMAIN = 1
        const val ITEM_TYPE_RESERVATION = 2
        const val ITEM_TYPE_EXPIRE = 3
    }
}
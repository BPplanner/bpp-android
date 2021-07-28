package com.doubleslas.fifith.alcohol.ui.recommend

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ItemRecommendEmptyBinding
import com.doubleslas.fifith.alcohol.databinding.ItemSortRecommendBinding
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.sort.SortBottomSheetDialog
import com.doubleslas.fifith.alcohol.sort.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.common.AlcoholListAdapter

class RecommendAlcoholListAdapter : AlcoholListAdapter() {
    private var sortType: RecommendSortType? = null
    private var onSortChangeListener: ((RecommendSortType) -> Unit)? = null
    private val sortDialog by lazy {
        SortBottomSheetDialog(RecommendSortType.values()).apply {
            setOnSortSelectListener {
                sortType = it
                onSortChangeListener?.invoke(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM_TYPE_SORT -> {
                val binding = ItemSortRecommendBinding.inflate(inflater, parent, false)
                return SortViewHolder(binding)
            }
            ITEM_TYPE_INFO -> {
                val binding = ItemRecommendEmptyBinding.inflate(inflater, parent, false)
                return EmptyViewHolder(binding)
            }
        }

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if(list!!.isEmpty()){
            return ITEM_TYPE_INFO
        }
        if (position == 0) return ITEM_TYPE_SORT
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        if(list == null) return 0
        if(list!!.isEmpty()) return 1
        return list!!.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SortViewHolder -> holder.binding.run {
                tvSort.text = sortType?.text ?: RecommendSortType.Recommend.text
            }
            else -> {
                super.onBindViewHolder(holder, position)

                val rank = getRank(position)

                if (holder is AlcoholViewHolder) {
                    holder.binding.let { b ->
                        if (rank == -1) {
                            b.ivAlcohol.background = null
                            b.tvRank.visibility = View.GONE
                        } else {
                            b.ivAlcohol.background =
                                getRankBackground(rank, holder.binding.root.resources)
                            b.tvRank.visibility = View.VISIBLE
                            b.tvRank.setBackgroundResource(
                                when (rank) {
                                    0 -> R.drawable.ic_1st
                                    1 -> R.drawable.ic_2nd
                                    2 -> R.drawable.ic_3rd
                                    else -> R.drawable.ic_etc
                                }
                            )
                            b.tvRank.setTextColor(if (rank < 3) Color.WHITE else Color.BLACK)
                            b.tvRank.text = (rank + 1).toString()
                        }
                    }
                }
            }
        }
    }

    override fun getItem(position: Int): AlcoholSimpleData {
        return super.getItem(position - 1)
    }

    fun setSort(sortType: RecommendSortType) {
        this.sortType = sortType
        if (itemCount > 0) notifyItemChanged(0)
    }

    fun setOnSortChangeListener(listener: ((RecommendSortType) -> Unit)?) {
        onSortChangeListener = listener
    }


    private fun getRank(position: Int): Int {
        return position - 1
    }


    private fun getRankBackground(rank: Int, resources: Resources): Drawable? {
        val id = when (rank) {
            0 -> R.drawable.bg_1st
            1 -> R.drawable.bg_2nd
            2 -> R.drawable.bg_3rd
            else -> R.drawable.bg_item_alcohol
        } ?: return null

        return ResourcesCompat.getDrawable(resources, id, null)
    }

    private fun openRecommendInfoInputActivity(context: Context){
        val intent = Intent(context, RecommendInfoActivity::class.java)
        context.startActivity(intent)
    }

    inner class SortViewHolder(val binding: ItemSortRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutSort.setOnClickListener {
                val activity = it.context as AppCompatActivity
                sortDialog.show(
                    activity.supportFragmentManager,
                    sortType ?: RecommendSortType.Recommend
                )
            }

            binding.layoutRecommend.setOnClickListener {
                openRecommendInfoInputActivity(it.context)
            }
        }
    }

    inner class EmptyViewHolder(val binding: ItemRecommendEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRecommend.setOnClickListener {
                openRecommendInfoInputActivity(it.context)
            }
        }
    }

    companion object {
        private const val ITEM_TYPE_SORT = 1
        private const val ITEM_TYPE_INFO = 2
    }
}
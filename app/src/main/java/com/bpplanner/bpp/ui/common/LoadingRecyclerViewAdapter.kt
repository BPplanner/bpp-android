package com.bpplanner.bpp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemLoadingBinding

class LoadingRecyclerViewAdapter<A : RecyclerView.Adapter<RecyclerView.ViewHolder>>(val adapter: A) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onBindLoadingListener: (() -> Unit)? = null
    private var loadingVisible = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemLoadingBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }
            else -> adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + if (loadingVisible) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                onBindLoadingListener?.invoke()
            }
            else -> adapter.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (loadingVisible && position == itemCount - 1) return VIEW_TYPE_LOADING
        return adapter.getItemViewType(position)
    }

    fun setVisibleLoading(visible: Boolean) {
        loadingVisible = visible
    }

    fun setOnBindLoadingListener(listener: (() -> Unit)?) {
        onBindLoadingListener = listener
    }

    private class LoadingViewHolder(binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val VIEW_TYPE_LOADING = -1000
    }
}
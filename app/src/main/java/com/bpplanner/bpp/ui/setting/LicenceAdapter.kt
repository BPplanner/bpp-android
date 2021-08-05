package com.bpplanner.bpp.ui.setting

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bpplanner.bpp.databinding.ItemLicenceBinding

class LicenceAdapter : RecyclerView.Adapter<LicenceAdapter.LicenceViewHolder>() {
    private val list = LicenceData.list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = ItemLicenceBinding.inflate(inflater, parent, false)
        return LicenceViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LicenceViewHolder, position: Int) {
        val item = list[position]

        holder.binding.let { b ->
            b.title.text = item.name
            b.link.text = item.link
            b.link.paintFlags = b.link.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            b.licence.text = item.licence
        }
    }


    inner class LicenceViewHolder(val binding: ItemLicenceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.link.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(list[adapterPosition].link)
                startActivity(binding.root.context, i, null)
            }
        }
    }
}
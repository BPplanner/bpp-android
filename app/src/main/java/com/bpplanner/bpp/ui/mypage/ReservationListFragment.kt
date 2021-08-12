package com.bpplanner.bpp.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentReservationBinding
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity

class ReservationListFragment : BaseFragment<FragmentReservationBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(requireParentFragment()).get(MypageViewModel::class.java)
    }

    private val adapter by lazy { ReservationAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReservationBinding {
        return FragmentReservationBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager = LinearLayoutManager(context)

            b.recyclerView.adapter = adapter
        }

        adapter.setOnItemClickListener(object : ReservationAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: MypageData) {
                val intent = ShopDetailActivity.getStartIntent(context!!, item.shop.id)
                startActivity(intent)
            }

            override fun onItemInquireClick(position: Int, item: MypageData) {
                val uri: Uri = Uri.parse(item.shop.kakaoUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    fun loadList() {
        viewModel.getReservation().observe(viewLifecycleOwner, {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data!!)
                    adapter.notifyDataSetChanged()
                    binding?.layoutNone?.isVisible = adapter.itemCount == 0
                }
                is ApiStatus.Error -> {

                }
            }
        })

    }
}
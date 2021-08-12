package com.bpplanner.bpp.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentInquireBinding
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.MainActivity
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity

class InquiringListFragment : BaseFragment<FragmentInquireBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(requireParentFragment()).get(MypageViewModel::class.java)
    }

    private val adapter by lazy { InquiringAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInquireBinding {
        return FragmentInquireBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager = LinearLayoutManager(context)

            b.recyclerView.adapter = adapter

            b.btnNone.setOnClickListener {
                (activity as MainActivity).gotoMain()
            }
        }

        adapter.setOnItemClickListener(object : InquiringAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: MypageData) {
                val intent = ShopDetailActivity.getStartIntent(context!!, item.shop.id)
                startActivity(intent)
            }

            override fun onItemCancelClick(position: Int, item: MypageData) {
                viewModel.cancelInquiring(item).observe(viewLifecycleOwner, {
                    when (it) {
                        is ApiStatus.Success -> {
                            loadList()
                        }
                        is ApiStatus.Error -> {
                            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }

            override fun onItemConfirmClick(position: Int, item: MypageData) {
                DatePickerDialog()
                    .setOnConfirmClickListener { date ->
                        viewModel.confirmReservation(item, date).observe(viewLifecycleOwner, {
                            when (it) {
                                is ApiStatus.Success -> {
                                    loadList()
                                    OkDialog().show(childFragmentManager, null)
                                }
                                is ApiStatus.Error -> {
                                    Toast.makeText(
                                        context,
                                        R.string.networ_error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                    }
                    .show(childFragmentManager, null)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    fun loadList() {
        viewModel.getInquiringList().observe(viewLifecycleOwner, {
            when (it) {
                is ApiStatus.Success -> {
                    val data = it.data ?: return@observe
                    adapter.setData(data)
                    adapter.notifyDataSetChanged()

                    binding?.layoutNone?.isVisible = adapter.itemCount == 0

                }
                is ApiStatus.Error -> {

                }
            }
        })

    }
}
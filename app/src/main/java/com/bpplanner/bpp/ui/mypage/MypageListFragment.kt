package com.bpplanner.bpp.ui.mypage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.DialogDatepickerBinding
import com.bpplanner.bpp.databinding.RecyclerviewBinding
import com.bpplanner.bpp.dto.MypageData
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.shopdetail.ShopDetailActivity
import kotlinx.android.synthetic.main.dialog_datepicker.*

class MypageListFragment : BaseFragment<RecyclerviewBinding>() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MypageViewModel::class.java)
    }

    private val adapter by lazy { MypageAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): RecyclerviewBinding {
        return RecyclerviewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.recyclerView.layoutManager = LinearLayoutManager(context)

            b.recyclerView.adapter = adapter
        }

        adapter.setOnItemClickListener(object : MypageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: MypageData) {
                val intent = ShopDetailActivity.getStartIntent(context!!, item.shop.id)
                startActivity(intent)
            }

            override fun onItemCancelClick(position: Int, item: MypageData) {
                viewModel.cancelInquiring(item).observe(viewLifecycleOwner, {
                    when(it){
                        is ApiStatus.Success -> {
                            loadList()
                        }
                        is ApiStatus.Error -> {
                            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

            override fun onItemConfirmClick(position: Int, item: MypageData) {
                DatePickerDialog()
                    .setOnConfirmClickListener { date ->
                        viewModel.confirmReservation(item, date).observe(viewLifecycleOwner, {
                            when(it){
                                is ApiStatus.Success -> {
                                    loadList()
                                }
                                is ApiStatus.Error -> {
                                    Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show()
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

    fun loadList(){
        viewModel.getInquiringList().observe(viewLifecycleOwner, {
            when (it) {
                is ApiStatus.Success -> {
                    adapter.setData(it.data)
                    adapter.notifyDataSetChanged()
                }
                is ApiStatus.Error -> {

                }
            }
        })

    }
}
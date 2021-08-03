package com.bpplanner.bpp.ui.shopdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityShopDetailBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class ShopDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityShopDetailBinding
    private val viewModel by lazy {
        ViewModelProvider(this, ShopDetailViewModel.Factory(intent.getIntExtra(EXTRA_ID, 0)))
            .get(ShopDetailViewModel::class.java)
    }
    private val adapter by lazy { DetailViewPagerAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                1 -> getString(R.string.all_detail_info)
                else -> getString(R.string.all_portfolio)
            }
        }.attach()

        viewModel.getDetailData().observe(this, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val data = it.data
                    Glide.with(binding.logo)
                        .load(data.logo)
                        .into(binding.logo)

                    binding.tvName.text = data.name
                    binding.tvPrice.text = "${data.minPrice} ~"
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    inner class DetailViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> DetailInfoFragment()
                else -> PortfolioListFragment()
            }
        }
    }

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"

        fun getStartIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}
package com.bpplanner.bpp.ui.shopdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityShopDetailBinding
import com.bpplanner.bpp.databinding.FragmentImgBinding
import com.bpplanner.bpp.databinding.ItemLoadingBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.LoadingRecyclerViewAdapter
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator


class ShopDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityShopDetailBinding
    private val viewModel by lazy {
        ViewModelProvider(this, ShopDetailViewModel.Factory(intent.getIntExtra(EXTRA_ID, 0)))
            .get(ShopDetailViewModel::class.java)
    }
    private val infoAdapter by lazy { DetailViewPagerAdapter() }
    private val bannerAdapter by lazy { BannerAdapter() }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appbar.setExpanded(true, true)

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = infoAdapter

        binding.banner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.banner.adapter = bannerAdapter

        TabLayoutMediator(
            binding.bannerIndicator,
            binding.banner
        ) { tab, position ->
            binding.banner.currentItem = tab.position
        }.attach()

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

                    bannerAdapter.notifyDataSetChanged()
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }


    inner class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FragmentImgBinding.inflate(inflater, parent, false)
            return BannerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
            Glide.with(holder.binding.img)
                .load(viewModel.getBannerList()[position])
                .into(holder.binding.img)
        }

        override fun getItemCount(): Int {
            return viewModel.getBannerList().size
        }


        inner class BannerViewHolder(val binding: FragmentImgBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    val intent = ImgListActivity.getStartIntent(this@ShopDetailActivity, viewModel.getBannerList(), adapterPosition)
                    startActivity(intent)
                }
            }
        }
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
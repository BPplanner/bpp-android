package com.bpplanner.bpp.ui.shopdetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityShopDetailBinding
import com.bpplanner.bpp.databinding.FragmentImgBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ShopDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityShopDetailBinding
    private val viewModel by lazy {
        ViewModelProvider(this, ShopDetailViewModel.Factory(intent.getIntExtra(EXTRA_ID, 0)))
            .get(ShopDetailViewModel::class.java)
    }
    private val infoAdapter by lazy { DetailViewPagerAdapter() }
    private val bannerAdapter by lazy { BannerAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.setDisplayShowTitleEnabled(false)
            it.elevation = 0f
        }

        binding.appbar.setExpanded(true, true)
        binding.appbar.outlineProvider = null

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = infoAdapter

        binding.banner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.banner.adapter = bannerAdapter

        binding.btnLike.setOnCheckedChangeListener { view, b ->
            viewModel.setLike(b)
        }

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

        (binding.tabLayout.getTabAt(0)?.view?.getChildAt(1) as? TextView)
            ?.setTextAppearance(R.style.Text_Emphasize)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = tab.view.getChildAt(1) as? TextView
                textView?.setTextAppearance(R.style.Text_Emphasize)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = tab.view.getChildAt(1) as? TextView
                textView?.setTextAppearance(R.style.Text_Basic)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val value = -255 * verticalOffset / appBarLayout.totalScrollRange
            binding.toolbar.background.alpha = value

            val hex = "%02x".format(255 - value)

            val reverseValue = 255 - value
            val dynamicThumbColor = Color.argb(255, reverseValue, reverseValue, reverseValue)


            val upArrow = ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
            upArrow!!.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                dynamicThumbColor,
                BlendModeCompat.SRC_IN
            )
//            upArrow!!.setColorFilter(Color.parseColor("#$hex$hex$hex"), PorterDuff.Mode.ADD)
            supportActionBar?.setHomeAsUpIndicator(upArrow)

        })

        viewModel.getDetailData().observe(this, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val data = it.data
                    Glide.with(binding.logo)
                        .load(data.logo)
                        .apply(RequestOptions().circleCrop())
                        .into(binding.logo)

                    binding.tvName.text = data.name
                    if (data.minPrice == null) {
                        binding.tvPrice.isVisible = false
                    } else {
                        binding.tvPrice.isVisible = true
                        binding.tvPrice.text = "${data.minPrice} ~"
                    }

                    binding.btnLike.isChecked = data.like

                    binding.btnCall.setOnClickListener {
                        val uri: Uri = Uri.parse(data.kakaoUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }

                    bannerAdapter.notifyDataSetChanged()
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
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
                    val intent = ImgListActivity.getStartIntent(
                        this@ShopDetailActivity,
                        viewModel.getBannerList(),
                        adapterPosition
                    )
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

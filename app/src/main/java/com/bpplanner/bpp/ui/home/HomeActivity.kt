package com.bpplanner.bpp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityHomeBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val adapter by lazy { ShopViewPagerAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                1 -> getString(R.string.all_makeup)
                else -> getString(R.string.all_studio)
            }
        }.attach()

    }


    inner class ShopViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return HomeListFragment.create(position)
        }
    }
}
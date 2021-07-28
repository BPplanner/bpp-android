package com.bpplanner.bpp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityHomeBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.google.android.material.tabs.TabLayout

class HomeActivity : BaseActivity(){

    private lateinit var binding: ActivityHomeBinding
    private val adapter by lazy { ShopViewPagerAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(R.string.all_studio)
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(R.string.all_makeup)
        )

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = adapter
    }


    inner class ShopViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return binding.tabLayout.tabCount
        }

        override fun createFragment(position: Int): Fragment {
            return HomeListFragment.create(position)
        }
    }
}
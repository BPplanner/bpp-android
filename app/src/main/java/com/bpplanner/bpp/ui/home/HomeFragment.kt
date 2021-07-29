package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentHomeBinding
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val adapter by lazy { ShopViewPagerAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = when (position) {
                    1 -> getString(R.string.all_makeup)
                    else -> getString(R.string.all_studio)
                }
            }.attach()
        }
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
package com.bpplanner.bpp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentHomeBinding
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.utils.LogUtil
import com.google.android.material.tabs.TabLayout
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


            (b.tabLayout.getTabAt(0)?.view?.getChildAt(1) as? TextView)
                ?.setTextAppearance(R.style.Text_Emphasize)
            b.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
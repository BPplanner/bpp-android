package com.doubleslas.fifith.alcohol.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentRecommendBinding
import com.doubleslas.fifith.alcohol.databinding.TabCustomBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RecommendFragment : BaseFragment<FragmentRecommendBinding>() {
    private val categoryList by lazy {
        listOf(
            Pair(getString(R.string.category_all), "전체"),
            Pair(getString(R.string.category_liquor), "양주"),
            Pair(getString(R.string.category_wine), "와인"),
            Pair(getString(R.string.category_beer), "세계맥주")
        )
    }
    private lateinit var adapter: CategoryViewPagerAdapter
    private val fragmentList by lazy {
        List(categoryList.size) {
            val category = categoryList[it].second
            RecommendListFragment.create(category)
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecommendBinding {
        return FragmentRecommendBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryViewPagerAdapter()

        binding?.let { b ->
            b.toolbar.inflateMenu(R.menu.toolbar_menu)
            b.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu -> toolbarMenu.show(fragmentManager!!, null)
                    else -> return@setOnMenuItemClickListener false
                }

                return@setOnMenuItemClickListener true
            }

            b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            b.viewPager.adapter = adapter

            TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                tab.text = categoryList[position].first
            }.attach()

            for (i in 0 until b.tabLayout.tabCount) {
                val tab: TabLayout.Tab = b.tabLayout.getTabAt(i)!!
                tab.customView = getTabView(i)
            }
        }


        getSupportActionBar()?.apply {
            setDisplayShowTitleEnabled(false)
        }
    }


    private fun getTabView(position: Int): View? {
        val b = TabCustomBinding.inflate(LayoutInflater.from(context))
        b.tvTab.text = categoryList[position].first
        return b.root
    }

    inner class CategoryViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}
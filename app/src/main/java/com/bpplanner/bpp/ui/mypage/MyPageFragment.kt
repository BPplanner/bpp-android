package com.bpplanner.bpp.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.FragmentMyPageBinding
import com.bpplanner.bpp.ui.common.base.BaseFragment
import com.bpplanner.bpp.ui.home.HomeListFragment
import com.bpplanner.bpp.ui.setting.LicenceActivity
import com.bpplanner.bpp.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayoutMediator

class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {
//    private val viewModel by lazy {
//        ViewModelProvider(this).get(ConceptViewModel::class.java)
//    }

    private val adapter by lazy { MyPageAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyPageBinding {
        return FragmentMyPageBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->

            binding?.let { b ->
                b.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                b.viewPager.adapter = adapter

                TabLayoutMediator(b.tabLayout, b.viewPager) { tab, position ->
                    tab.text = when (position) {
                        1 -> getString(R.string.all_reservation_confirm)
                        else -> getString(R.string.all_inquiring)
                    }
                }.attach()


                b.btnSetting.setOnClickListener {
                    startActivity(Intent(context, SettingActivity::class.java))
                }

            }
        }
    }


    inner class MyPageAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return HomeListFragment.create(position)
        }
    }

}
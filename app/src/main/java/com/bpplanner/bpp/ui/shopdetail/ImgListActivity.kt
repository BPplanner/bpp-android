package com.bpplanner.bpp.ui.shopdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bpplanner.bpp.databinding.FragmentImgListBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class ImgListActivity : BaseActivity() {
    private val imgList: Array<String> by lazy { intent.getStringArrayExtra(EXTRA_IMG_LIST)!! }
    private val isShowIndicator by lazy { intent.getBooleanExtra(EXTRA_IS_SHOW_INDICATOR, true) }
    private val startPosition by lazy { intent.getIntExtra(EXTRA_IS_START_POSITION, 0) }

    private lateinit var binding: FragmentImgListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentImgListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnExit.setOnClickListener {
            finish()
        }

        binding.indicator.isVisible = isShowIndicator

        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = ImgAdapter()

        TabLayoutMediator(
            binding.indicator,
            binding.viewPager
        ) { tab, position ->
            binding.viewPager.currentItem = tab.position
        }.attach()

        binding.viewPager.setCurrentItem(startPosition, false)
    }


    inner class ImgAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return imgList.size
        }

        override fun createFragment(position: Int): Fragment {
            return ImgFragment.create(imgList[position])
        }
    }


    companion object {
        private const val EXTRA_IMG_LIST = "EXTRA_IMG_LIST"
        private const val EXTRA_IS_START_POSITION = "EXTRA_IS_START_POSITION"
        private const val EXTRA_IS_SHOW_INDICATOR = "EXTRA_IS_SHOW_INDICATOR"

        fun getStartIntent(
            context: Context,
            img: String,
            isShowIndicator: Boolean = false
        ): Intent {
            val intent = Intent(context, ImgListActivity::class.java)
            intent.putExtra(EXTRA_IMG_LIST, arrayOf(img))
            intent.putExtra(EXTRA_IS_SHOW_INDICATOR, isShowIndicator)
            return intent
        }

        fun getStartIntent(
            context: Context,
            img: Array<String>,
            position: Int = 0,
            isShowIndicator: Boolean = true
        ): Intent {
            val intent = Intent(context, ImgListActivity::class.java)
            intent.putExtra(EXTRA_IMG_LIST, img)
            intent.putExtra(EXTRA_IS_START_POSITION, position)
            intent.putExtra(EXTRA_IS_SHOW_INDICATOR, isShowIndicator)
            return intent
        }
    }
}
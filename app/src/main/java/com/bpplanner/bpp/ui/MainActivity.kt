package com.bpplanner.bpp.ui

import android.os.Bundle
import android.view.MenuItem
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityMainBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bpplanner.bpp.ui.concept.ConceptFragment
import com.bpplanner.bpp.ui.home.HomeFragment
import com.bpplanner.bpp.ui.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment by lazy { HomeFragment() }
    private val conceptFragment by lazy { ConceptFragment() }
    private val myPageFragment by lazy { MyPageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigation.itemIconTintList = null;


        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment, homeFragment)
            .add(R.id.main_fragment, conceptFragment)
            .add(R.id.main_fragment, myPageFragment)
            .commit()

        gotoMain()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        hideAllFragment()

        when (item.itemId) {
            R.id.menu_home ->
                supportFragmentManager.beginTransaction()
                    .show(homeFragment)
                    .commit()
            R.id.menu_concept ->
                supportFragmentManager.beginTransaction()
                    .show(conceptFragment)
                    .commit()
            R.id.menu_mypage ->
                supportFragmentManager.beginTransaction()
                    .show(myPageFragment)
                    .commit()
            else -> return false
        }

        return true
    }

    fun gotoMain() {
        binding.bottomNavigation.selectedItemId = R.id.menu_home
    }


    private fun hideAllFragment() {
        supportFragmentManager.beginTransaction()
            .hide(homeFragment)
            .hide(conceptFragment)
            .hide(myPageFragment)
            .commit()
    }
}
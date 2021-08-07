package com.bpplanner.bpp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivitySettingBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bpplanner.bpp.ui.launch.AuthActivity

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        binding.licence.setOnClickListener {
            startActivity(Intent(this@SettingActivity, LicenceActivity::class.java))
        }
        binding.withdraw.setOnClickListener {
            startActivity(Intent(this@SettingActivity, WithdrawActivity::class.java))
        }
        binding.logout.setOnClickListener {
            MyApp.getPrefs().clear()
            startActivity(Intent(this@SettingActivity, AuthActivity::class.java))
            finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
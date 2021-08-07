package com.bpplanner.bpp.ui.setting

import android.os.Bundle
import android.view.MenuItem
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivitySettingBinding
import com.bpplanner.bpp.databinding.ActivityWithdrawBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity

class WithdrawActivity : BaseActivity() {
    private lateinit var binding: ActivityWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }


        binding.layoutCheck.setOnClickListener {
            binding.check.isChecked = !binding.check.isChecked
        }
        binding.check.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.btnConfirm.isEnabled = isChecked
        }

        binding.btnConfirm.setOnClickListener {
            // TODO: API 연동
        }


        binding.btnConfirm.isEnabled = binding.check.isChecked
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
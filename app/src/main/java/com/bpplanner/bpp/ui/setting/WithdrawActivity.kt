package com.bpplanner.bpp.ui.setting

import android.os.Bundle
import com.bpplanner.bpp.databinding.ActivitySettingBinding
import com.bpplanner.bpp.databinding.ActivityWithdrawBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity

class WithdrawActivity : BaseActivity() {
    private lateinit var binding: ActivityWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
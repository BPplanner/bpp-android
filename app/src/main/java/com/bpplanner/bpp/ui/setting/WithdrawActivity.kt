package com.bpplanner.bpp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityWithdrawBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.model.base.RestClient
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bpplanner.bpp.ui.launch.AuthActivity

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
            it.elevation = 0f
        }


        binding.layoutCheck.setOnClickListener {
            binding.check.isChecked = !binding.check.isChecked
        }
        binding.check.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.btnConfirm.isEnabled = isChecked
        }

        binding.btnConfirm.setOnClickListener {
            RestClient.getAuthService().withdraw().observe(this, Observer {
                when (it) {
                    is ApiStatus.Success -> {
                        MyApp.getPrefs().clear()
                        val intent = Intent(this@WithdrawActivity, AuthActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                    is ApiStatus.Error -> {
                        Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show()
                    }
                }
            })
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
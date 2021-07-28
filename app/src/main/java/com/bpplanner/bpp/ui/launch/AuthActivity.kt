package com.bpplanner.bpp.ui.launch

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.databinding.ActivityAuthBinding
import com.bpplanner.bpp.ui.base.BaseActivity
import com.bpplanner.bpp.model.base.ApiStatus
import com.kakao.sdk.user.UserApiClient

class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by lazy { ViewModelProvider(this).get(LaunchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this@AuthActivity) { token, error ->
                if (error != null) {
                    Log.e("KakaoAuth", "로그인 실패", error)
                } else if (token != null) {
                    login(token.accessToken)
                }
            }
        }
    }

    private fun login(token : String) {
        viewModel.loginKakao(token).observe(this, Observer {
            when (it) {
                is ApiStatus.Success ->{
                    binding.txt.text = it.toString()
                }
            }
        })

    }
}
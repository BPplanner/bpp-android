package com.bpplanner.bpp.ui.launch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityAuthBinding
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.MainActivity
import com.bpplanner.bpp.ui.home.HomeFragment
import com.kakao.sdk.user.UserApiClient

class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by lazy { ViewModelProvider(this).get(LaunchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.title.text = getText(R.string.auth_title)

        binding.videoView.setVideoURI(Uri.parse("android.resource://${packageName}/${R.raw.login}"))
        binding.videoView.setOnPreparedListener {
            it.isLooping = true
        }

        binding.btnKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this@AuthActivity) { token, error ->
                if (error != null) {
                    Log.e("KakaoAuth", "로그인 실패", error)
                } else if (token != null) {
                    login(token.accessToken)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.videoView.start()
    }

    private fun login(token : String) {
        viewModel.loginKakao(token).observe(this, Observer {
            when (it) {
                is ApiStatus.Success ->{
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}
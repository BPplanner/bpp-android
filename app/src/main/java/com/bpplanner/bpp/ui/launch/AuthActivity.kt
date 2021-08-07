package com.bpplanner.bpp.ui.launch

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bpplanner.bpp.R
import com.bpplanner.bpp.databinding.ActivityAuthBinding
import com.bpplanner.bpp.model.base.ApiStatus
import com.bpplanner.bpp.ui.MainActivity
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.kakao.sdk.user.UserApiClient

class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel by lazy { ViewModelProvider(this).get(LaunchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val titleStr = SpannableString(getText(R.string.auth_title))
//        titleStr.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.title.text = titleStr
        binding.title.text = getText(R.string.auth_title)

        binding.videoView.setVideoURI(Uri.parse("android.resource://${packageName}/${R.raw.login}"))
        binding.videoView.setOnPreparedListener {
            it.isLooping = true
        }

        binding.btnKakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (token != null) {
                        login(token.accessToken)
                    } else if (error != null) {
                        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                            if (token != null) {
                                login(token.accessToken)
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (token != null) {
                        login(token.accessToken)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.videoView.start()
    }

    private fun login(token: String) {
        viewModel.loginKakao(token).observe(this, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}
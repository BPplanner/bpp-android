package com.bpplanner.bpp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bpplanner.bpp.databinding.ActivityMainBinding
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding :  ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this@MainActivity) { token, error ->
                if (error != null) {
                    Log.e("KakaoAuth", "로그인 실패", error)
                }
                else if (token != null) {
                    binding.txt.setText(token.accessToken.toString())
                    Log.i("KakaoAuth", "로그인 성공 ${token.accessToken}")
                }
            }
        }
    }
}
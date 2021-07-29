package com.bpplanner.bpp

import android.app.Application
import androidx.annotation.StringRes
import com.bpplanner.bpp.utils.RemoteConfigUtil
import com.bpplanner.bpp.utils.SharedPreferenceUtil
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.kakao.sdk.common.KakaoSdk

class MyApp : Application() {
    private val prefs by lazy { SharedPreferenceUtil(this)}
    private val remoteConfig by lazy { RemoteConfigUtil(this)}

    override fun onCreate() {
        super.onCreate()
        app = this

        KakaoSdk.init(this, "728c87e40ccd496fb94f1000585da2df")

        Firebase.initialize(this)
        remoteConfig
    }


    companion object {
        private lateinit var app: MyApp

        fun getInstance(): MyApp{
            return app
        }

        fun getPrefs(): SharedPreferenceUtil{
            return app.prefs
        }

        fun getRemoteConfig() : RemoteConfigUtil{
            return app.remoteConfig
        }

        fun getString(@StringRes id: Int): String {
            return app.getString(id)
        }
    }
}
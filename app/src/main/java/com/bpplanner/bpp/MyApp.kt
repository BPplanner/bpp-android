package com.bpplanner.bpp

import android.app.Application
import androidx.annotation.StringRes
import com.bpplanner.bpp.utils.SharedPreferenceUtil
import com.kakao.sdk.common.KakaoSdk

class MyApp : Application() {
    private lateinit var prefs: SharedPreferenceUtil


    override fun onCreate() {
        super.onCreate()
        app = this
        prefs = SharedPreferenceUtil(this)


        KakaoSdk.init(this, "728c87e40ccd496fb94f1000585da2df")
    }


    companion object {
        private lateinit var app: MyApp

        fun getInstance(): MyApp{
            return app
        }

        fun getPrefs(): SharedPreferenceUtil{
            return app.prefs
        }

        fun getString(@StringRes id: Int): String {
            return app.getString(id)
        }
    }
}
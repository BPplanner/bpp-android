package com.bpplanner.bpp.ui

import android.content.Intent
import android.os.Bundle
import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.ui.common.base.BaseActivity
import com.bpplanner.bpp.ui.home.HomeActivity
import com.bpplanner.bpp.ui.launch.AuthActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (MyApp.getPrefs().token == null) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}
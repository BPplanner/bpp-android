package com.bpplanner.bpp.utils

import android.annotation.SuppressLint
import android.content.Context
import com.bpplanner.bpp.dto.TokenData
import com.google.gson.Gson

class SharedPreferenceUtil(private val context: Context) {
    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var token: TokenData?
        get() {
            val json = prefs.getString(PREFS_TOKEN, null) ?: return null
            return Gson().fromJson(json, TokenData::class.java)
        }
        @SuppressLint("ApplySharedPref")
        set(value) {
            if (value == null)
                prefs.edit().remove(PREFS_TOKEN).commit()
            else
                prefs.edit().putString(PREFS_TOKEN, Gson().toJson(value)).commit()
        }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "BPP_PLANNER_PREFS"

        private const val PREFS_TOKEN = "PREFS_TOKEN"
    }
}
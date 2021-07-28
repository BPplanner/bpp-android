package com.bpplanner.bpp.utils

import android.util.Log
import com.bpplanner.bpp.BuildConfig

class LogUtil {
    companion object {

        private val DEBUG: Boolean = BuildConfig.DEBUG

        fun v(tag: String, msg: String) {
            if (DEBUG) Log.v(tag, msg)
        }

        fun v(tag: String, msg: String, throwable: Throwable) {
            if (DEBUG) Log.v(tag, msg, throwable)
        }

        fun d(tag: String, msg: String) {
            if (DEBUG) Log.d(tag, msg)
        }

        fun d(tag: String, msg: String, throwable: Throwable) {
            if (DEBUG) Log.d(tag, msg, throwable)
        }

        fun i(tag: String, msg: String) {
            if (DEBUG) Log.d(tag, msg)
        }

        fun i(tag: String, msg: String, throwable: Throwable) {
            if (DEBUG) Log.i(tag, msg, throwable)
        }

        fun w(tag: String, msg: String) {
            if (DEBUG) Log.d(tag, msg)
        }

        fun w(tag: String, msg: String, throwable: Throwable) {
            if (DEBUG) Log.w(tag, msg, throwable)
        }

        fun e(tag: String, msg: String) {
            if (DEBUG) Log.e(tag, msg)
        }

        fun e(tag: String, msg: String, throwable: Throwable) {
            if (DEBUG) Log.e(tag, msg, throwable)
        }
    }
}

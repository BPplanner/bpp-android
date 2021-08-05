package com.bpplanner.bpp.ui.setting

data class LicenceData(val name: String, val link: String, val licence: String) {
    companion object {
        val list = listOf(
            LicenceData(
                "Android Constraint Layout Library",
                "https://developer.android.com/reference/androidx/constraintlayout/packages",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Android KTX",
                "https://github.com/android/android-ktx",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Kotlin",
                "https://github.com/jetbrains/kotlin",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Android Recycler View",
                "https://developer.android.com/jetpack/androidx/releases/recyclerview",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Android ViewPager2",
                "https://developer.android.com/jetpack/androidx/releases/viewpager2",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Android Lifecycle",
                "https://developer.android.com/reference/kotlin/androidx/lifecycle/package-summary",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Square Retrofit",
                "https://github.com/square/retrofit",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Square OkHttp",
                "https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor",
                "Apache License Version 2.0"
            ),
            LicenceData(
                "Glide",
                "https://github.com/bumptech/glide",
                "BSD, part MIT and Apache 2.0."
            ),
            LicenceData(
                "Kotlin Coroutines",
                "https://github.com/Kotlin/kotlinx.coroutines",
                "Apache License Version 2.0"
            ),
        )
    }
}
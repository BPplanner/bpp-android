package com.bpplanner.bpp.utils

import android.content.Context
import com.bpplanner.bpp.BuildConfig
import com.bpplanner.bpp.dto.ConceptFilter
import com.bpplanner.bpp.dto.IdValuePair
import com.bpplanner.bpp.dto.IdValuePairCheckable
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson

class RemoteConfigUtil(private val context: Context) {
    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 60 else 60 * 60 * 24
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetch().addOnSuccessListener {
            remoteConfig.activate()
        }

    }

    val studioFilterList: Array<IdValuePairCheckable>
        get() {
            val str = remoteConfig.getString(STUDIO_FILTER)
            LogUtil.d("BPP", remoteConfig.getValue(STUDIO_FILTER).toString())
            return Gson().fromJson(str, Array<IdValuePairCheckable>::class.java)
        }

    val beautyFilterList: Array<IdValuePairCheckable>
        get() {
            val str = remoteConfig.getString(BEAUTY_FILTER)
            return Gson().fromJson(str, Array<IdValuePairCheckable>::class.java)
        }

    val conceptFilter: ConceptFilter
        get() {
            val str = remoteConfig.getString(CONCEPT_FILTER)
            return Gson().fromJson(str, ConceptFilter::class.java)
        }


    companion object {
        private const val BEAUTY_FILTER = "beauty_filter"
        private const val STUDIO_FILTER = "studio_filter"
        private const val CONCEPT_FILTER = "concept_filter"
    }

}
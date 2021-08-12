package com.bpplanner.bpp.utils

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class JWT {
    companion object {
        fun getUserId(jwt: String): Int {
            val payload = jwt.split(".")[1]
            val jsonString = String(Base64.decode(payload, 0))
            val json = Gson().fromJson(jsonString, JWTData::class.java)

            return json.userId
        }

        fun getUserName(jwt: String): String {
            val payload = jwt.split(".")[1]
            val jsonString = String(Base64.decode(payload, 0))
            val json = Gson().fromJson(jsonString, JWTData::class.java)

            return json.name
        }
    }

    data class JWTData(
        @SerializedName("user_id") val userId: Int,
        @SerializedName("username") val name: String
    )
}
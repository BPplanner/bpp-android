package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("refresh_token") val refresh: String
)

package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("access_token")
    val accessToken: String?,
    val refresh: String?
)

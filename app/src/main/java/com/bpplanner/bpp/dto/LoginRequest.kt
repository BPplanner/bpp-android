package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("access_token")
    val accessToken: String
)

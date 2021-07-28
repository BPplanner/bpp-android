package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    val userId: Int,
    val refresh: String
)

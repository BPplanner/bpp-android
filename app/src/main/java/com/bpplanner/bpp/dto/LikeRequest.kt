package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class LikeRequest(
    @SerializedName("change_to_like") val like: Boolean
)

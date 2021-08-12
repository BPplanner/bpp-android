package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class MypagShopData(
    val id: Int,
    val name: String,
    val logo: String,
    @SerializedName("kakaourl") val kakaoUrl: String,
    @SerializedName("shop_type") val type: Int
)

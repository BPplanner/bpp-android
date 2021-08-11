package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class MypageData(
    val id: Int,
    val state: Int,
    val shop: MypagShopData,
    @SerializedName("reserved_date") val reservedDate: String
)
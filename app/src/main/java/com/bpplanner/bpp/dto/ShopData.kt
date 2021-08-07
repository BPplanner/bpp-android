package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class ShopData(
    val id: Int,
    val name: String,
    val address: String,
    var like: Boolean,
    @SerializedName("minprice") val minPrice: Int,
    @SerializedName("profile") val profileImg: String
)

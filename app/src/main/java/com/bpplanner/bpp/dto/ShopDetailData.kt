package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class ShopDetailData(
    val id:Int,
    val name:String,
    val logo: String,
    var like: Boolean,
    @SerializedName("kakaourl") val kakaoUrl: String,
    @SerializedName("address_detail")val address:String,
    @SerializedName("profiles") val profileImgList: List<String>,
    @SerializedName("map") val mapImg: String,
    @SerializedName("minprice") val minPrice:Int?,
    @SerializedName("price_desc") val priceImg: String?,
    @SerializedName("affiliates") val partnershipList: List<PartnershipData>?,
)


data class PartnershipData(
    val id: Int,
    val name: String,
    @SerializedName("profile") val img: String,
)


package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class InquiringList(
    val studioList: List<MypageData>,
    val beautyList: List<MypageData>,
)

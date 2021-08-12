package com.bpplanner.bpp.dto

import com.bpplanner.bpp.utils.pagination.IPageLoadData
import com.google.gson.annotations.SerializedName

data class MypageResponse(
    @SerializedName("remaining_days") val remainingDays: Int?,
    @SerializedName("results") val list: List<MypageData>,
)
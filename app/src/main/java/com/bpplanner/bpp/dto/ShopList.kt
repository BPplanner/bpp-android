package com.bpplanner.bpp.dto

import com.bpplanner.bpp.utils.pagination.IPageLoadData
import com.google.gson.annotations.SerializedName

data class ShopList(
    @SerializedName("results") private val list: List<ShopData>,
    val next : String?
) : IPageLoadData<ShopData> {
    override fun getList(): List<ShopData> {
        return list
    }

    override fun isFinish(): Boolean {
        return next == null
    }

}

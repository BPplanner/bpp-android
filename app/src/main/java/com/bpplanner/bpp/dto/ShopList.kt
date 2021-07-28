package com.bpplanner.bpp.dto

import com.bpplanner.bpp.utils.pagination.IPageLoadData
import com.google.gson.annotations.SerializedName

data class ShopList(
    @SerializedName("return_data") private val list: List<ShopData>

) : IPageLoadData<ShopData> {
    override fun getList(): List<ShopData> {
        return list
    }

}

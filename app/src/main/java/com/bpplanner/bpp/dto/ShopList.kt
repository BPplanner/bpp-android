package com.bpplanner.bpp.dto

import com.bpplanner.bpp.utils.pagination.IPageLoadData

data class ShopList(
    val reviewList: List<ShopData>,
    val totalCnt: Int
) : IPageLoadData<ShopData> {
    override fun getTotalCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getList(): List<ShopData> {
        TODO("Not yet implemented")
    }
}

package com.bpplanner.bpp.ui.home

import com.bpplanner.bpp.dto.ShopList
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.model.base.RestClient


class HomeRepository {
    private val service by lazy { RestClient.getShopService() }

    fun getShopList(page: Int, address: Array<String>) : ApiLiveData<ShopList>{
        return service.getStudioList(page, address)
    }

    fun getBeautyList(page: Int, address: Array<String>) : ApiLiveData<ShopList>{
        return service.getBeautyList(page, address)
    }
}
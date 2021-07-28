package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.ShopList
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopRetrofit {

    @GET("/shop/studios/")
    fun getStudioList(
        @Query("page") page: Int,
        @Query("address") address: Array<String>
    ): ApiLiveData<ShopList>

    @GET("/beautyshops")
    fun getBeautyList(
        @Query("page") page: Int,
        @Query("address") address: Array<String>
    ): ApiLiveData<ShopList>

}


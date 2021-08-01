package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.ShopList
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopRetrofit {

    @GET("/shops/studios/")
    fun getStudioList(
        @Query("page") page: Int,
        @Query("like") like: Boolean,
        @Query("address") address: List<String>?
    ): ApiLiveData<ShopList>

    @GET("/shops/beautyshops/")
    fun getBeautyList(
        @Query("page") page: Int,
        @Query("like") like: Boolean,
        @Query("address") address: List<String>?
    ): ApiLiveData<ShopList>

}


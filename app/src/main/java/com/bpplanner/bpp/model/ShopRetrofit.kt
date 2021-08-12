package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.*
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.*

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

    @GET("/shops/{id}")
    fun getShopDetail(
        @Path("id") id: Int
    ): ApiLiveData<ShopDetailData>

    @GET("/shops/{id}/concepts")
    fun getConceptList(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): ApiLiveData<ConceptList>

    @PUT("/shops/{id}/like")
    fun setLike(
        @Path("id") id: Int,
        @Body like: LikeRequest
    ): ApiLiveData<ConceptList>


    @POST("/reservations/shops/{id}")
    fun inquireShop(
        @Path("id") id: Int,
    ): ApiLiveData<Any>

}


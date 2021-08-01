package com.bpplanner.bpp.model

import com.bpplanner.bpp.dto.ConceptFilter
import com.bpplanner.bpp.dto.ConceptList
import com.bpplanner.bpp.dto.LikeRequest
import com.bpplanner.bpp.dto.ShopList
import com.bpplanner.bpp.model.base.ApiLiveData
import retrofit2.http.*

interface ConceptRetrofit {

    @GET("/concepts/studios/")
    fun getConceptList(
        @Query("page") page: Int,
        @Query("like") like: Boolean,
        @Query("head_count") head_count: Array<String>?,
        @Query("gender") gender: Array<String>?,
        @Query("background") background: Array<String>?,
        @Query("prop") prop: Array<String>?,
        @Query("dress") dress: Array<String>?,
    ): ApiLiveData<ConceptList>

    @PUT("/shops/{id}/like")
    fun setLike(
        @Path("id") id:Int,
        @Body body: LikeRequest
    ) : ApiLiveData<Any>
}


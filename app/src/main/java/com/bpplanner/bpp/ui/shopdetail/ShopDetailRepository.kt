package com.bpplanner.bpp.ui.shopdetail

import com.bpplanner.bpp.MyApp
import com.bpplanner.bpp.dto.*
import com.bpplanner.bpp.model.AuthRetrofit
import com.bpplanner.bpp.model.base.*

class ShopDetailRepository {
    private val retrofit = RestClient.getShopService()

    fun getDetailData(id: Int): ApiLiveData<ShopDetailData> {
        return retrofit.getShopDetail(id)
    }

    fun getConceptList(id: Int, page: Int): ApiLiveData<ConceptList> {
        return retrofit.getConceptList(id, page)
    }

    fun setLike(id: Int, value: Boolean){
        retrofit.setLike(id, LikeRequest(value))
    }

}
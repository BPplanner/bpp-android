package com.bpplanner.bpp.ui.concept

import com.bpplanner.bpp.dto.ConceptFilter
import com.bpplanner.bpp.dto.ConceptList
import com.bpplanner.bpp.dto.LikeRequest
import com.bpplanner.bpp.model.base.ApiLiveData
import com.bpplanner.bpp.model.base.RestClient


class ConceptRepository {
    private val service by lazy { RestClient.getConceptService() }

    fun getConceptList(
        page: Int,
        likeFilter: Boolean,
        filter: ConceptFilter
    ): ApiLiveData<ConceptList> {
        val request = filter.toRequest()
        return service.getConceptList(
            page,
            likeFilter,
            request.head_count,
            request.gender,
            request.background,
            request.prop,
            request.dress
        )
    }

    fun setLikeConcept(id: Int, value: Boolean): ApiLiveData<Any> {
        return service.setLike(id, LikeRequest(value))
    }
}

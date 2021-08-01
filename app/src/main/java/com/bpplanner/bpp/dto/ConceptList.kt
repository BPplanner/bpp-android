package com.bpplanner.bpp.dto

import com.bpplanner.bpp.utils.pagination.IPageLoadData
import com.google.gson.annotations.SerializedName

data class ConceptList(
    @SerializedName("results") private val list: List<ConceptData>,
    val next : String?
) : IPageLoadData<ConceptData> {
    override fun getList(): List<ConceptData> {
        return list
    }

    override fun isFinish(): Boolean {
        return next == null
    }

}

package com.bpplanner.bpp.dto

import com.google.gson.annotations.SerializedName

data class ConceptData(
    val id: Int,
    @SerializedName("profile") val img: String,
    val shop: Int,
    var like: Boolean
)

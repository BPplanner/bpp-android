package com.bpplanner.bpp.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConceptData(
    val id: Int,
    @SerializedName("profile") val img: String,
    val shop: IdNamePair,
    var like: Boolean
) : Parcelable

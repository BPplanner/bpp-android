package com.bpplanner.bpp.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdNamePair(val id: String, val name: String): Parcelable
data class IdValuePair(val id: String, val value: String)
data class IdValuePairCheckable(val id: String, val value: String, var checked: Boolean)

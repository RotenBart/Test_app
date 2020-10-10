package com.example.testapp.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Specialty(
    @SerializedName("name")
    var specialtyName: String? = null,

    @SerializedName("specialty_id")
    var specialtyId: Int? = null
): Parcelable
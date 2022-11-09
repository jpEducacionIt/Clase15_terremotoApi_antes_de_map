package com.example.clase15_terremotoapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Terremoto(
    val id: String,
    val magnitude: Double,
    val place: String,
    val time: Long,
    val longitude: Double,
    val latitude: Double
) : Parcelable

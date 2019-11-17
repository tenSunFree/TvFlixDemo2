package com.home.tvflixdemo2.list.model.bean

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoBean(
    val albumId: Int,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable
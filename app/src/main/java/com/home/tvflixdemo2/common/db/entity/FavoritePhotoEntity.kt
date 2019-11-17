package com.home.tvflixdemo2.common.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_photo_entity")
data class FavoritePhotoEntity(
    @PrimaryKey
    val id: Long,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
    val isFavorite: Boolean
)


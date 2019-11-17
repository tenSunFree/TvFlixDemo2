package com.home.tvflixdemo2.common.db.dao

import androidx.room.*
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity

@Dao
interface TFDDao {

    @Query("SELECT * FROM favorite_photo_entity")
    suspend fun getFavoritePhotoEntityList(): List<FavoritePhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoritePhotoEntity)

    @Delete
    suspend fun remove(entity: FavoritePhotoEntity)

    @Query("SELECT id from favorite_photo_entity")
    suspend fun getFavoritePhotoEntityIdList(): List<Long>

    @Query("DELETE FROM favorite_photo_entity")
    suspend fun clear()
}

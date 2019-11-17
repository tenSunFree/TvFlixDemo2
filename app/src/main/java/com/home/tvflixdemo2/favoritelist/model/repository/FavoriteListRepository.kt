package com.home.tvflixdemo2.favoritelist.model.repository

import com.home.tvflixdemo2.common.db.dao.TFDDao
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteListRepository @Inject
constructor(private val tFDDao: TFDDao) {

    suspend fun getFavoritePhotoEntityList(): List<FavoritePhotoEntity> {
        return tFDDao.getFavoritePhotoEntityList()
    }
}

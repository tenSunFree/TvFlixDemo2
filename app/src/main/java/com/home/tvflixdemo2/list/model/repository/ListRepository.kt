package com.home.tvflixdemo2.list.model.repository

import com.home.tvflixdemo2.common.db.dao.TFDDao
import com.home.tvflixdemo2.common.db.entity.FavoritePhotoEntity
import com.home.tvflixdemo2.list.model.viewdata.ListViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject
constructor(private val tFDDao: TFDDao) {

    fun insertShowIntoFavorites(bean: ListViewData.FavoritePhotoBean) {
        val entity = FavoritePhotoEntity(
            id = bean.photoBean.id,
            albumId = bean.photoBean.albumId,
            title = bean.photoBean.title,
            url = bean.photoBean.url,
            thumbnailUrl = bean.photoBean.thumbnailUrl,
            isFavorite = bean.isFavoriteShow
        )
        CoroutineScope(Dispatchers.IO).launch { tFDDao.insert(entity) }
    }

    fun removeShowFromFavorites(bean: ListViewData.FavoritePhotoBean) {
        val entity = FavoritePhotoEntity(
            id = bean.photoBean.id,
            albumId = bean.photoBean.albumId,
            title = bean.photoBean.title,
            url = bean.photoBean.url,
            thumbnailUrl = bean.photoBean.thumbnailUrl,
            isFavorite = bean.isFavoriteShow
        )
        CoroutineScope(Dispatchers.IO).launch { tFDDao.remove(entity) }
    }

    suspend fun getFavoritePhotoEntityIds(): List<Long> {
        return tFDDao.getFavoritePhotoEntityIdList()
    }
}

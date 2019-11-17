package com.home.tvflixdemo2.list.model.viewdata

import com.home.tvflixdemo2.list.model.bean.PhotoBean

data class ListViewData(val favoritePhotoBeanList: List<FavoritePhotoBean>) {

    data class FavoritePhotoBean(
        val photoBean: PhotoBean,
        val isFavoriteShow: Boolean
    )
}
package com.home.tvflixdemo2.favoritelist.model.viewstate

import com.home.tvflixdemo2.favoritelist.model.viewdata.FavoriteListViewData

sealed class FavoriteListViewState
data class NetworkError(val message: String?) : FavoriteListViewState()
object Loading : FavoriteListViewState()
data class Success(val data: FavoriteListViewData) : FavoriteListViewState()
package com.home.tvflixdemo2.list.model.viewstate

import com.home.tvflixdemo2.list.model.viewdata.ListViewData

sealed class HomeViewState
data class NetworkError(val message: String?) : HomeViewState()
object Loading : HomeViewState()
data class Success(val data: ListViewData) : HomeViewState()
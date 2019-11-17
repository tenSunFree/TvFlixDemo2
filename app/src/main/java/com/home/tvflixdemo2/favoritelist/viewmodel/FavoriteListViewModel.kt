package com.home.tvflixdemo2.favoritelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.tvflixdemo2.favoritelist.model.repository.FavoriteListRepository
import com.home.tvflixdemo2.favoritelist.model.viewdata.FavoriteListViewData
import com.home.tvflixdemo2.favoritelist.model.viewstate.FavoriteListViewState
import com.home.tvflixdemo2.favoritelist.model.viewstate.Loading
import com.home.tvflixdemo2.favoritelist.model.viewstate.NetworkError
import com.home.tvflixdemo2.favoritelist.model.viewstate.Success
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteListViewModel @Inject
constructor(private val favoriteShowsRepository: FavoriteListRepository) : ViewModel() {

    private val favoriteListViewStateLiveData: MutableLiveData<FavoriteListViewState> =
        MutableLiveData()

    fun loadFavoritePhotoEntityList() {
        favoriteListViewStateLiveData.value = Loading
        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, exception -> onError(exception) }
        viewModelScope.launch(coroutineExceptionHandler) {
            val favoriteShows =
                withContext(Dispatchers.IO + coroutineExceptionHandler) {
                    favoriteShowsRepository.getFavoritePhotoEntityList()
                }
            withContext(Dispatchers.Main + coroutineExceptionHandler) {
                favoriteListViewStateLiveData.value = Success(FavoriteListViewData(favoriteShows))
            }
        }
    }

    private fun onError(throwable: Throwable) {
        favoriteListViewStateLiveData.value = NetworkError(throwable.message)
    }

    fun getFavoriteListViewStateLiveData(): LiveData<FavoriteListViewState> {
        return favoriteListViewStateLiveData
    }
}

package com.home.tvflixdemo2.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.tvflixdemo2.common.network.TFDApi
import com.home.tvflixdemo2.list.model.bean.PhotoBean
import com.home.tvflixdemo2.list.model.repository.ListRepository
import com.home.tvflixdemo2.list.model.viewdata.ListViewData
import com.home.tvflixdemo2.list.model.viewstate.HomeViewState
import com.home.tvflixdemo2.list.model.viewstate.Loading
import com.home.tvflixdemo2.list.model.viewstate.NetworkError
import com.home.tvflixdemo2.list.model.viewstate.Success
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val tFDApi: TFDApi,
    private val repository: ListRepository
) : ViewModel() {

    private val homeViewStateLiveData: MutableLiveData<HomeViewState> = MutableLiveData()

    fun requestData() {
        homeViewStateLiveData.value = Loading
        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, exception -> onError(exception) }
        // viewModelScope在Dispatchers.Main上啟動新的協程
        // 添加CoroutineExceptionHandler到父協程上下文中以捕獲異常並進行處理
        viewModelScope.launch(coroutineExceptionHandler) {
            // 從db獲取喜歡的節目，room中的suspend功能將使用IO調度程序啟動新的協程
            // 協程在執行到有suspend標記的函數, 會被suspend掛起, 就是切個線程
            // 掛起函數在執行完成之後, 協程會重新切回它原先的線程
            // 在Kotlin中所謂的掛起, 就是一個稍後會被自動切回來的線程調度操作
            val favoriteShowIds = repository.getFavoritePhotoEntityIds()
            // 從伺服器獲取資料, retrofit中的suspend功能將與IO調度程序一起啟動新的協程
            val photos = tFDApi.getPhotos()
            // 通過Dispatchers.Main在主線程上返回結果
            homeViewStateLiveData.value =
                Success(ListViewData(getFavoritePhotoBeanList(photos, favoriteShowIds)))
        }
    }

    private fun getFavoritePhotoBeanList(
        photoBeanList: List<PhotoBean>,
        favoritePhotoEntityIds: List<Long>
    ): List<ListViewData.FavoritePhotoBean> {
        val favoritePhotoBeanList = ArrayList<ListViewData.FavoritePhotoBean>(photoBeanList.size)
        for (photo in photoBeanList) {
            val favoritePhotoBean =
                if (favoritePhotoEntityIds.contains(photo.id)) {
                    ListViewData.FavoritePhotoBean(photo, true)
                } else {
                    ListViewData.FavoritePhotoBean(photo, false)
                }
            favoritePhotoBeanList.add(favoritePhotoBean)
        }
        return favoritePhotoBeanList
    }

    private fun onError(throwable: Throwable) {
        homeViewStateLiveData.value =
            NetworkError(throwable.message)
    }

    fun getHomeViewState(): LiveData<HomeViewState> {
        return homeViewStateLiveData
    }

    fun addToFavorite(bean: ListViewData.FavoritePhotoBean) {
        repository.insertShowIntoFavorites(bean)
    }

    fun removeFromFavorite(bean: ListViewData.FavoritePhotoBean) {
        repository.removeShowFromFavorites(bean)
    }
}

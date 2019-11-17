package com.home.tvflixdemo2.common.network

import com.home.tvflixdemo2.list.model.bean.PhotoBean
import retrofit2.http.GET

interface TFDApi {

    @GET("photos")
    suspend fun getPhotos(): List<PhotoBean>
}

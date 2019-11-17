package com.home.tvflixdemo2.common.network.module

import com.home.tvflixdemo2.common.network.TFDApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object TvMazeApiModule {

    @JvmStatic
    @Provides // 提供被依賴的對象
    @Singleton // 表明這個被依賴的對像在應用的生命週期裡只有一個實例
    fun provideTFDApi(
        okHttpClient: OkHttpClient,
        @Named(NetworkModule.TFD_BASE_URL) baseUrl: String
    ): TFDApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create(TFDApi::class.java)
    }
}
